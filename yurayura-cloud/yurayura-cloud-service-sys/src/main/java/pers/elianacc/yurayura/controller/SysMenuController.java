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
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        }
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
            return ApiResult.warn("id不能为空");
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
        if (ObjectUtils.isEmpty(dto.getMenuTitle())) {
            return ApiResult.warn("标题不能为空");
        } else if (ObjectUtils.isEmpty(dto.getMenuName())) {
            return ApiResult.warn("标识不能为空");
        } else if (ObjectUtils.isEmpty(dto.getMenuIconClass())) {
            return ApiResult.warn("图标样式不能为空");
        } else if (ObjectUtils.isEmpty(dto.getMenuSeq())) {
            return ApiResult.warn("序号不能为空");
        } else if (ObjectUtils.isEmpty(dto.getMenuStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (!dto.getMenuName().matches("^[a-z][a-z_]*$")) {
            return ApiResult.warn("标识只能包含小写字母下划线，以小写字母开头");
        } else if (dto.getMenuTitle().length() > 20 || dto.getMenuName().length() > 20 || dto.getMenuIconClass().length() > 30) {
            return ApiResult.warn("标题、标识不能超过20个字符，图标样式不能超过30个字符");
        }
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
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        } else if (ObjectUtils.isEmpty(dto.getMenuTitle())) {
            return ApiResult.warn("标题不能为空");
        } else if (ObjectUtils.isEmpty(dto.getMenuIconClass())) {
            return ApiResult.warn("图标样式不能为空");
        } else if (ObjectUtils.isEmpty(dto.getMenuStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (ObjectUtils.isEmpty(dto.getMenuSeq())) {
            return ApiResult.warn("序号不能为空");
        } else if (dto.getMenuTitle().length() > 20 || dto.getMenuIconClass().length() > 30) {
            return ApiResult.warn("标题不能超过20个字符，图标样式不能超过30个字符");
        }
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
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        }
        iSysMenuService.deleteById(dto);
        return ApiResult.success("删除成功");
    }
}

