package pers.elianacc.yurayura.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
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
import pers.elianacc.yurayura.vo.SysManagerAndRoleVo;
import pers.elianacc.yurayura.vo.SysManagerMsgVo;

import javax.servlet.http.HttpSession;
import java.util.List;

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
    public ApiResult<PageInfo<SysManagerAndRoleVo>> getPage(SysManagerSelectDto dto) {
        ApiResult<PageInfo<SysManagerAndRoleVo>> apiResult = sysFeignClient.getPage(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @Override
    public ApiResult<String> insert(SysManagerInsertDto dto) {
        ApiResult<String> apiResult = sysFeignClient.insert(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @Override
    public ApiResult<String> update(SysManagerUpdateDto dto) {
        ApiResult<String> apiResult = sysFeignClient.update(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    @Override
    public void login(SysManagerLoginDto dto, HttpSession session) {
        // 获取服务器生成验证码
        Object managerVerifyCode = session.getAttribute("managerVerifyCode");
        // 验证码session失效
        Assert.isTrue(!ObjectUtils.isEmpty(managerVerifyCode), "验证码过期，请重新输入");
        Assert.isTrue(managerVerifyCode.toString().equalsIgnoreCase(dto.getVerifyCode()), "验证码错误");

        SysManager sysManager = new SysManager();
        ApiResult<SysManager> apiResult = sysFeignClient.getEnableManagerByName(dto.getManagerName());
        if (apiResult.getCode() == 200) {
            sysManager = JSON.toJavaObject(JSON.parseObject(JSON.toJSONString(apiResult.getData())), SysManager.class);
        }

        Assert.isTrue(!ObjectUtils.isEmpty(sysManager), "用户不存在");
        Assert.isTrue(sysManager.getManagerPassword().equals(DigestUtils.md5DigestAsHex(dto.getManagerPassword().getBytes()))
                , "密码错误");

        StpUtil.login(sysManager.getId(), "PC");

        StpUtil.getSession().set("sysManager", sysManager);
    }

    @Override
    public SysManagerMsgVo getCurrentManagerMsg() {
        SysManagerMsgVo sysManagerMsgVo = new SysManagerMsgVo();
        SysManager currentSysManager = (SysManager) StpUtil.getSession().get("sysManager");
        sysManagerMsgVo.setManagerName(currentSysManager.getManagerName());
        ApiResult<List<String>> apiResult = sysFeignClient.getManagerRolePermission(StpUtil.getLoginIdAsInt());
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        sysManagerMsgVo.setManagerPermission(String.join(",", apiResult.getData()));
        return sysManagerMsgVo;
    }

}
