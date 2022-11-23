package pers.elianacc.yurayura.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.elianacc.yurayura.vo.ApiResult;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 认证授权未通过 controller
 *
 * @author ELiaNaCc
 * @since 2022-11-18
 */
@RestController
@RequestMapping("/api")
public class NotAuthController {

    /**
     * 未认证
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    @RequestMapping("/notAuthentication")
    @ApiIgnore
    public ApiResult notAuthentication() {
        return ApiResult.notAuthentication();
    }

    /**
     * 未授权
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    @RequestMapping("/notAuthorization")
    @ApiIgnore
    public ApiResult notAuthorization() {
        return ApiResult.notAuthorization();
    }


}
