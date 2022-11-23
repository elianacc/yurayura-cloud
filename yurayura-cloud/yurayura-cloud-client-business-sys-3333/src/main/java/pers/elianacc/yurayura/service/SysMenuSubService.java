package pers.elianacc.yurayura.service;

import pers.elianacc.yurayura.dto.IdDto;
import pers.elianacc.yurayura.dto.SysMenuSubInsertDto;
import pers.elianacc.yurayura.dto.SysMenuSubUpdateDto;
import pers.elianacc.yurayura.vo.ApiResult;

/**
 * 系统子菜单 service
 *
 * @author ELiaNaCc
 * @since 2022-11-15
 */
public interface SysMenuSubService {

    /**
     * 添加系统子菜单
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    public ApiResult insert(SysMenuSubInsertDto dto);

    /**
     * 删除系统子菜单（根据系统子菜单id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    public ApiResult deleteById(IdDto dto);

    /**
     * 修改系统子菜单
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    public ApiResult update(SysMenuSubUpdateDto dto);

    /**
     * 查询系统子菜单（根据路径）
     *
     * @param index
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    public ApiResult getByIndex(String index);

    /**
     * 查询所有系统子菜单
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    public ApiResult getAll();
}
