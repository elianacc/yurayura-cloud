package pers.elianacc.yurayura.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.lock.annotation.Lock4j;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.controller.block.SysDictBlockHandler;
import pers.elianacc.yurayura.dto.SysDictInsertDto;
import pers.elianacc.yurayura.dto.SysDictSelectDto;
import pers.elianacc.yurayura.dto.SysDictUpdateDto;
import pers.elianacc.yurayura.entity.sys.dict.SysDict;
import pers.elianacc.yurayura.service.SysDictService;
import pers.elianacc.yurayura.vo.ApiResult;

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
public class SysDictController {

    @Autowired
    private SysDictService sysDictService;

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
    public ApiResult<PageInfo<SysDict>> getPage(@RequestBody SysDictSelectDto dto) {
        return sysDictService.getPage(dto);
    }

    /**
     * 添加系统数据字典
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/insert")
    @Lock4j(keys = {"#dto.dictCode", "#dto.dictVal"}, autoRelease = false)
    @ApiOperation("添加系统数据字典")
    public ApiResult<String> insert(@RequestBody SysDictInsertDto dto) {
        return sysDictService.insert(dto);
    }

    /**
     * 修改系统数据字典
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/update")
    @Lock4j(keys = {"#dto.id"}, autoRelease = false)
    @ApiOperation("修改系统数据字典")
    public ApiResult<String> update(@RequestBody SysDictUpdateDto dto) {
        return sysDictService.update(dto);
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
    public ApiResult<List<SysDict>> getByDictCode(@RequestParam String dictCode) {
        return sysDictService.getByDictCode(dictCode);
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
        return sysDictService.getAll();
    }

}
