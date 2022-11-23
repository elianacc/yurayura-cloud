package pers.elianacc.yurayura.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.dto.*;
import pers.elianacc.yurayura.vo.ApiResult;

/**
 * 系统模块 feign client
 *
 * @author ELiaNaCc
 * @since 2022-10-20
 */
@FeignClient(value = "yurayura-cloud-service-sys")
public interface SysFeignClient {

    @PostMapping("/sys/dict/getPage")
    public ApiResult getPage(@RequestBody SysDictSelectDto dto);

    @PostMapping("/sys/dict/insert")
    public ApiResult insert(@RequestBody SysDictInsertDto dto);

    @PutMapping("/sys/dict/update")
    public ApiResult update(@RequestBody SysDictUpdateDto dto);

    @GetMapping("/sys/dict/getByDictCode")
    public ApiResult getDictByDictCode(@RequestParam String dictCode);

    @GetMapping("/sys/dict/getAll")
    public ApiResult getAllDict();

//----------------------------------------------------------------------------------------------------------------------

    @PostMapping("/sys/manager/getPage")
    public ApiResult getPage(@RequestBody SysManagerSelectDto dto);

    @PostMapping("/sys/manager/insert")
    public ApiResult insert(@RequestBody SysManagerInsertDto dto);

    @PutMapping("/sys/manager/update")
    public ApiResult update(@RequestBody SysManagerUpdateDto dto);

    @RequestMapping("/sys/manager/getManagerRolePermission")
    public ApiResult getManagerRolePermission(@RequestParam Integer managerId);

    @RequestMapping("/sys/manager/getEnableManagerByName")
    public ApiResult getEnableManagerByName(@RequestParam String managerName);

//----------------------------------------------------------------------------------------------------------------------

    @GetMapping("/sys/menu/getTreeList")
    public ApiResult getMenuTreeList();

    @GetMapping("/sys/menu/getTreeListByManagerId")
    public ApiResult getMenuTreeListByManagerId(@RequestParam Integer managerId);

    @PostMapping("/sys/menu/insert")
    public ApiResult insert(@RequestBody SysMenuInsertDto dto);

    @PutMapping("/sys/menu/update")
    public ApiResult update(@RequestBody SysMenuUpdateDto dto);

    @PutMapping("/sys/menu/deleteById")
    public ApiResult deleteMenuById(@RequestBody IdDto dto);

//----------------------------------------------------------------------------------------------------------------------

    @PostMapping("/sys/menuSub/insert")
    public ApiResult insert(@RequestBody SysMenuSubInsertDto dto);

    @PutMapping("/sys/menuSub/update")
    public ApiResult update(@RequestBody SysMenuSubUpdateDto dto);

    @PutMapping("/sys/menuSub/deleteById")
    public ApiResult deleteMenuSubById(@RequestBody IdDto dto);

    @GetMapping("/sys/menuSub/getByIndex")
    public ApiResult getMenuSubByIndex(@RequestParam String index);

    @GetMapping("/sys/menuSub/getAll")
    public ApiResult getAllMenuSub();

//----------------------------------------------------------------------------------------------------------------------

    @PostMapping("/sys/permission/getPage")
    public ApiResult getPage(@RequestBody SysPermissionSelectDto dto);

    @PostMapping("/sys/permission/insert")
    public ApiResult insert(@RequestBody SysPermissionInsertDto dto);

    @PutMapping("/sys/permission/update")
    public ApiResult update(@RequestBody SysPermissionUpdateDto dto);

    @GetMapping("/sys/permission/getPermissionAuthorTree")
    public ApiResult getPermissionAuthorTree();

//----------------------------------------------------------------------------------------------------------------------

    @PostMapping("/sys/role/getPage")
    public ApiResult getPage(@RequestBody SysRoleSelectDto dto);

    @PostMapping("/sys/role/insert")
    public ApiResult insert(@RequestBody SysRoleInsertDto dto);

    @PutMapping("/sys/role/update")
    public ApiResult update(@RequestBody SysRoleUpdateDto dto);

    @GetMapping("/sys/role/getAll")
    public ApiResult getAllRole();

}
