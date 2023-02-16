package pers.elianacc.yurayura.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.lock.annotation.Lock4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.controller.block.SysMenuBlockHandler;
import pers.elianacc.yurayura.dto.IdDto;
import pers.elianacc.yurayura.dto.SysMenuInsertDto;
import pers.elianacc.yurayura.dto.SysMenuUpdateDto;
import pers.elianacc.yurayura.service.SysMenuService;
import pers.elianacc.yurayura.vo.ApiResult;
import pers.elianacc.yurayura.vo.SysMenuTreeSelectVo;

import java.util.List;

/**
 * 系统菜单 controller
 *
 * @author ELiaNaCc
 * @since 2022-10-26
 */
@RestController
@RequestMapping("/api/sys/menu")
@Api(tags = "系统菜单API")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 查询系统侧边菜单
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<List<SysMenuTreeSelectVo>>
     */
    @GetMapping("/getSysSideMenu")
    @ApiOperation("查询系统侧边菜单")
    public ApiResult<List<SysMenuTreeSelectVo>> getSysSideMenu() {
        return sysMenuService.getTreeListForCurrentManager();
    }

    /**
     * 查询系统菜单树形列表
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<List<SysMenuTreeSelectVo>>
     */
    @GetMapping("/getTreeList")
    @SentinelResource(value = "sys-menu-getTree",
            blockHandlerClass = SysMenuBlockHandler.class,
            blockHandler = "getTreeListBlockHandler")
    @ApiOperation("查询系统菜单树形列表")
    public ApiResult<List<SysMenuTreeSelectVo>> getTreeList() {
        return sysMenuService.getTreeList();
    }

    /**
     * 添加系统菜单
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/insert")
    @Lock4j(keys = {"#dto.menuName"}, autoRelease = false)
    @ApiOperation("添加系统菜单")
    public ApiResult<String> insert(@RequestBody SysMenuInsertDto dto) {
        return sysMenuService.insert(dto);
    }

    /**
     * 修改系统菜单
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/update")
    @Lock4j(keys = {"#dto.id"}, autoRelease = false)
    @ApiOperation("修改系统菜单")
    public ApiResult<String> update(@RequestBody SysMenuUpdateDto dto) {
        return sysMenuService.update(dto);
    }

    /**
     * 删除系统菜单（根据系统菜单id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/deleteById")
    @ApiOperation("删除系统菜单（根据系统菜单id）")
    public ApiResult<String> deleteById(@RequestBody IdDto dto) {
        return sysMenuService.deleteById(dto);
    }

}
