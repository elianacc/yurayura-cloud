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
import pers.elianacc.yurayura.controller.block.SysRoleBlockHandler;
import pers.elianacc.yurayura.dto.SysRoleInsertDto;
import pers.elianacc.yurayura.dto.SysRoleSelectDto;
import pers.elianacc.yurayura.dto.SysRoleUpdateDto;
import pers.elianacc.yurayura.entity.sys.role.SysRole;
import pers.elianacc.yurayura.exception.BusinessException;
import pers.elianacc.yurayura.feign.SysFeignClient;
import pers.elianacc.yurayura.vo.ApiResult;
import pers.elianacc.yurayura.vo.SysRoleAndPermissionVo;

import java.util.List;

/**
 * 系统角色 controller
 *
 * @author ELiaNaCc
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/api/sys/role")
@Api(tags = "系统角色API")
public class SysRoleController {

    @Autowired
    private SysFeignClient sysFeignClient;

    /**
     * 分页查询系统角色
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<com.github.pagehelper.PageInfo<pers.elianacc.yurayura.vo.SysRoleAndPermissionVo>>
     */
    @PostMapping("/getPage")
    @SentinelResource(value = "sys-role-getPage",
            blockHandlerClass = SysRoleBlockHandler.class,
            blockHandler = "getPageBlockHandler")
    @ApiOperation("分页查询系统角色")
    public ApiResult<PageInfo<SysRoleAndPermissionVo>> getPage(@Validated @RequestBody SysRoleSelectDto dto) {
        ApiResult<PageInfo<SysRoleAndPermissionVo>> apiResult = sysFeignClient.getPage(dto);
        if (apiResult.getCode() != ApiResult.SUCCESS_CODE) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    /**
     * 添加系统角色
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/insert")
    @Lock4j(keys = {"#dto.roleName"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("添加系统角色")
    public ApiResult<String> insert(@Validated @RequestBody SysRoleInsertDto dto) {
        ApiResult<String> apiResult = sysFeignClient.insert(dto);
        if (apiResult.getCode() != ApiResult.SUCCESS_CODE) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    /**
     * 修改系统角色
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/update")
    @Lock4j(keys = {"#dto.id"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("修改系统角色")
    public ApiResult<String> update(@Validated @RequestBody SysRoleUpdateDto dto) {
        ApiResult<String> apiResult = sysFeignClient.update(dto);
        if (apiResult.getCode() != ApiResult.SUCCESS_CODE) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    /**
     * 查询系统角色（根据组织）
     *
     * @param orgId
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<pers.elianacc.yurayura.entity.sys.role.SysRole>>
     */
    @GetMapping("/getByOrg/{orgId}")
    @ApiOperation("查询系统角色（根据组织）")
    public ApiResult<List<SysRole>> getByOrg(@PathVariable("orgId") Integer orgId) {
        ApiResult<List<SysRole>> apiResult = sysFeignClient.getByOrg(orgId);
        if (apiResult.getCode() != ApiResult.SUCCESS_CODE) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }
}
