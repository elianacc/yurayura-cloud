package pers.elianacc.yurayura.service;

import pers.elianacc.yurayura.dto.SysRoleInsertDto;
import pers.elianacc.yurayura.dto.SysRoleSelectDto;
import pers.elianacc.yurayura.dto.SysRoleUpdateDto;
import pers.elianacc.yurayura.vo.ApiResult;

/**
 * 系统角色 service
 *
 * @author ELiaNaCc
 * @since 2022-11-16
 */
public interface SysRoleService {

    /**
     * 分页查询系统角色
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    public ApiResult getPage(SysRoleSelectDto dto);

    /**
     * 查询所有系统角色
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    public ApiResult getAll();

    /**
     * 添加系统角色
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    public ApiResult insert(SysRoleInsertDto dto);

    /**
     * 修改系统角色
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    public ApiResult update(SysRoleUpdateDto dto);

}
