package pers.elianacc.yurayura.controller;

import com.baomidou.lock.annotation.Lock4j;
import com.github.pagehelper.PageInfo;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.dto.SysOrgInsertDto;
import pers.elianacc.yurayura.dto.SysOrgSelectDto;
import pers.elianacc.yurayura.dto.SysOrgUpdateDto;
import pers.elianacc.yurayura.entity.sys.org.SysOrg;
import pers.elianacc.yurayura.exception.BusinessException;
import pers.elianacc.yurayura.feign.SysFeignClient;
import pers.elianacc.yurayura.vo.ApiResult;

import java.util.List;

/**
 * 系统组织 controller
 *
 * @author ELiaNaCc
 * @since 2024-05-16
 */
@RestController
@RequestMapping("/api/sys/org")
@Api(tags = "系统组织API")
public class SysOrgController {

    @Autowired
    private SysFeignClient sysFeignClient;

    /**
     * 查询所有系统组织
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<pers.elianacc.yurayura.entity.sys.org.SysOrg>>
     */
    @GetMapping("/getAll")
    @ApiOperation("查询所有系统组织")
    public ApiResult<List<SysOrg>> getAll() {
        ApiResult<List<SysOrg>> apiResult = sysFeignClient.getAllOrg();
        if (apiResult.getCode() != ApiResult.SUCCESS_CODE) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    /**
     * 分页查询系统组织
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<com.github.pagehelper.PageInfo<pers.elianacc.yurayura.entity.sys.org.SysOrg>>
     */
    @PostMapping("/getPage")
    @ApiOperation("分页查询系统组织")
    public ApiResult<PageInfo<SysOrg>> getPage(@Validated @RequestBody SysOrgSelectDto dto) {
        ApiResult<PageInfo<SysOrg>> apiResult = sysFeignClient.getPage(dto);
        if (apiResult.getCode() != ApiResult.SUCCESS_CODE) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    /**
     * 添加系统组织
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/insert")
    @Lock4j(keys = {"#dto.orgName"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("添加系统组织")
    public ApiResult<String> insert(@Validated @RequestBody SysOrgInsertDto dto) {
        ApiResult<String> apiResult = sysFeignClient.insert(dto);
        if (apiResult.getCode() != ApiResult.SUCCESS_CODE) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    /**
     * 修改系统组织
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/update")
    @Lock4j(keys = {"#dto.id"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("修改系统组织")
    public ApiResult<String> update(@Validated @RequestBody SysOrgUpdateDto dto) {
        ApiResult<String> apiResult = sysFeignClient.update(dto);
        if (apiResult.getCode() != ApiResult.SUCCESS_CODE) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

}
