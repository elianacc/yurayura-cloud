package pers.elianacc.yurayura.controller;

import com.baomidou.lock.annotation.Lock4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.dto.IdDto;
import pers.elianacc.yurayura.dto.SysMenuSubInsertDto;
import pers.elianacc.yurayura.dto.SysMenuSubUpdateDto;
import pers.elianacc.yurayura.service.SysMenuSubService;
import pers.elianacc.yurayura.vo.ApiResult;

/**
 * 系统子菜单 controller
 *
 * @author ELiaNaCc
 * @since 2022-11-15
 */
@RestController
@RequestMapping("/api/sys/menuSub")
@Api(tags = "系统子菜单API")
public class SysMenuSubController {

    @Autowired
    private SysMenuSubService sysMenuSubService;

    /**
     * 添加系统子菜单
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    @PostMapping("/insert")
    @Lock4j(keys = {"#dto.menuName"}, autoRelease = false)
    @ApiOperation("添加系统子菜单")
    public ApiResult insert(@RequestBody SysMenuSubInsertDto dto) {
        return sysMenuSubService.insert(dto);
    }

    /**
     * 修改系统子菜单
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    @PutMapping("/update")
    @Lock4j(keys = {"#dto.id"}, autoRelease = false)
    @ApiOperation("修改系统子菜单")
    public ApiResult update(@RequestBody SysMenuSubUpdateDto dto) {
        return sysMenuSubService.update(dto);
    }

    /**
     * 删除系统子菜单（根据系统子菜单id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    @PutMapping("/deleteById")
    @ApiOperation("删除系统子菜单（根据系统子菜单id）")
    public ApiResult deleteById(@RequestBody IdDto dto) {
        return sysMenuSubService.deleteById(dto);
    }

    /**
     * 查询系统子菜单（根据路径）
     *
     * @param index
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/getByIndex")
    @ApiOperation("查询系统子菜单（根据路径）")
    @ApiImplicitParam(name = "index", value = "路径", required = true, dataTypeClass = String.class)
    public ApiResult getByIndex(@RequestParam String index) {
        return sysMenuSubService.getByIndex(index);
    }

    /**
     * 查询所有系统子菜单
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/getAll")
    @ApiOperation("查询所有系统子菜单")
    public ApiResult getAll() {
        return sysMenuSubService.getAll();
    }
}
