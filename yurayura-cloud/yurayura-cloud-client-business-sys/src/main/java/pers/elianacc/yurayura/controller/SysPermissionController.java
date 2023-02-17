package pers.elianacc.yurayura.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.lock.annotation.Lock4j;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.controller.block.SysPermissionBlockHandler;
import pers.elianacc.yurayura.dto.SysPermissionInsertDto;
import pers.elianacc.yurayura.dto.SysPermissionSelectDto;
import pers.elianacc.yurayura.dto.SysPermissionUpdateDto;
import pers.elianacc.yurayura.entity.sys.permission.SysPermission;
import pers.elianacc.yurayura.service.SysPermissionService;
import pers.elianacc.yurayura.vo.ApiResult;
import pers.elianacc.yurayura.vo.SysPermissionAuthorTreeVo;

import java.util.List;

/**
 * 系统权限 controller
 *
 * @author ELiaNaCc
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/api/sys/permission")
@Api(tags = "系统权限API")
public class SysPermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * 分页查询系统权限
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<PageInfo<SysPermission>>
     */
    @PostMapping("/getPage")
    @SentinelResource(value = "sys-permission-getPage",
            blockHandlerClass = SysPermissionBlockHandler.class,
            blockHandler = "getPageBlockHandler")
    @ApiOperation("分页查询系统权限")
    public ApiResult<PageInfo<SysPermission>> getPage(@RequestBody SysPermissionSelectDto dto) {
        return sysPermissionService.getPage(dto);
    }

    /**
     * 添加系统权限
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/insert")
    @Lock4j(keys = {"#dto.permissionType", "#dto.permissionBelongSubmenuName"}, autoRelease = false)
    @ApiOperation("添加系统权限")
    public ApiResult<String> insert(@RequestBody SysPermissionInsertDto dto) {
        return sysPermissionService.insert(dto);
    }

    /**
     * 修改系统权限
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/update")
    @Lock4j(keys = {"#dto.id"}, autoRelease = false)
    @ApiOperation("修改系统权限")
    public ApiResult<String> update(@RequestBody SysPermissionUpdateDto dto) {
        return sysPermissionService.update(dto);
    }

    /**
     * 查询权限授权树
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<pers.elianacc.yurayura.vo.SysPermissionAuthorTreeVo>>
     */
    @GetMapping("/getPermissionAuthorTree")
    @ApiOperation("查询权限授权树")
    public ApiResult<List<SysPermissionAuthorTreeVo>> getPermissionAuthorTree() {
        return sysPermissionService.getPermissionAuthorTree();
    }

}
