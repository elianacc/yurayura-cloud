package pers.elianacc.yurayura.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import pers.elianacc.yurayura.vo.SysPermissionAuthorTreeSelectVo;
import pers.elianacc.yurayura.dto.SysPermissionInsertDto;
import pers.elianacc.yurayura.dto.SysPermissionSelectDto;
import pers.elianacc.yurayura.dto.SysPermissionUpdateDto;
import pers.elianacc.yurayura.entity.sys.permission.SysPermission;

import java.util.List;

/**
 * 系统权限 service
 *
 * @author ELiaNaCc
 * @since 2021-08-05
 */
public interface ISysPermissionService extends IService<SysPermission> {

    /**
     * 分页查询系统权限
     *
     * @param dto
     * @return com.github.pagehelper.PageInfo<pers.elianacc.yurayura.entity.sys.permission.SysPermission>
     */
    public PageInfo<SysPermission> getPage(SysPermissionSelectDto dto);

    /**
     * 添加系统权限
     *
     * @param dto
     * @return java.lang.String
     */
    public String insert(SysPermissionInsertDto dto);

    /**
     * 修改系统权限
     *
     * @param dto
     * @return java.lang.String
     */
    public String update(SysPermissionUpdateDto dto);

    /**
     * 查询权限授权树
     *
     * @param
     * @return java.util.List<pers.elianacc.yurayura.vo.SysPermissionAuthorTreeSelectBo>
     */
    public List<SysPermissionAuthorTreeSelectVo> getPermissionAuthorTree();

}
