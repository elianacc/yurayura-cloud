package pers.elianacc.yurayura.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import pers.elianacc.yurayura.dto.IdsDto;
import pers.elianacc.yurayura.dto.SysDictInsertDto;
import pers.elianacc.yurayura.dto.SysDictSelectDto;
import pers.elianacc.yurayura.dto.SysDictUpdateDto;
import pers.elianacc.yurayura.entity.sys.dict.SysDict;

import java.util.List;

/**
 * 系统数据字典 service
 *
 * @author ELiaNaCc
 * @since 2020-03-24
 */
public interface ISysDictService extends IService<SysDict> {

    /**
     * 分页查询系统数据字典
     *
     * @param dto
     * @return com.github.pagehelper.PageInfo<pers.elianacc.yurayura.entity.sys.dict.SysDict>
     */
    public PageInfo<SysDict> getPage(SysDictSelectDto dto);

    /**
     * 添加系统数据字典
     *
     * @param dto
     * @return void
     */
    public void insert(SysDictInsertDto dto);

    /**
     * 批量删除系统数据字典（根据系统数据字典id组）
     *
     * @param dto
     * @return void
     */
    public void deleteBatchByIds(IdsDto dto);

    /**
     * 修改系统数据字典
     *
     * @param dto
     * @return void
     */
    public void update(SysDictUpdateDto dto);

    /**
     * 查询系统数据字典（根据字典编码）
     *
     * @param dictCode
     * @return java.util.List<pers.elianacc.yurayura.entity.sys.dict.SysDict>
     */
    public List<SysDict> getByDictCode(String dictCode);

    /**
     * 查询所有系统数据字典（只从redis获取）
     *
     * @param
     * @return java.util.List<pers.elianacc.yurayura.entity.sys.dict.SysDict>
     */
    public List<SysDict> getAll();

}
