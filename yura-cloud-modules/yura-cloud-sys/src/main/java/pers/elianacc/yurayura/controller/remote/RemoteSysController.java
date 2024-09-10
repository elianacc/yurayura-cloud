package pers.elianacc.yurayura.controller.remote;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.elianacc.yurayura.service.ISysManagerService;
import pers.elianacc.yurayura.vo.ApiResult;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 远程调用sys服务 controller
 *
 * @author ELiaNaCc
 * @since 2024-09-09
 */
@RestController
@RequestMapping("/remote/sys")
@ApiIgnore
public class RemoteSysController {

    @Autowired
    private ISysManagerService iSysManagerService;

    /**
     * 查询管理员拥有角色的所有权限（根据管理员id）
     *
     * @param managerId
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<java.lang.String>>
     */
    @RequestMapping("/manager/getManagerRolePermission")
    public ApiResult<List<String>> getManagerRolePermission(@RequestParam Integer managerId) {
        return ApiResult.success("查询成功", iSysManagerService.getManagerRolePermission(managerId));
    }

}
