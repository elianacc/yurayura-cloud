package pers.elianacc.yurayura.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.dto.IdDto;
import pers.elianacc.yurayura.dto.SysMenuInsertDto;
import pers.elianacc.yurayura.dto.SysMenuUpdateDto;
import pers.elianacc.yurayura.entity.sys.menu.SysMenu;
import pers.elianacc.yurayura.service.ISysMenuService;
import pers.elianacc.yurayura.vo.ApiResult;
import pers.elianacc.yurayura.vo.SysMenuTreeVo;

import java.util.List;

/**
 * 系统菜单 controller
 *
 * @author ELiaNaCc
 * @since 2021-03-16
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {

    @Autowired
    private ISysMenuService iSysMenuService;

    /**
     * 查询系统菜单（根据系统菜单id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<SysMenu>
     */
    @GetMapping("/getById")
    public ApiResult<SysMenu> getById(IdDto dto) {
        return ApiResult.success("查询成功", iSysMenuService.getById(dto.getId()));
    }

    /**
     * 查询系统菜单树形列表
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<List<SysMenuTreeVo>>
     */
    @GetMapping("/getTreeList")
    public ApiResult<List<SysMenuTreeVo>> getTreeList() {
        return ApiResult.success("树形列表查询成功", iSysMenuService.getTreeList());
    }

    /**
     * 查询系统菜单树形列表（根据管理员id）
     *
     * @param managerId
     * @return pers.elianacc.yurayura.vo.ApiResult<List<SysMenuTreeVo>>
     */
    @GetMapping("/getTreeListByManagerId")
    public ApiResult<List<SysMenuTreeVo>> getTreeListByManagerId(@RequestParam Integer managerId) {
        if (ObjectUtils.isEmpty(managerId)) {
            return ApiResult.badRequest("id不能为空");
        }
        return ApiResult.success("树形列表查询成功", iSysMenuService.getTreeListByManagerId(managerId));
    }

    /**
     * 添加系统菜单
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/insert")
    public ApiResult<String> insert(@RequestBody SysMenuInsertDto dto) {
        String warn = iSysMenuService.insert(dto);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("添加成功");
    }

    /**
     * 修改系统菜单
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/update")
    public ApiResult<String> update(@RequestBody SysMenuUpdateDto dto) {
        String warn = iSysMenuService.update(dto);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("修改成功");
    }

    /**
     * 删除系统菜单（根据系统菜单id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/deleteById")
    public ApiResult<String> deleteById(@RequestBody IdDto dto) {
        iSysMenuService.deleteById(dto);
        return ApiResult.success("删除成功");
    }
}

