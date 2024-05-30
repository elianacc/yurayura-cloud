package pers.elianacc.yurayura.feign;

import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.dto.*;
import pers.elianacc.yurayura.entity.sys.dict.SysDict;
import pers.elianacc.yurayura.entity.sys.manager.SysManager;
import pers.elianacc.yurayura.entity.sys.menu.SysMenuSub;
import pers.elianacc.yurayura.entity.sys.org.SysOrg;
import pers.elianacc.yurayura.entity.sys.permission.SysPermission;
import pers.elianacc.yurayura.entity.sys.role.SysRole;
import pers.elianacc.yurayura.vo.*;

import java.util.List;

/**
 * 系统模块 feign client
 *
 * @author ELiaNaCc
 * @since 2022-10-20
 */
@FeignClient(value = "yurayura-cloud-service-sys")
public interface SysFeignClient {

    @PostMapping("/sys/dict/getPage")
    public ApiResult<PageInfo<SysDict>> getPage(@RequestBody SysDictSelectDto dto);

    @PostMapping("/sys/dict/insert")
    public ApiResult<String> insert(@RequestBody SysDictInsertDto dto);

    @PutMapping("/sys/dict/update")
    public ApiResult<String> update(@RequestBody SysDictUpdateDto dto);

    @GetMapping("/sys/dict/getByDictCode")
    public ApiResult<List<SysDict>> getDictByDictCode(@RequestParam String dictCode);

    @GetMapping("/sys/dict/getAll")
    public ApiResult<List<SysDict>> getAllDict();

//----------------------------------------------------------------------------------------------------------------------

    @PostMapping("/sys/manager/getPage")
    public ApiResult<PageInfo<SysManagerAndRoleVo>> getPage(@RequestBody SysManagerSelectDto dto);

    @PostMapping("/sys/manager/insert")
    public ApiResult<String> insert(@RequestBody SysManagerInsertDto dto);

    @PutMapping("/sys/manager/update")
    public ApiResult<String> update(@RequestBody SysManagerUpdateDto dto);

    @RequestMapping("/sys/manager/getManagerRolePermission")
    public ApiResult<List<String>> getManagerRolePermission(@RequestParam Integer managerId);

    @RequestMapping("/sys/manager/getEnableManagerByName")
    public ApiResult<SysManager> getEnableManagerByName(@RequestParam String managerName);

//----------------------------------------------------------------------------------------------------------------------

    @GetMapping("/sys/menu/getTreeList")
    public ApiResult<List<SysMenuTreeVo>> getMenuTreeList();

    @GetMapping("/sys/menu/getTreeListByManagerId")
    public ApiResult<List<SysMenuTreeVo>> getMenuTreeListByManagerId(@RequestParam Integer managerId);

    @PostMapping("/sys/menu/insert")
    public ApiResult<String> insert(@RequestBody SysMenuInsertDto dto);

    @PutMapping("/sys/menu/update")
    public ApiResult<String> update(@RequestBody SysMenuUpdateDto dto);

    @PutMapping("/sys/menu/deleteById")
    public ApiResult<String> deleteMenuById(@RequestBody IdDto dto);

//----------------------------------------------------------------------------------------------------------------------

    @PostMapping("/sys/menuSub/insert")
    public ApiResult<String> insert(@RequestBody SysMenuSubInsertDto dto);

    @PutMapping("/sys/menuSub/update")
    public ApiResult<String> update(@RequestBody SysMenuSubUpdateDto dto);

    @PutMapping("/sys/menuSub/deleteById")
    public ApiResult<String> deleteMenuSubById(@RequestBody IdDto dto);

    @GetMapping("/sys/menuSub/getByIndex")
    public ApiResult<SysMenuSub> getMenuSubByIndex(@RequestParam String index);

    @GetMapping("/sys/menuSub/getAll")
    public ApiResult<List<SysMenuSub>> getAllMenuSub();

//----------------------------------------------------------------------------------------------------------------------

    @PostMapping("/sys/permission/getPage")
    public ApiResult<PageInfo<SysPermission>> getPage(@RequestBody SysPermissionSelectDto dto);

    @PostMapping("/sys/permission/insert")
    public ApiResult<String> insert(@RequestBody SysPermissionInsertDto dto);

    @PutMapping("/sys/permission/update")
    public ApiResult<String> update(@RequestBody SysPermissionUpdateDto dto);

    @GetMapping("/sys/permission/getPermissionAuthorTree")
    public ApiResult<List<SysPermissionAuthorTreeVo>> getPermissionAuthorTree();

//----------------------------------------------------------------------------------------------------------------------

    @PostMapping("/sys/role/getPage")
    public ApiResult<PageInfo<SysRoleAndPermissionVo>> getPage(@RequestBody SysRoleSelectDto dto);

    @PostMapping("/sys/role/insert")
    public ApiResult<String> insert(@RequestBody SysRoleInsertDto dto);

    @PutMapping("/sys/role/update")
    public ApiResult<String> update(@RequestBody SysRoleUpdateDto dto);

    @GetMapping("/sys/role/getByOrg/{orgId}")
    public ApiResult<List<SysRole>> getByOrg(@PathVariable("orgId") Integer orgId);

//----------------------------------------------------------------------------------------------------------------------

    @GetMapping("/sys/org/getAll")
    public ApiResult<List<SysOrg>> getAllOrg();

    @PostMapping("/sys/org/getPage")
    public ApiResult<PageInfo<SysOrg>> getPage(@RequestBody SysOrgSelectDto dto);

    @PostMapping("/sys/org/insert")
    public ApiResult<String> insert(@RequestBody SysOrgInsertDto dto);

    @PutMapping("/sys/org/update")
    public ApiResult<String> update(@RequestBody SysOrgUpdateDto dto);

}
