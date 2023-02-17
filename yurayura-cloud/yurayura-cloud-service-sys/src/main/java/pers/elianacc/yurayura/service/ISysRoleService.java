package pers.elianacc.yurayura.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import pers.elianacc.yurayura.dto.SysRoleInsertDto;
import pers.elianacc.yurayura.dto.SysRoleSelectDto;
import pers.elianacc.yurayura.dto.SysRoleUpdateDto;
import pers.elianacc.yurayura.entity.sys.role.SysRole;
import pers.elianacc.yurayura.vo.SysRoleAndPermissionVo;

import java.util.List;
import java.util.Map;

/**
 * 系统角色 service
 *
 * @author ELiaNaCc
 * @since 2022-03-07
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 分页查询系统角色
     *
     * @param dto
     * @return com.github.pagehelper.PageInfo<pers.elianacc.yurayura.vo.SysRoleAndPermissionVo>
     */
    public PageInfo<SysRoleAndPermissionVo> getPage(SysRoleSelectDto dto);

    /**
     * 查询所有系统角色
     *
     * @param
     * @return java.util.List<pers.elianacc.yurayura.entity.sys.role.SysRole>
     */
    public List<SysRole> getAll();

    /**
     * 添加系统角色
     *
     * @param dto
     * @return java.lang.String
     */
    public String insert(SysRoleInsertDto dto);

    /**
     * 修改系统角色
     *
     * @param dto
     * @return java.lang.String
     */
    public String update(SysRoleUpdateDto dto);

}
