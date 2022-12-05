package pers.elianacc.yurayura.service.impl;

import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.elianacc.yurayura.dto.IdDto;
import pers.elianacc.yurayura.dto.UserSelectDto;
import pers.elianacc.yurayura.dto.UserUpdateStatusDto;
import pers.elianacc.yurayura.exception.BusinessException;
import pers.elianacc.yurayura.feign.UserFeignClient;
import pers.elianacc.yurayura.service.UserService;
import pers.elianacc.yurayura.vo.ApiResult;

/**
 * 用户 service impl
 *
 * @author ELiaNaCc
 * @since 2022-10-14
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public ApiResult getPage(UserSelectDto dto) {
        ApiResult apiResult = userFeignClient.getPage(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @Override
    public ApiResult updateStatus(UserUpdateStatusDto dto) {
        ApiResult apiResult = userFeignClient.updateStatus(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @Override
    public ApiResult updateAvatarDefault(IdDto dto) {
        ApiResult apiResult = userFeignClient.updateAvatarDefault(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

}
