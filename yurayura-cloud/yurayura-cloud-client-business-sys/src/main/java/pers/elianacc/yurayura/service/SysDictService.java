package pers.elianacc.yurayura.service;

import pers.elianacc.yurayura.dto.SysDictInsertDto;
import pers.elianacc.yurayura.dto.SysDictSelectDto;
import pers.elianacc.yurayura.dto.SysDictUpdateDto;
import pers.elianacc.yurayura.vo.ApiResult;

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
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    public ApiResult getPage(SysDictSelectDto dto);

    /**
     * 添加系统数据字典
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    public ApiResult insert(SysDictInsertDto dto);

    /**
     * 修改系统数据字典
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    public ApiResult update(SysDictUpdateDto dto);

    /**
     * 查询系统数据字典（根据字典编码）
     *
     * @param dictCode
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    public ApiResult getByDictCode(String dictCode);

    /**
     * 查询所有系统数据字典（只从redis获取）
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    public ApiResult getAll();

}
