package pers.elianacc.yurayura.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.dto.IdDto;
import pers.elianacc.yurayura.dto.SysMenuSubInsertDto;
import pers.elianacc.yurayura.dto.SysMenuSubUpdateDto;
import pers.elianacc.yurayura.entity.sys.menu.SysMenuSub;
import pers.elianacc.yurayura.service.ISysMenuSubService;
import pers.elianacc.yurayura.vo.ApiResult;

import java.util.List;

/**
 * 系统子菜单 controller
 *
 * @author ELiaNaCc
 * @since 2021-03-16
 */
@RestController
@RequestMapping("/sys/menuSub")
public class SysMenuSubController {

    @Autowired
    private ISysMenuSubService iSysMenuSubService;

    /**
     * 查询系统子菜单（根据系统子菜单id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<pers.elianacc.yurayura.entity.sys.menu.SysMenuSub>
     */
    @GetMapping("/getById")
    public ApiResult<SysMenuSub> getById(IdDto dto) {
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        }
        return ApiResult.success("查询成功", iSysMenuSubService.getById(dto.getId()));
    }

    /**
     * 添加系统子菜单
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/insert")
    public ApiResult<String> insert(@RequestBody SysMenuSubInsertDto dto) {
        if (ObjectUtils.isEmpty(dto.getMenuTitle())) {
            return ApiResult.warn("标题不能为空");
        } else if (ObjectUtils.isEmpty(dto.getMenuName())) {
            return ApiResult.warn("标识不能为空");
        } else if (ObjectUtils.isEmpty(dto.getMenuIconClass())) {
            return ApiResult.warn("图标样式不能为空");
        } else if (ObjectUtils.isEmpty(dto.getMenuSeq())) {
            return ApiResult.warn("序号不能为空");
        } else if (ObjectUtils.isEmpty(dto.getMenuIndex())) {
            return ApiResult.warn("路径不能为空");
        } else if (ObjectUtils.isEmpty(dto.getMenuPid())) {
            return ApiResult.warn("父菜单id不能为空");
        } else if (ObjectUtils.isEmpty(dto.getMenuStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (!dto.getMenuName().matches("^[a-z][a-z_]*$")) {
            return ApiResult.warn("标识只能包含小写字母下划线，以小写字母开头");
        } else if (dto.getMenuTitle().length() > 20 || dto.getMenuName().length() > 20 || dto.getMenuIconClass().length() > 30) {
            return ApiResult.warn("标题、标识不能超过20个字符，图标样式不能超过30个字符");
        }
        String warn = iSysMenuSubService.insert(dto);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("添加成功");
    }

    /**
     * 修改系统子菜单
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/update")
    public ApiResult<String> update(@RequestBody SysMenuSubUpdateDto dto) {
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        } else if (ObjectUtils.isEmpty(dto.getMenuTitle())) {
            return ApiResult.warn("标题不能为空");
        } else if (ObjectUtils.isEmpty(dto.getMenuIconClass())) {
            return ApiResult.warn("图标样式不能为空");
        } else if (ObjectUtils.isEmpty(dto.getMenuSeq())) {
            return ApiResult.warn("序号不能为空");
        } else if (ObjectUtils.isEmpty(dto.getMenuStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (dto.getMenuTitle().length() > 20 || dto.getMenuIconClass().length() > 30) {
            return ApiResult.warn("标题不能超过20个字符，图标样式不能超过30个字符");
        }
        String warn = iSysMenuSubService.update(dto);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("修改成功");
    }

    /**
     * 删除系统子菜单（根据系统子菜单id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/deleteById")
    public ApiResult<String> deleteById(@RequestBody IdDto dto) {
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        }
        iSysMenuSubService.deleteById(dto);
        return ApiResult.success("删除成功");
    }

    /**
     * 查询系统子菜单（根据路径）
     *
     * @param index
     * @return pers.elianacc.yurayura.vo.ApiResult<pers.elianacc.yurayura.entity.sys.menu.SysMenuSub>
     */
    @GetMapping("/getByIndex")
    public ApiResult<SysMenuSub> getByIndex(@RequestParam String index) {
        if (ObjectUtils.isEmpty(index)) {
            return ApiResult.warn("路径不能为空");
        }
        return ApiResult.success("查询成功", iSysMenuSubService.getByIndex(index));
    }

    /**
     * 查询所有系统子菜单
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<pers.elianacc.yurayura.entity.sys.menu.SysMenuSub>>
     */
    @GetMapping("/getAll")
    public ApiResult<List<SysMenuSub>> getAll() {
        return ApiResult.success("查询成功", iSysMenuSubService.getAll());
    }
}

