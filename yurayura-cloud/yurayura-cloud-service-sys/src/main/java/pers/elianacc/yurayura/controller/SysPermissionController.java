package pers.elianacc.yurayura.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.dto.IdDto;
import pers.elianacc.yurayura.dto.SysPermissionInsertDto;
import pers.elianacc.yurayura.dto.SysPermissionSelectDto;
import pers.elianacc.yurayura.dto.SysPermissionUpdateDto;
import pers.elianacc.yurayura.entity.sys.permission.SysPermission;
import pers.elianacc.yurayura.service.ISysPermissionService;
import pers.elianacc.yurayura.vo.ApiResult;
import pers.elianacc.yurayura.vo.SysPermissionAuthorTreeVo;

import java.util.List;

/**
 * 系统权限 controller
 *
 * @author ELiaNaCc
 * @since 2021-08-05
 */
@RestController
@RequestMapping("/sys/permission")
public class SysPermissionController {

    @Autowired
    private ISysPermissionService iSysPermissionService;

    /**
     * 查询系统权限（根据系统权限id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<pers.elianacc.yurayura.entity.sys.permission.SysPermission>
     */
    @GetMapping("/getById")
    public ApiResult<SysPermission> getById(IdDto dto) {
        return ApiResult.success("查询成功", iSysPermissionService.getById(dto.getId()));
    }

    /**
     * 分页查询系统权限
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<PageInfo<SysPermission>>
     */
    @PostMapping("/getPage")
    public ApiResult<PageInfo<SysPermission>> getPage(@RequestBody SysPermissionSelectDto dto) {
        return ApiResult.success("分页查询成功", iSysPermissionService.getPage(dto));
    }

    /**
     * 添加系统权限
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/insert")
    public ApiResult<String> insert(@RequestBody SysPermissionInsertDto dto) {
        iSysPermissionService.insert(dto);
        return ApiResult.success("添加成功");
    }

    /**
     * 修改系统权限
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/update")
    public ApiResult<String> update(@RequestBody SysPermissionUpdateDto dto) {
        iSysPermissionService.update(dto);
        return ApiResult.success("修改成功");
    }

    /**
     * 查询权限授权树
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<pers.elianacc.yurayura.vo.SysPermissionAuthorTreeVo>>
     */
    @GetMapping("/getPermissionAuthorTree")
    public ApiResult<List<SysPermissionAuthorTreeVo>> getPermissionAuthorTree() {
        return ApiResult.success("权限授权树查询成功", iSysPermissionService.getPermissionAuthorTree());
    }

}

