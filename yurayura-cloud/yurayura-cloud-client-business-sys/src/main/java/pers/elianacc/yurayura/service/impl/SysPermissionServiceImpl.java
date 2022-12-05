package pers.elianacc.yurayura.service.impl;

import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.elianacc.yurayura.dto.SysPermissionInsertDto;
import pers.elianacc.yurayura.dto.SysPermissionSelectDto;
import pers.elianacc.yurayura.dto.SysPermissionUpdateDto;
import pers.elianacc.yurayura.exception.BusinessException;
import pers.elianacc.yurayura.feign.SysFeignClient;
import pers.elianacc.yurayura.service.SysPermissionService;
import pers.elianacc.yurayura.vo.ApiResult;

/**
 * 系统权限 service impl
 *
 * @author ELiaNaCc
 * @since 2022-11-16
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    private SysFeignClient sysFeignClient;

    @Override
    public ApiResult getPage(SysPermissionSelectDto dto) {
        ApiResult apiResult = sysFeignClient.getPage(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @Override
    public ApiResult insert(SysPermissionInsertDto dto) {
        ApiResult apiResult = sysFeignClient.insert(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @Override
    public ApiResult update(SysPermissionUpdateDto dto) {
        ApiResult apiResult = sysFeignClient.update(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    @Override
    public ApiResult getPermissionAuthorTree() {
        ApiResult apiResult = sysFeignClient.getPermissionAuthorTree();
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }
}
