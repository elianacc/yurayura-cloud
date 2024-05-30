package pers.elianacc.yurayura.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.dto.IdDto;
import pers.elianacc.yurayura.dto.SysRoleInsertDto;
import pers.elianacc.yurayura.dto.SysRoleSelectDto;
import pers.elianacc.yurayura.dto.SysRoleUpdateDto;
import pers.elianacc.yurayura.entity.sys.role.SysRole;
import pers.elianacc.yurayura.service.ISysRoleService;
import pers.elianacc.yurayura.vo.ApiResult;
import pers.elianacc.yurayura.vo.SysRoleAndPermissionVo;

import java.util.List;

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
        return ApiResult.success("查询成功", iSysRoleService.getById(dto.getId()));
    }

    /**
     * 分页查询系统角色
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<com.github.pagehelper.PageInfo<pers.elianacc.yurayura.vo.SysRoleAndPermissionVo>>
     */
    @PostMapping("/getPage")
    public ApiResult<PageInfo<SysRoleAndPermissionVo>> getPage(@RequestBody SysRoleSelectDto dto) {
        return ApiResult.success("分页查询成功", iSysRoleService.getPage(dto));
    }

    /**
     * 添加系统角色
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/insert")
    public ApiResult<String> insert(@RequestBody SysRoleInsertDto dto) {
        iSysRoleService.insert(dto);
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
        iSysRoleService.update(dto);
        return ApiResult.success("修改成功");
    }

    /**
     * 查询系统角色（根据组织）
     *
     * @param orgId
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<pers.elianacc.yurayura.entity.sys.role.SysRole>>
     */
    @GetMapping("/getByOrg/{orgId}")
    public ApiResult<List<SysRole>> getByOrg(@PathVariable("orgId") Integer orgId) {
        return ApiResult.success("查询成功", iSysRoleService.getByOrg(orgId));
    }
}

