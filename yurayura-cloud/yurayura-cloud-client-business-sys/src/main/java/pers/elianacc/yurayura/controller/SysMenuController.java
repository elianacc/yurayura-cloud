package pers.elianacc.yurayura.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.lock.annotation.Lock4j;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.controller.block.SysMenuBlockHandler;
import pers.elianacc.yurayura.dto.IdDto;
import pers.elianacc.yurayura.dto.SysMenuInsertDto;
import pers.elianacc.yurayura.dto.SysMenuUpdateDto;
import pers.elianacc.yurayura.exception.BusinessException;
import pers.elianacc.yurayura.feign.SysFeignClient;
import pers.elianacc.yurayura.vo.ApiResult;
import pers.elianacc.yurayura.vo.SysMenuTreeVo;

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
    private SysFeignClient sysFeignClient;

    /**
     * 查询系统侧边菜单
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<List<SysMenuTreeVo>>
     */
    @GetMapping("/getSysSideMenu")
    @ApiOperation("查询系统侧边菜单")
    public ApiResult<List<SysMenuTreeVo>> getSysSideMenu() {
        ApiResult<List<SysMenuTreeVo>> apiResult = sysFeignClient.getMenuTreeListByManagerId(StpUtil.getLoginIdAsInt());
        if (apiResult.getCode() != ApiResult.SUCCESS_CODE) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    /**
     * 查询系统菜单树形列表
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<List<SysMenuTreeVo>>
     */
    @GetMapping("/getTreeList")
    @SentinelResource(value = "sys-menu-getTree",
            blockHandlerClass = SysMenuBlockHandler.class,
            blockHandler = "getTreeListBlockHandler")
    @ApiOperation("查询系统菜单树形列表")
    public ApiResult<List<SysMenuTreeVo>> getTreeList() {
        ApiResult<List<SysMenuTreeVo>> apiResult = sysFeignClient.getMenuTreeList();
        if (apiResult.getCode() != ApiResult.SUCCESS_CODE) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    /**
     * 添加系统菜单
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/insert")
    @Lock4j(keys = {"#dto.menuName"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("添加系统菜单")
    public ApiResult<String> insert(@Validated @RequestBody SysMenuInsertDto dto) {
        ApiResult<String> apiResult = sysFeignClient.insert(dto);
        if (apiResult.getCode() != ApiResult.SUCCESS_CODE) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    /**
     * 修改系统菜单
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/update")
    @Lock4j(keys = {"#dto.id"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("修改系统菜单")
    public ApiResult<String> update(@Validated @RequestBody SysMenuUpdateDto dto) {
        ApiResult<String> apiResult = sysFeignClient.update(dto);
        if (apiResult.getCode() != ApiResult.SUCCESS_CODE) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    /**
     * 删除系统菜单（根据系统菜单id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/deleteById")
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("删除系统菜单（根据系统菜单id）")
    public ApiResult<String> deleteById(@Validated @RequestBody IdDto dto) {
        ApiResult<String> apiResult = sysFeignClient.deleteMenuById(dto);
        if (apiResult.getCode() != ApiResult.SUCCESS_CODE) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

}
