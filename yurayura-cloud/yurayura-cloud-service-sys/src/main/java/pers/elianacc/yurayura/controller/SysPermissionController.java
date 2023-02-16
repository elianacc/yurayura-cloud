package pers.elianacc.yurayura.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.dto.IdDto;
import pers.elianacc.yurayura.dto.SysPermissionInsertDto;
import pers.elianacc.yurayura.dto.SysPermissionSelectDto;
import pers.elianacc.yurayura.dto.SysPermissionUpdateDto;
import pers.elianacc.yurayura.entity.sys.permission.SysPermission;
import pers.elianacc.yurayura.enumerate.SysPermissionTypeEnum;
import pers.elianacc.yurayura.service.ISysPermissionService;
import pers.elianacc.yurayura.vo.ApiResult;
import pers.elianacc.yurayura.vo.SysPermissionAuthorTreeSelectVo;

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
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        }
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
        if (ObjectUtils.isEmpty(dto.getPageNum())) {
            return ApiResult.warn("页码不能为空");
        } else if (ObjectUtils.isEmpty(dto.getPageSize())) {
            dto.setPageSize(10); // 页记录数默认10
        }
        PageInfo<SysPermission> pageInfo = iSysPermissionService.getPage(dto);
        if (pageInfo.getTotal() == 0) {
            return ApiResult.warn("查询不到数据");
        }
        return ApiResult.success("分页查询成功", pageInfo);
    }

    /**
     * 添加系统权限
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/insert")
    public ApiResult<String> insert(@RequestBody SysPermissionInsertDto dto) {
        if (ObjectUtils.isEmpty(dto.getPermissionName())) {
            return ApiResult.warn("权限名称不能为空");
        } else if (ObjectUtils.isEmpty(dto.getPermissionBelongSubmenuName())) {
            return ApiResult.warn("所属子菜单标识不能为空");
        } else if (ObjectUtils.isEmpty(dto.getPermissionType())) {
            return ApiResult.warn("权限类型不能为空");
        } else if (ObjectUtils.isEmpty(dto.getPermissionStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (ObjectUtils.isEmpty(dto.getPermissionSeq())) {
            return ApiResult.warn("序号不能为空");
        } else if (ObjectUtils.isEmpty(dto.getPermissionBtnVal()) && dto.getPermissionType() == SysPermissionTypeEnum.BUTTON.getTypeId().intValue()) {
            return ApiResult.warn("权限类型为按钮时权限按钮不能为空");
        } else if (dto.getPermissionName().length() > 20) {
            return ApiResult.warn("权限名称不能超过20个字符");
        }
        String warn = iSysPermissionService.insert(dto);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
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
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        } else if (ObjectUtils.isEmpty(dto.getPermissionName())) {
            return ApiResult.warn("权限名称不能为空");
        } else if (ObjectUtils.isEmpty(dto.getPermissionStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (ObjectUtils.isEmpty(dto.getPermissionSeq())) {
            return ApiResult.warn("序号不能为空");
        } else if (dto.getPermissionName().length() > 20) {
            return ApiResult.warn("权限名称不能超过20个字符");
        }
        String warn = iSysPermissionService.update(dto);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("修改成功");
    }

    /**
     * 查询权限授权树
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<pers.elianacc.yurayura.vo.SysPermissionAuthorTreeSelectVo>>
     */
    @GetMapping("/getPermissionAuthorTree")
    public ApiResult<List<SysPermissionAuthorTreeSelectVo>> getPermissionAuthorTree() {
        return ApiResult.success("权限授权树查询成功", iSysPermissionService.getPermissionAuthorTree());
    }

}

