package pers.elianacc.yurayura.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import pers.elianacc.yurayura.bo.SysPermissionAuthorTreeSelectBo;
import pers.elianacc.yurayura.dao.SysPermissionMapper;
import pers.elianacc.yurayura.dao.SysRoleMapper;
import pers.elianacc.yurayura.dto.SysPermissionInsertDto;
import pers.elianacc.yurayura.dto.SysPermissionSelectDto;
import pers.elianacc.yurayura.dto.SysPermissionUpdateDto;
import pers.elianacc.yurayura.entity.sys.permission.SysPermission;
import pers.elianacc.yurayura.enumerate.EnableStatusEnum;
import pers.elianacc.yurayura.service.ISysPermissionService;

import java.util.List;

/**
 * 系统权限 service impl
 *
 * @author ELiaNaCc
 * @since 2021-08-05
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;


    @Override
    public PageInfo<SysPermission> getPage(SysPermissionSelectDto dto) {
        // 设置分页
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<SysPermission> sysPermissionList = sysPermissionMapper.selectList(Wrappers.<SysPermission>lambdaQuery()
                .like(!ObjectUtils.isEmpty(dto.getPermissionCode()), SysPermission::getPermissionCode, dto.getPermissionCode())
                .eq(!ObjectUtils.isEmpty(dto.getPermissionType()), SysPermission::getPermissionType, dto.getPermissionType())
                .eq(!ObjectUtils.isEmpty(dto.getPermissionStatus()), SysPermission::getPermissionStatus, dto.getPermissionStatus())
                .eq(!ObjectUtils.isEmpty(dto.getPermissionBelongSubmenuName()), SysPermission::getPermissionBelongSubmenuName, dto.getPermissionBelongSubmenuName())
                .orderByAsc(SysPermission::getPermissionBelongSubmenuName, SysPermission::getPermissionSeq)
        );
        return new PageInfo<>(sysPermissionList, 5);
    }

    @Override
    public String insert(SysPermissionInsertDto dto) {
        String warn = "";
        SysPermission sysPermission = new SysPermission();
        BeanUtils.copyProperties(dto, sysPermission);
        if (ObjectUtils.isEmpty(dto.getPermissionBtnVal())) {
            sysPermission.setPermissionCode(sysPermission.getPermissionBelongSubmenuName() + "_select");
        } else {
            sysPermission.setPermissionCode(sysPermission.getPermissionBelongSubmenuName() + "_" + dto.getPermissionBtnVal());
        }
        List<SysPermission> existPermList = sysPermissionMapper.selectList(Wrappers.<SysPermission>lambdaQuery()
                .eq(SysPermission::getPermissionCode, sysPermission.getPermissionCode()));
        if (!existPermList.isEmpty()) {
            warn = "此权限已经存在";
        } else {
            sysPermissionMapper.insert(sysPermission);
            if (sysPermission.getPermissionStatus() == EnableStatusEnum.ENABLE.getStatusId().intValue()) {
                sysRoleMapper.insertRolePermissionForAdmin(sysPermission.getId());
            }
        }
        return warn;
    }

    @Override
    public String update(SysPermissionUpdateDto dto) {
        String warn = "";
        SysPermission oldPerm = sysPermissionMapper.selectById(dto.getId());
        SysPermission sysPermission = new SysPermission();
        BeanUtils.copyProperties(dto, sysPermission);
        if (sysPermission.getPermissionStatus() == EnableStatusEnum.DISABLE.getStatusId().intValue()) {
            sysRoleMapper.deleteRolePermissionByPermissionId(sysPermission.getId());
        }
        sysPermissionMapper.updateById(sysPermission);
        if (oldPerm.getPermissionStatus() == EnableStatusEnum.DISABLE.getStatusId().intValue()
                && sysPermission.getPermissionStatus() == EnableStatusEnum.ENABLE.getStatusId().intValue()) {
            sysRoleMapper.insertRolePermissionForAdmin(sysPermission.getId());
        }
        return warn;
    }

    @Override
    public List<SysPermissionAuthorTreeSelectBo> getPermissionAuthorTree() {
        return sysPermissionMapper.getPermissionAuthorTree();
    }

}
