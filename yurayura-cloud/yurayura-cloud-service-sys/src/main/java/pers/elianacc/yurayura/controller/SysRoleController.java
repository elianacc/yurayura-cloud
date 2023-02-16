package pers.elianacc.yurayura.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.dto.IdDto;
import pers.elianacc.yurayura.dto.SysRoleInsertDto;
import pers.elianacc.yurayura.dto.SysRoleSelectDto;
import pers.elianacc.yurayura.dto.SysRoleUpdateDto;
import pers.elianacc.yurayura.entity.sys.role.SysRole;
import pers.elianacc.yurayura.service.ISysRoleService;
import pers.elianacc.yurayura.vo.ApiResult;

import java.util.List;
import java.util.Map;

/**
 * 系统角色 controller
 *
 * @author ELiaNaCc
 * @since 2022-03-07
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    @Autowired
    private ISysRoleService iSysRoleService;

    /**
     * 查询系统角色（根据系统角色id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<pers.elianacc.yurayura.entity.sys.role.SysRole>
     */
    @GetMapping("/getById")
    public ApiResult<SysRole> getById(IdDto dto) {
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        }
        return ApiResult.success("查询成功", iSysRoleService.getById(dto.getId()));
    }

    /**
     * 分页查询系统角色
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<PageInfo<Map<String,Object>>>
     */
    @PostMapping("/getPage")
    public ApiResult<PageInfo<Map<String, Object>>> getPage(@RequestBody SysRoleSelectDto dto) {
        if (ObjectUtils.isEmpty(dto.getPageNum())) {
            return ApiResult.warn("页码不能为空");
        } else if (ObjectUtils.isEmpty(dto.getPageSize())) {
            dto.setPageSize(10); // 页记录数默认10
        }
        PageInfo<Map<String, Object>> pageInfo = iSysRoleService.getPage(dto);
        if (pageInfo.getTotal() == 0) {
            return ApiResult.warn("查询不到数据");
        }
        return ApiResult.success("分页查询成功", pageInfo);
    }

    /**
     * 添加系统角色
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/insert")
    public ApiResult<String> insert(@RequestBody SysRoleInsertDto dto) {
        if (ObjectUtils.isEmpty(dto.getRoleName())) {
            return ApiResult.warn("角色名不能为空");
        } else if (ObjectUtils.isEmpty(dto.getRoleStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (dto.getRoleName().length() > 20) {
            return ApiResult.warn("角色名不能超过20个字符");
        }
        String warn = iSysRoleService.insert(dto);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("添加成功");
    }

    /**
     * 修改系统角色
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/update")
    public ApiResult<String> update(@RequestBody SysRoleUpdateDto dto) {
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        } else if (ObjectUtils.isEmpty(dto.getRoleName())) {
            return ApiResult.warn("角色名不能为空");
        } else if (ObjectUtils.isEmpty(dto.getRoleStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (dto.getRoleName().length() > 20) {
            return ApiResult.warn("角色名不能超过20个字符");
        }
        String warn = iSysRoleService.update(dto);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("修改成功");
    }

    /**
     * 查询所有系统角色
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<pers.elianacc.yurayura.entity.sys.role.SysRole>>
     */
    @GetMapping("/getAll")
    public ApiResult<List<SysRole>> getAll() {
        return ApiResult.success("查询成功", iSysRoleService.getAll());
    }
}

