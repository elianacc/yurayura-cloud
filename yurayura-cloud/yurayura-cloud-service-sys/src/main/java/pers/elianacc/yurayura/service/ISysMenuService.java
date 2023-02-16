package pers.elianacc.yurayura.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.elianacc.yurayura.vo.SysMenuTreeSelectVo;
import pers.elianacc.yurayura.dto.IdDto;
import pers.elianacc.yurayura.dto.SysMenuInsertDto;
import pers.elianacc.yurayura.dto.SysMenuUpdateDto;
import pers.elianacc.yurayura.entity.sys.menu.SysMenu;

import java.util.List;

/**
 * 系统菜单 service
 *
 * @author ELiaNaCc
 * @since 2021-03-16
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 查询系统菜单树形列表
     *
     * @param
     * @return java.util.List<pers.elianacc.yurayura.bo.MenuBo>
     */
    public List<SysMenuTreeSelectVo> getTreeList();

    /**
     * 查询系统菜单树形列表（根据管理员id）
     *
     * @param managerId
     * @return java.util.List<pers.elianacc.yurayura.vo.SysMenuTreeSelectBo>
     */
    public List<SysMenuTreeSelectVo> getTreeListByManagerId(Integer managerId);

    /**
     * 添加系统菜单
     *
     * @param dto
     * @return java.lang.String
     */
    public String insert(SysMenuInsertDto dto);

    /**
     * 删除系统菜单（根据系统菜单id）
     *
     * @param dto
     * @return void
     */
    public void deleteById(IdDto dto);

    /**
     * 修改系统菜单
     *
     * @param dto
     * @return java.lang.String
     */
    public String update(SysMenuUpdateDto dto);
}
