package pers.elianacc.yurayura.service;

import pers.elianacc.yurayura.dto.IdDto;
import pers.elianacc.yurayura.dto.SysMenuInsertDto;
import pers.elianacc.yurayura.dto.SysMenuUpdateDto;
import pers.elianacc.yurayura.vo.ApiResult;

/**
 * 系统菜单 service
 *
 * @author ELiaNaCc
 * @since 2022-10-26
 */
public interface SysMenuService {

    /**
     * 查询系统菜单树形列表
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    public ApiResult getTreeList();

    /**
     * 查询系统菜单树形列表（当前管理员有权限的）
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    public ApiResult getTreeListForCurrentManager();

    /**
     * 添加系统菜单
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    public ApiResult insert(SysMenuInsertDto dto);

    /**
     * 修改系统菜单
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    public ApiResult update(SysMenuUpdateDto dto);

    /**
     * 删除系统菜单（根据系统菜单id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    public ApiResult deleteById(IdDto dto);

}
