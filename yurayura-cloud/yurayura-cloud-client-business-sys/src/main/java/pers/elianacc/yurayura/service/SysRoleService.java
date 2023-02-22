package pers.elianacc.yurayura.service;

import com.github.pagehelper.PageInfo;
import pers.elianacc.yurayura.dto.SysRoleInsertDto;
import pers.elianacc.yurayura.dto.SysRoleSelectDto;
import pers.elianacc.yurayura.dto.SysRoleUpdateDto;
import pers.elianacc.yurayura.entity.sys.role.SysRole;
import pers.elianacc.yurayura.vo.ApiResult;
import pers.elianacc.yurayura.vo.SysRoleAndPermissionVo;

import java.util.List;

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
     * @return pers.elianacc.yurayura.vo.ApiResult<com.github.pagehelper.PageInfo<pers.elianacc.yurayura.vo.SysRoleAndPermissionVo>>
     */
    public ApiResult<PageInfo<SysRoleAndPermissionVo>> getPage(SysRoleSelectDto dto);

    /**
     * 查询所有系统角色
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<pers.elianacc.yurayura.entity.sys.role.SysRole>>
     */
    public ApiResult<List<SysRole>> getAll();

    /**
     * 添加系统角色
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    public ApiResult<String> insert(SysRoleInsertDto dto);

    /**
     * 修改系统角色
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    public ApiResult<String> update(SysRoleUpdateDto dto);

}
