package pers.elianacc.yurayura.service.impl;

import com.github.pagehelper.PageInfo;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.elianacc.yurayura.dto.SysDictInsertDto;
import pers.elianacc.yurayura.dto.SysDictSelectDto;
import pers.elianacc.yurayura.dto.SysDictUpdateDto;
import pers.elianacc.yurayura.entity.sys.dict.SysDict;
import pers.elianacc.yurayura.exception.BusinessException;
import pers.elianacc.yurayura.feign.SysFeignClient;
import pers.elianacc.yurayura.service.SysDictService;
import pers.elianacc.yurayura.vo.ApiResult;

import java.util.List;

/**
 * 系统数据字典 service impl
 *
 * @author ELiaNaCc
 * @since 2022-10-20
 */
@Service
public class SysDictServiceImpl implements SysDictService {

    @Autowired
    private SysFeignClient sysFeignClient;

    @Override
    public ApiResult<PageInfo<SysDict>> getPage(SysDictSelectDto dto) {
        ApiResult<PageInfo<SysDict>> apiResult = sysFeignClient.getPage(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @Override
    public ApiResult<String> insert(SysDictInsertDto dto) {
        ApiResult<String> apiResult = sysFeignClient.insert(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @Override
    public ApiResult<String> update(SysDictUpdateDto dto) {
        ApiResult<String> apiResult = sysFeignClient.update(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    @Override
    public ApiResult<List<SysDict>> getByDictCode(String dictCode) {
        ApiResult<List<SysDict>> apiResult = sysFeignClient.getDictByDictCode(dictCode);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    @Override
    public ApiResult<List<SysDict>> getAll() {
        ApiResult<List<SysDict>> apiResult = sysFeignClient.getAllDict();
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }
}
