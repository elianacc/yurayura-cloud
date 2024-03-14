package pers.elianacc.yurayura.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.lock.annotation.Lock4j;
import com.github.pagehelper.PageInfo;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.controller.block.SysDictBlockHandler;
import pers.elianacc.yurayura.dto.SysDictInsertDto;
import pers.elianacc.yurayura.dto.SysDictSelectDto;
import pers.elianacc.yurayura.dto.SysDictUpdateDto;
import pers.elianacc.yurayura.entity.sys.dict.SysDict;
import pers.elianacc.yurayura.exception.BusinessException;
import pers.elianacc.yurayura.feign.SysFeignClient;
import pers.elianacc.yurayura.vo.ApiResult;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 系统数据字典 controller
 *
 * @author ELiaNaCc
 * @since 2022-10-21
 */
@RestController
@RequestMapping("/api/sys/dict")
@Api(tags = "系统数据字典API")
@Validated
public class SysDictController {

    @Autowired
    private SysFeignClient sysFeignClient;

    /**
     * 分页查询系统数据字典
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<PageInfo<SysDict>>
     */
    @PostMapping("/getPage")
    @SentinelResource(value = "sys-dict-getPage",
            blockHandlerClass = SysDictBlockHandler.class,
            blockHandler = "getPageBlockHandler")
    @ApiOperation("分页查询系统数据字典")
    public ApiResult<PageInfo<SysDict>> getPage(@Validated @RequestBody SysDictSelectDto dto) {
        ApiResult<PageInfo<SysDict>> apiResult = sysFeignClient.getPage(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    /**
     * 添加系统数据字典
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/insert")
    @Lock4j(keys = {"#dto.dictCode", "#dto.dictVal"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("添加系统数据字典")
    public ApiResult<String> insert(@Validated @RequestBody SysDictInsertDto dto) {
        ApiResult<String> apiResult = sysFeignClient.insert(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    /**
     * 修改系统数据字典
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/update")
    @Lock4j(keys = {"#dto.id"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("修改系统数据字典")
    public ApiResult<String> update(@Validated @RequestBody SysDictUpdateDto dto) {
        ApiResult<String> apiResult = sysFeignClient.update(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    /**
     * 查询系统数据字典（根据字典编码）
     *
     * @param dictCode
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<pers.elianacc.yurayura.entity.sys.dict.SysDict>>
     */
    @GetMapping("/getByDictCode")
    @ApiOperation("查询系统数据字典（根据字典编码）")
    @ApiImplicitParam(name = "dictCode", value = "字典编码", required = true, dataTypeClass = String.class)
    public ApiResult<List<SysDict>> getByDictCode(@NotBlank(message = "字典编码不能为空") @RequestParam String dictCode) {
        ApiResult<List<SysDict>> apiResult = sysFeignClient.getDictByDictCode(dictCode);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    /**
     * 查询所有系统数据字典
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<pers.elianacc.yurayura.entity.sys.dict.SysDict>>
     */
    @GetMapping("/getAll")
    @ApiOperation("查询所有系统数据字典")
    public ApiResult<List<SysDict>> getAll() {
        ApiResult<List<SysDict>> apiResult = sysFeignClient.getAllDict();
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

}
