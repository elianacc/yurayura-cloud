package pers.elianacc.yurayura.controller;


import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.dto.IdDto;
import pers.elianacc.yurayura.dto.SysManagerInsertDto;
import pers.elianacc.yurayura.dto.SysManagerSelectDto;
import pers.elianacc.yurayura.dto.SysManagerUpdateDto;
import pers.elianacc.yurayura.entity.sys.manager.SysManager;
import pers.elianacc.yurayura.service.ISysManagerService;
import pers.elianacc.yurayura.vo.ApiResult;
import pers.elianacc.yurayura.vo.SysManagerAndRoleVo;

import java.util.List;


/**
 * 系统管理员 controller
 *
 * @author ELiaNaCc
 * @since 2019-10-27
 */
@RestController
@RequestMapping("/sys/manager")
public class SysManagerController {

    @Autowired
    private ISysManagerService iSysManagerService;

    /**
     * 查询系统管理员（根据系统管理员id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<pers.elianacc.yurayura.entity.sys.manager.SysManager>
     */
    @GetMapping("/getById")
    public ApiResult<SysManager> getById(IdDto dto) {
        return ApiResult.success("查询成功", iSysManagerService.getById(dto.getId()));
    }

    /**
     * 分页查询系统管理员
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<com.github.pagehelper.PageInfo<pers.elianacc.yurayura.vo.SysManagerAndRoleVo>>
     */
    @PostMapping("/getPage")
    public ApiResult<PageInfo<SysManagerAndRoleVo>> getPage(@RequestBody SysManagerSelectDto dto) {
        return ApiResult.success("分页查询成功", iSysManagerService.getPage(dto));
    }

    /**
     * 添加系统管理员
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/insert")
    public ApiResult<String> insert(@RequestBody SysManagerInsertDto dto) {
        iSysManagerService.insert(dto);
        return ApiResult.success("添加成功");
    }

    /**
     * 修改系统管理员
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/update")
    public ApiResult<String> update(@RequestBody SysManagerUpdateDto dto) {
        iSysManagerService.update(dto);
        return ApiResult.success("修改成功");
    }

    /**
     * 查询管理员拥有角色的所有权限（根据管理员id）
     *
     * @param managerId
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<java.lang.String>>
     */
    @RequestMapping("/getManagerRolePermission")
    public ApiResult<List<String>> getManagerRolePermission(@RequestParam Integer managerId) {
        return ApiResult.success("查询成功", iSysManagerService.getManagerRolePermission(managerId));
    }

    /**
     * 查询启用的管理员（根据管理员名）
     *
     * @param managerName
     * @return pers.elianacc.yurayura.vo.ApiResult<pers.elianacc.yurayura.entity.sys.manager.SysManager>
     */
    @RequestMapping("/getEnableManagerByName")
    public ApiResult<SysManager> getEnableManagerByName(@RequestParam String managerName) {
        return ApiResult.success("查询成功", iSysManagerService.getEnableManagerByName(managerName));
    }

}

