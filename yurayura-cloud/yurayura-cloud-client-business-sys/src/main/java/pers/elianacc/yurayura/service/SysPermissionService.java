package pers.elianacc.yurayura.service;

import com.github.pagehelper.PageInfo;
import pers.elianacc.yurayura.dto.SysPermissionInsertDto;
import pers.elianacc.yurayura.dto.SysPermissionSelectDto;
import pers.elianacc.yurayura.dto.SysPermissionUpdateDto;
import pers.elianacc.yurayura.entity.sys.permission.SysPermission;
import pers.elianacc.yurayura.vo.ApiResult;
import pers.elianacc.yurayura.vo.SysPermissionAuthorTreeSelectVo;

import java.util.List;

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
     * @return pers.elianacc.yurayura.vo.ApiResult<PageInfo<SysPermission>>
     */
    public ApiResult<PageInfo<SysPermission>> getPage(SysPermissionSelectDto dto);

    /**
     * 添加系统权限
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    public ApiResult<String> insert(SysPermissionInsertDto dto);

    /**
     * 修改系统权限
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    public ApiResult<String> update(SysPermissionUpdateDto dto);

    /**
     * 查询权限授权树
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<pers.elianacc.yurayura.vo.SysPermissionAuthorTreeSelectVo>>
     */
    public ApiResult<List<SysPermissionAuthorTreeSelectVo>> getPermissionAuthorTree();
}
