package pers.elianacc.yurayura.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import pers.elianacc.yurayura.dao.SysMenuMapper;
import pers.elianacc.yurayura.dao.SysMenuSubMapper;
import pers.elianacc.yurayura.dao.SysPermissionMapper;
import pers.elianacc.yurayura.dao.SysRoleMapper;
import pers.elianacc.yurayura.dto.IdDto;
import pers.elianacc.yurayura.dto.SysMenuSubInsertDto;
import pers.elianacc.yurayura.dto.SysMenuSubUpdateDto;
import pers.elianacc.yurayura.entity.sys.menu.SysMenuSub;
import pers.elianacc.yurayura.entity.sys.permission.SysPermission;
import pers.elianacc.yurayura.enumerate.EnableStatusEnum;
import pers.elianacc.yurayura.enumerate.SysMenuTypeEnum;
import pers.elianacc.yurayura.enumerate.SysPermissionTypeEnum;
import pers.elianacc.yurayura.service.ISysMenuSubService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统子菜单 service impl
 *
 * @author ELiaNaCc
 * @since 2021-03-16
 */
@Service
public class SysMenuSubServiceImpl extends ServiceImpl<SysMenuSubMapper, SysMenuSub> implements ISysMenuSubService {

    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysMenuSubMapper sysMenuSubMapper;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public String insert(SysMenuSubInsertDto dto) {
        String warn = "";
        if (!("/business/" + dto.getMenuName()).equals(dto.getMenuIndex())) {
            warn = "菜单路径不正确";
        } else {
            List<String> menuNameList = sysMenuMapper.getMenuNameAndMenuSubName();
            if (menuNameList.contains(dto.getMenuName())) {
                warn = "菜单标识已存在，请更换";
            } else {
                SysMenuSub sysMenuSub = new SysMenuSub();
                BeanUtils.copyProperties(dto, sysMenuSub);
                sysMenuSub.setMenuType(SysMenuTypeEnum.SECONDLEVEL.getTypeId());
                sysMenuSubMapper.insert(sysMenuSub);
                SysPermission sysPermission = new SysPermission();
                sysPermission.setPermissionCode(sysMenuSub.getMenuName() + "_select");
                sysPermission.setPermissionName(sysMenuSub.getMenuTitle() + "查看");
                sysPermission.setPermissionType(SysPermissionTypeEnum.MENU.getTypeId());
                sysPermission.setPermissionStatus(EnableStatusEnum.ENABLE.getStatusId());
                sysPermission.setPermissionBelongSubmenuName(sysMenuSub.getMenuName());
                sysPermission.setPermissionSeq(1);
                sysPermissionMapper.insert(sysPermission);
                sysRoleMapper.insertRolePermissionForAdmin(sysPermission.getId());
            }
        }
        return warn;
    }

    @Override
    public void deleteById(IdDto dto) {
        SysMenuSub deleteSysMenuSub = sysMenuSubMapper.selectById(dto.getId());
        QueryWrapper<SysPermission> permissionQueryWrapper = new QueryWrapper<>();
        List<SysPermission> deleteSysPermissions = sysPermissionMapper.selectList(permissionQueryWrapper
                .eq("permission_belong_submenu_name", deleteSysMenuSub.getMenuName()));
        deleteSysPermissions.forEach(permission -> sysRoleMapper.deleteRolePermissionByPermissionId(permission.getId()));
        sysPermissionMapper.delete(permissionQueryWrapper.eq("permission_belong_submenu_name", deleteSysMenuSub.getMenuName()));
        sysMenuSubMapper.deleteById(dto.getId());
    }

    @Override
    public String update(SysMenuSubUpdateDto dto) {
        String warn = "";
        SysMenuSub sysMenuSub = new SysMenuSub();
        BeanUtils.copyProperties(dto, sysMenuSub);
        sysMenuSubMapper.updateById(sysMenuSub);
        return warn;
    }

    @Override
    public SysMenuSub getByIndex(String index) {
        QueryWrapper<SysMenuSub> queryWrapper = new QueryWrapper<>();
        return sysMenuSubMapper.selectOne(queryWrapper.eq("menu_index", index));
    }

    @Override
    public List<SysMenuSub> getAll() {
        return sysMenuSubMapper.selectList(null);
    }
}
