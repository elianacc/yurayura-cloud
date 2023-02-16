package pers.elianacc.yurayura.service;

import com.github.pagehelper.PageInfo;
import pers.elianacc.yurayura.dto.SysDictInsertDto;
import pers.elianacc.yurayura.dto.SysDictSelectDto;
import pers.elianacc.yurayura.dto.SysDictUpdateDto;
import pers.elianacc.yurayura.entity.sys.dict.SysDict;
import pers.elianacc.yurayura.vo.ApiResult;

import java.util.List;

/**
 * 系统数据字典 service
 *
 * @author ELiaNaCc
 * @since 2022-10-20
 */
public interface SysDictService {

    /**
     * 分页查询系统数据字典
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<PageInfo<SysDict>>
     */
    public ApiResult<PageInfo<SysDict>> getPage(SysDictSelectDto dto);

    /**
     * 添加系统数据字典
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    public ApiResult<String> insert(SysDictInsertDto dto);

    /**
     * 修改系统数据字典
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    public ApiResult<String> update(SysDictUpdateDto dto);

    /**
     * 查询系统数据字典（根据字典编码）
     *
     * @param dictCode
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<pers.elianacc.yurayura.entity.sys.dict.SysDict>>
     */
    public ApiResult<List<SysDict>> getByDictCode(String dictCode);

    /**
     * 查询所有系统数据字典（只从redis获取）
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<pers.elianacc.yurayura.entity.sys.dict.SysDict>>
     */
    public ApiResult<List<SysDict>> getAll();

}
