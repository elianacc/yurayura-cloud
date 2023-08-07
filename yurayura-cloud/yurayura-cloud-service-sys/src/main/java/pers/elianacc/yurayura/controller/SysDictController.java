package pers.elianacc.yurayura.controller;


import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.dto.IdDto;
import pers.elianacc.yurayura.dto.SysDictInsertDto;
import pers.elianacc.yurayura.dto.SysDictSelectDto;
import pers.elianacc.yurayura.dto.SysDictUpdateDto;
import pers.elianacc.yurayura.entity.sys.dict.SysDict;
import pers.elianacc.yurayura.service.ISysDictService;
import pers.elianacc.yurayura.vo.ApiResult;

import java.util.List;

/**
 * 系统数据字典 controller
 *
 * @author ELiaNaCc
 * @since 2020-03-24
 */
@RestController
@RequestMapping("/sys/dict")
public class SysDictController {

    @Autowired
    private ISysDictService iSysDictService;

    /**
     * 查询系统数据字典（根据系统数据字典id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<pers.elianacc.yurayura.entity.sys.dict.SysDict>
     */
    @GetMapping("/getById")
    public ApiResult<SysDict> getById(IdDto dto) {
        return ApiResult.success("查询成功", iSysDictService.getById(dto.getId()));
    }

    /**
     * 分页查询系统数据字典
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<PageInfo<SysDict>>
     */
    @PostMapping("/getPage")
    public ApiResult<PageInfo<SysDict>> getPage(@RequestBody SysDictSelectDto dto) {
        return ApiResult.success("分页查询成功", iSysDictService.getPage(dto));
    }

    /**
     * 添加系统数据字典
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/insert")
    public ApiResult<String> insert(@RequestBody SysDictInsertDto dto) {
        iSysDictService.insert(dto);
        return ApiResult.success("添加成功");
    }

    /**
     * 修改系统数据字典
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/update")
    public ApiResult<String> update(@RequestBody SysDictUpdateDto dto) {
        iSysDictService.update(dto);
        return ApiResult.success("修改成功");
    }

    /**
     * 查询系统数据字典（根据字典编码）
     *
     * @param dictCode
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<pers.elianacc.yurayura.entity.sys.dict.SysDict>>
     */
    @GetMapping("/getByDictCode")
    public ApiResult<List<SysDict>> getByDictCode(@RequestParam String dictCode) {
        return ApiResult.success("查询成功", iSysDictService.getByDictCode(dictCode));
    }

    /**
     * 查询所有系统数据字典
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<pers.elianacc.yurayura.entity.sys.dict.SysDict>>
     */
    @GetMapping("/getAll")
    public ApiResult<List<SysDict>> getAll() {
        return ApiResult.success("查询成功", iSysDictService.getAll());
    }
}

