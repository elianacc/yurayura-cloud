package pers.elianacc.yurayura.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.elianacc.yurayura.bo.SysMenuTreeSelectBo;
import pers.elianacc.yurayura.dao.SysMenuMapper;
import pers.elianacc.yurayura.dao.SysMenuSubMapper;
import pers.elianacc.yurayura.dao.SysPermissionMapper;
import pers.elianacc.yurayura.dao.SysRoleMapper;
import pers.elianacc.yurayura.dto.IdDto;
import pers.elianacc.yurayura.dto.SysMenuInsertDto;
import pers.elianacc.yurayura.dto.SysMenuUpdateDto;
import pers.elianacc.yurayura.entity.sys.menu.SysMenu;
import pers.elianacc.yurayura.entity.sys.menu.SysMenuSub;
import pers.elianacc.yurayura.entity.sys.permission.SysPermission;
import pers.elianacc.yurayura.enumerate.SysMenuTypeEnum;
import pers.elianacc.yurayura.service.ISysMenuService;

import java.util.List;

/**
 * 系统菜单 service impl
 *
 * @author ELiaNaCc
 * @since 2021-03-16
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysMenuSubMapper sysMenuSubMapper;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<SysMenuTreeSelectBo> getTreeList() {
        return sysMenuMapper.getTreeList();
    }

    @Override
    public List<SysMenuTreeSelectBo> getTreeListByManagerId(Integer managerId) {
        return sysMenuMapper.getTreeListByManagerId(managerId);
    }

    @Override
    public String insert(SysMenuInsertDto dto) {
        String warn = "";
        List<String> menuNameList = sysMenuMapper.getMenuNameAndMenuSubName();
        if (menuNameList.contains(dto.getMenuName())) {
            warn = "菜单标识已存在，请更换";
        } else {
            SysMenu sysMenu = new SysMenu();
            BeanUtils.copyProperties(dto, sysMenu);
            sysMenu.setMenuType(SysMenuTypeEnum.FIRSTLEVEL.getTypeId());
            sysMenuMapper.insert(sysMenu);
        }
        return warn;
    }

    @Override
    public void deleteById(IdDto dto) {
        sysMenuMapper.deleteById(dto.getId());
        List<SysMenuSub> deleteSysMenuSubs = sysMenuSubMapper.selectList(Wrappers.<SysMenuSub>lambdaQuery()
                .eq(SysMenuSub::getMenuPid, dto.getId()));
        deleteSysMenuSubs.forEach(menuSub -> {
            List<SysPermission> deleteSysPermissions = sysPermissionMapper.selectList(Wrappers.<SysPermission>lambdaQuery()
                    .eq(SysPermission::getPermissionBelongSubmenuName, menuSub.getMenuName()));
            deleteSysPermissions.forEach(permission -> sysRoleMapper.deleteRolePermissionByPermissionId(permission.getId()));
            sysPermissionMapper.delete(Wrappers.<SysPermission>lambdaQuery()
                    .eq(SysPermission::getPermissionBelongSubmenuName, menuSub.getMenuName()));
        });
        sysMenuSubMapper.delete(Wrappers.<SysMenuSub>lambdaQuery().eq(SysMenuSub::getMenuPid, dto.getId()));
    }

    @Override
    public String update(SysMenuUpdateDto dto) {
        String warn = "";
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(dto, sysMenu);
        sysMenuMapper.updateById(sysMenu);
        return warn;
    }
}
