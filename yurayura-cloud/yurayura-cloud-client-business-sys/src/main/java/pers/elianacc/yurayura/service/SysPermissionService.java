package pers.elianacc.yurayura.service;

import pers.elianacc.yurayura.dto.SysPermissionInsertDto;
import pers.elianacc.yurayura.dto.SysPermissionSelectDto;
import pers.elianacc.yurayura.dto.SysPermissionUpdateDto;
import pers.elianacc.yurayura.vo.ApiResult;

/**
 * 系统权限 service
 *
 * @author ELiaNaCc
 * @since 2022-11-16
 */
public interface SysPermissionService {

    /**
     * 分页查询系统权限
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    public ApiResult getPage(SysPermissionSelectDto dto);

    /**
     * 添加系统权限
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    public ApiResult insert(SysPermissionInsertDto dto);

    /**
     * 修改系统权限
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    public ApiResult update(SysPermissionUpdateDto dto);

    /**
     * 查询权限授权树
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    public ApiResult getPermissionAuthorTree();
}
