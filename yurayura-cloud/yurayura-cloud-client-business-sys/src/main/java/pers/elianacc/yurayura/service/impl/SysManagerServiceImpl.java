package pers.elianacc.yurayura.service.impl;

import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import pers.elianacc.yurayura.dto.*;
import pers.elianacc.yurayura.entity.sys.manager.SysManager;
import pers.elianacc.yurayura.exception.BusinessException;
import pers.elianacc.yurayura.feign.SysFeignClient;
import pers.elianacc.yurayura.service.SysManagerService;
import pers.elianacc.yurayura.vo.ApiResult;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统管理员 service impl
 *
 * @author ELiaNaCc
 * @since 2022-10-21
 */
@Slf4j
@Service
public class SysManagerServiceImpl implements SysManagerService {

    @Autowired
    private SysFeignClient sysFeignClient;

    @Override
    public ApiResult getPage(SysManagerSelectDto dto) {
        ApiResult apiResult = sysFeignClient.getPage(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @Override
    public ApiResult insert(SysManagerInsertDto dto) {
        ApiResult apiResult = sysFeignClient.insert(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @Override
    public ApiResult update(SysManagerUpdateDto dto) {
        ApiResult apiResult = sysFeignClient.update(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    @Override
    public String login(SysManagerLoginDto dto, HttpSession session) {
        String warn = "";
        // 获取服务器生成验证码
        Object managerVerifyCode = session.getAttribute("managerVerifyCode");
        // 验证码session失效
        if (ObjectUtils.isEmpty(managerVerifyCode)) {
            warn = "验证码过期，请重新输入";
            return warn;
        }
        if (managerVerifyCode.toString().equalsIgnoreCase(dto.getVerifyCode())) {
            // 封装用户登入数据(用户名+密码)为token
            UsernamePasswordToken token = new UsernamePasswordToken(dto.getManagerName(), DigestUtils.md5DigestAsHex(dto.getManagerPassword().getBytes()));
            // 获取当前用户
            Subject subject = SecurityUtils.getSubject();
            try {
                // 当前用户登入
                subject.login(token);
                SysManager currentSysManager = (SysManager) subject.getPrincipal();
                log.info("管理员：{}，登入成功", currentSysManager.getManagerName());
            } catch (UnknownAccountException uae) {
                warn = "用户不存在";
            } catch (IncorrectCredentialsException ice) {
                warn = "密码错误";
            }
        } else {
            warn = "验证码错误";
        }
        return warn;
    }

    @Override
    public Map<String, Object> getCurrentManagerMsg() {
        SysManager currentSysManager = (SysManager) SecurityUtils.getSubject().getPrincipal();
        Map<String, Object> currentManagerMsg = new HashMap<>();
        currentManagerMsg.put("managerName", currentSysManager.getManagerName());
        ApiResult apiResult = sysFeignClient.getManagerRolePermission(currentSysManager.getId());
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        currentManagerMsg.put("managerPermission", apiResult.getData());
        return currentManagerMsg;
    }

}
