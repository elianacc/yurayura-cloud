package pers.elianacc.yurayura.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.elianacc.yurayura.dao.SysManagerMapper;
import pers.elianacc.yurayura.dao.SysPermissionMapper;
import pers.elianacc.yurayura.dao.SysRoleMapper;
import pers.elianacc.yurayura.dto.SysRoleInsertDto;
import pers.elianacc.yurayura.dto.SysRoleSelectDto;
import pers.elianacc.yurayura.dto.SysRoleUpdateDto;
import pers.elianacc.yurayura.entity.sys.role.SysRole;
import pers.elianacc.yurayura.enumerate.EnableStatusEnum;
import pers.elianacc.yurayura.service.ISysRoleService;
import pers.elianacc.yurayura.vo.SysRoleAndPermissionVo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统角色 service impl
 *
 * @author ELiaNaCc
 * @since 2022-03-07
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Autowired
    private SysManagerMapper sysManagerMapper;

    @Override
    public PageInfo<SysRoleAndPermissionVo> getPage(SysRoleSelectDto dto) {
        // 设置分页
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<SysRoleAndPermissionVo> roleAndPermissionList = sysRoleMapper.getRoleAndPermissionListBySelectDto(dto);
        return new PageInfo<>(roleAndPermissionList, 5);
    }

    @Override
    public List<SysRole> getAll() {
        return sysRoleMapper.selectList(Wrappers.<SysRole>lambdaQuery().ne(SysRole::getId, 1));
    }

    @Override
    public String insert(SysRoleInsertDto dto) {
        String warn = "";
        List<SysRole> sysRoleList = sysRoleMapper.selectList(Wrappers.<SysRole>lambdaQuery()
                .eq(SysRole::getRoleName, dto.getRoleName()));
        if (sysRoleList.isEmpty()) {
            SysRole sysRole = new SysRole();
            BeanUtils.copyProperties(dto, sysRole);
            sysRole.setRoleCreateTime(LocalDateTime.now());
            sysRoleMapper.insert(sysRole);
            if (!dto.getPermissionIdArr().isEmpty()) {
                List<Integer> permissionIdExistList = dto.getPermissionIdArr().stream()
                        .filter(permissionId -> !(sysPermissionMapper.selectById(permissionId).getPermissionStatus() == EnableStatusEnum.DISABLE.getStatusId().intValue()))
                        .collect(Collectors.toList());
                if (!permissionIdExistList.isEmpty()) {
                    sysRoleMapper.insertBatchRolePermission(permissionIdExistList, sysRole.getId());
                }
            }
        } else {
            warn = "角色名已经存在";
        }
        return warn;
    }

    @Override
    public String update(SysRoleUpdateDto dto) {
        String warn = "";
        SysRole oldRole = sysRoleMapper.selectById(dto.getId());
        if (oldRole.getRoleName().equals("超级管理员")) {
            warn = "超级管理员的角色信息不允许被修改";
            return warn;
        }
        List<SysRole> sysRoleList = sysRoleMapper.selectList(Wrappers.<SysRole>lambdaQuery()
                .eq(SysRole::getRoleName, dto.getRoleName()));
        if (!sysRoleList.isEmpty() && !oldRole.getRoleName().equals(dto.getRoleName())) {
            warn = "角色名已经存在";
        } else {
            SysRole sysRole = new SysRole();
            BeanUtils.copyProperties(dto, sysRole);
            sysRole.setRoleUpdateTime(LocalDateTime.now());
            sysRoleMapper.updateById(sysRole);
            sysRoleMapper.deleteRolePermissionByRoleId(sysRole.getId());
            if (!dto.getPermissionIdArr().isEmpty()) {
                List<Integer> permissionIdExistList = dto.getPermissionIdArr().stream()
                        .filter(permissionId -> !(sysPermissionMapper.selectById(permissionId).getPermissionStatus() == EnableStatusEnum.DISABLE.getStatusId().intValue()))
                        .collect(Collectors.toList());
                if (!permissionIdExistList.isEmpty()) {
                    sysRoleMapper.insertBatchRolePermission(permissionIdExistList, sysRole.getId());
                }
            }
            if (oldRole.getRoleStatus() == EnableStatusEnum.ENABLE.getStatusId().intValue()
                    && sysRole.getRoleStatus() == EnableStatusEnum.DISABLE.getStatusId().intValue()) {
                sysManagerMapper.deleteManagerRoleByRoleId(sysRole.getId());
            }
        }
        return warn;
    }


}
