package pers.elianacc.yurayura.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.lock.annotation.Lock4j;
import com.github.pagehelper.PageInfo;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.controller.block.SysPermissionBlockHandler;
import pers.elianacc.yurayura.dto.SysPermissionInsertDto;
import pers.elianacc.yurayura.dto.SysPermissionSelectDto;
import pers.elianacc.yurayura.dto.SysPermissionUpdateDto;
import pers.elianacc.yurayura.entity.sys.permission.SysPermission;
import pers.elianacc.yurayura.exception.BusinessException;
import pers.elianacc.yurayura.feign.SysFeignClient;
import pers.elianacc.yurayura.vo.ApiResult;
import pers.elianacc.yurayura.vo.SysPermissionAuthorTreeVo;

import java.util.List;

/**
 * 系统权限 controller
 *
 * @author ELiaNaCc
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/api/sys/permission")
@Api(tags = "系统权限API")
public class SysPermissionController {

    @Autowired
    private SysFeignClient sysFeignClient;

    /**
     * 分页查询系统权限
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<PageInfo<SysPermission>>
     */
    @PostMapping("/getPage")
    @SentinelResource(value = "sys-permission-getPage",
            blockHandlerClass = SysPermissionBlockHandler.class,
            blockHandler = "getPageBlockHandler")
    @ApiOperation("分页查询系统权限")
    public ApiResult<PageInfo<SysPermission>> getPage(@Validated @RequestBody SysPermissionSelectDto dto) {
        ApiResult<PageInfo<SysPermission>> apiResult = sysFeignClient.getPage(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    /**
     * 添加系统权限
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/insert")
    @Lock4j(keys = {"#dto.permissionType", "#dto.permissionBelongSubmenuName"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("添加系统权限")
    public ApiResult<String> insert(@Validated @RequestBody SysPermissionInsertDto dto) {
        ApiResult<String> apiResult = sysFeignClient.insert(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    /**
     * 修改系统权限
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/update")
    @Lock4j(keys = {"#dto.id"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("修改系统权限")
    public ApiResult<String> update(@Validated @RequestBody SysPermissionUpdateDto dto) {
        ApiResult<String> apiResult = sysFeignClient.update(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    /**
     * 查询权限授权树
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<pers.elianacc.yurayura.vo.SysPermissionAuthorTreeVo>>
     */
    @GetMapping("/getPermissionAuthorTree")
    @ApiOperation("查询权限授权树")
    public ApiResult<List<SysPermissionAuthorTreeVo>> getPermissionAuthorTree() {
        ApiResult<List<SysPermissionAuthorTreeVo>> apiResult = sysFeignClient.getPermissionAuthorTree();
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

}
