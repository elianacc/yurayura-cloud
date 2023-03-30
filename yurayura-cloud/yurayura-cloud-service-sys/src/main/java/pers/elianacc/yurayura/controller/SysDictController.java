package pers.elianacc.yurayura.controller;


import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
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
        PageInfo<SysDict> pageInfo = iSysDictService.getPage(dto);
        if (pageInfo.getTotal() == 0) {
            return ApiResult.warn("查询不到数据");
        }
        return ApiResult.success("分页查询成功", pageInfo);
    }

    /**
     * 添加系统数据字典
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/insert")
    public ApiResult<String> insert(@RequestBody SysDictInsertDto dto) {
        String warn = iSysDictService.insert(dto);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
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
        String warn = iSysDictService.update(dto);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
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
        if (ObjectUtils.isEmpty(dictCode)) {
            return ApiResult.badRequest("字典编码不能为空");
        }
        List<SysDict> sysDictList = iSysDictService.getByDictCode(dictCode);
        if (sysDictList.isEmpty()) {
            return ApiResult.warn("字典编码：" + dictCode + "对应系统数据字典为空");
        }
        return ApiResult.success("查询成功", sysDictList);
    }

    /**
     * 查询所有系统数据字典
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<pers.elianacc.yurayura.entity.sys.dict.SysDict>>
     */
    @GetMapping("/getAll")
    public ApiResult<List<SysDict>> getAll() {
        List<SysDict> sysDictList = iSysDictService.getAll();
        if (sysDictList.isEmpty()) {
            return ApiResult.warn("系统数据字典在redis中不存在，请添加");
        }
        return ApiResult.success("查询成功", sysDictList);
    }
}

