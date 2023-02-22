package pers.elianacc.yurayura.service.impl;

import com.github.pagehelper.PageInfo;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.elianacc.yurayura.dto.SysRoleInsertDto;
import pers.elianacc.yurayura.dto.SysRoleSelectDto;
import pers.elianacc.yurayura.dto.SysRoleUpdateDto;
import pers.elianacc.yurayura.entity.sys.role.SysRole;
import pers.elianacc.yurayura.exception.BusinessException;
import pers.elianacc.yurayura.feign.SysFeignClient;
import pers.elianacc.yurayura.service.SysRoleService;
import pers.elianacc.yurayura.vo.ApiResult;
import pers.elianacc.yurayura.vo.SysRoleAndPermissionVo;

import java.util.List;

/**
 * 系统角色 service impl
 *
 * @author ELiaNaCc
 * @since 2022-11-16
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysFeignClient sysFeignClient;

    @Override
    public ApiResult<PageInfo<SysRoleAndPermissionVo>> getPage(SysRoleSelectDto dto) {
        ApiResult<PageInfo<SysRoleAndPermissionVo>> apiResult = sysFeignClient.getPage(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    @Override
    public ApiResult<List<SysRole>> getAll() {
        ApiResult<List<SysRole>> apiResult = sysFeignClient.getAllRole();
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @Override
    public ApiResult<String> insert(SysRoleInsertDto dto) {
        ApiResult<String> apiResult = sysFeignClient.insert(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @Override
    public ApiResult<String> update(SysRoleUpdateDto dto) {
        ApiResult<String> apiResult = sysFeignClient.update(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }
}
