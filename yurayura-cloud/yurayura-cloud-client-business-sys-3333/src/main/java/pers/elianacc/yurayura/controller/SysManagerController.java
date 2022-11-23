package pers.elianacc.yurayura.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.lock.annotation.Lock4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.controller.block.SysManagerBlockHandler;
import pers.elianacc.yurayura.dto.SysManagerInsertDto;
import pers.elianacc.yurayura.dto.SysManagerLoginDto;
import pers.elianacc.yurayura.dto.SysManagerSelectDto;
import pers.elianacc.yurayura.dto.SysManagerUpdateDto;
import pers.elianacc.yurayura.service.SysManagerService;
import pers.elianacc.yurayura.util.VerifyCodeUtil;
import pers.elianacc.yurayura.vo.ApiResult;
import springfox.documentation.annotations.ApiIgnore;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * 系统管理员 controller
 *
 * @author ELiaNaCc
 * @since 2022-10-22
 */
@RestController
@RequestMapping("/api/sys/manager")
@Api(tags = "系统管理员API")
public class SysManagerController {

    @Autowired
    private SysManagerService sysManagerService;

    /**
     * 分页查询系统管理员
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    @PostMapping("/getPage")
    @SentinelResource(value = "sys-manager-getPage",
            blockHandlerClass = SysManagerBlockHandler.class,
            blockHandler = "getPageBlockHandler")
    @ApiOperation("分页查询系统管理员")
    public ApiResult getPage(@RequestBody SysManagerSelectDto dto) {
        return sysManagerService.getPage(dto);
    }

    /**
     * 添加系统管理员
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    @PostMapping("/insert")
    @Lock4j(keys = {"#dto.managerName"}, autoRelease = false)
    @ApiOperation("添加系统管理员")
    public ApiResult insert(@RequestBody SysManagerInsertDto dto) {
        return sysManagerService.insert(dto);
    }

    /**
     * 修改系统管理员
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    @PutMapping("/update")
    @Lock4j(keys = {"#dto.id"}, autoRelease = false)
    @ApiOperation("修改系统管理员")
    public ApiResult update(@RequestBody SysManagerUpdateDto dto) {
        return sysManagerService.update(dto);
    }

    /**
     * 获取系统管理员登入数字加英文验证码及图片
     *
     * @param response
     * @param session
     * @return void
     */
    @GetMapping("/getVerifyCode")
    @ApiOperation("获取系统管理员登入数字加英文验证码及图片")
    public void getVerifyCode(@ApiIgnore HttpServletResponse response, @ApiIgnore HttpSession session)
            throws IOException {
        // 利用图片工具生成图片
        // 第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = VerifyCodeUtil.createImage();
        // 将验证码存入Session
        session.setAttribute("managerVerifyCode", objs[0]);

        // 将图片输出给浏览器
        BufferedImage image = (BufferedImage) objs[1];
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
    }

    /**
     * 系统管理员登入
     *
     * @param dto
     * @param session
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    @PostMapping("/login")
    @Lock4j(keys = {"#dto.managerName"}, autoRelease = false)
    @ApiOperation("系统管理员登入")
    public ApiResult login(@RequestBody SysManagerLoginDto dto, @ApiIgnore HttpSession session) {
        if (ObjectUtils.isEmpty(dto.getManagerName())) {
            return ApiResult.warn("用户名不能为空");
        } else if (ObjectUtils.isEmpty(dto.getManagerPassword())) {
            return ApiResult.warn("密码不能为空");
        } else if (ObjectUtils.isEmpty(dto.getVerifyCode())) {
            return ApiResult.warn("验证码不能为空");
        }
        String warn = sysManagerService.login(dto, session);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("管理员登入成功");
    }

    /**
     * 系统管理员注销
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/logout")
    @ApiOperation("系统管理员注销")
    public ApiResult logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ApiResult.success("管理员注销成功");
    }

    /**
     * 判断系统管理员认证状态
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/judgeAuthen")
    @ApiOperation("判断系统管理员认证状态")
    public ApiResult judgeAuthen() {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            return ApiResult.warn("管理员还未登入，请先登入！");
        }
        return ApiResult.success("管理员已登入");
    }

    /**
     * 获取当前登入管理员信息
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/getCurrentManagerMsg")
    @ApiOperation("获取当前登入管理员信息")
    public ApiResult getCurrentManagerMsg() {
        Map<String, Object> currentManagerMsg = sysManagerService.getCurrentManagerMsg();
        return ApiResult.success("获取成功", currentManagerMsg);
    }

}
