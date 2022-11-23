package pers.elianacc.yurayura.service.impl;

import io.seata.spring.annotation.GlobalTransactional;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.elianacc.yurayura.dto.IdDto;
import pers.elianacc.yurayura.dto.SysMenuInsertDto;
import pers.elianacc.yurayura.dto.SysMenuUpdateDto;
import pers.elianacc.yurayura.entity.sys.manager.SysManager;
import pers.elianacc.yurayura.exception.BusinessException;
import pers.elianacc.yurayura.feign.SysFeignClient;
import pers.elianacc.yurayura.service.SysMenuService;
import pers.elianacc.yurayura.vo.ApiResult;

/**
 * 系统菜单 service impl
 *
 * @author ELiaNaCc
 * @since 2022-10-26
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysFeignClient sysFeignClient;

    @Override
    public ApiResult getTreeList() {
        ApiResult apiResult = sysFeignClient.getMenuTreeList();
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    @Override
    public ApiResult getTreeListForCurrentManager() {
        SysManager currentSysManager = (SysManager) SecurityUtils.getSubject().getPrincipal();
        ApiResult apiResult = sysFeignClient.getMenuTreeListByManagerId(currentSysManager.getId());
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @Override
    public ApiResult insert(SysMenuInsertDto dto) {
        ApiResult apiResult = sysFeignClient.insert(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @Override
    public ApiResult update(SysMenuUpdateDto dto) {
        ApiResult apiResult = sysFeignClient.update(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @Override
    public ApiResult deleteById(IdDto dto) {
        ApiResult apiResult = sysFeignClient.deleteMenuById(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }
}
