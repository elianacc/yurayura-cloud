package pers.elianacc.yurayura.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.lock.annotation.Lock4j;
import com.github.pagehelper.PageInfo;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.controller.block.UserBlockHandler;
import pers.elianacc.yurayura.dto.IdDto;
import pers.elianacc.yurayura.dto.UserSelectDto;
import pers.elianacc.yurayura.dto.UserUpdateStatusDto;
import pers.elianacc.yurayura.entity.user.User;
import pers.elianacc.yurayura.exception.BusinessException;
import pers.elianacc.yurayura.feign.UserFeignClient;
import pers.elianacc.yurayura.vo.ApiResult;

/**
 * 用户 controller
 *
 * @author ELiaNaCc
 * @since 2022-10-14
 */
@RestController
@RequestMapping("/api/user")
@Api(tags = "用户API")
public class UserController {

    @Autowired
    private UserFeignClient userFeignClient;

    /**
     * 分页查询用户
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<PageInfo<User>>
     */
    @PostMapping("/getPage")
    @SentinelResource(value = "user-getPage",
            blockHandlerClass = UserBlockHandler.class,
            blockHandler = "getPageBlockHandler")
    @ApiOperation("分页查询用户")
    public ApiResult<PageInfo<User>> getPage(@Validated @RequestBody UserSelectDto dto) {
        ApiResult<PageInfo<User>> apiResult = userFeignClient.getPage(dto);
        if (apiResult.getCode() != ApiResult.SUCCESS_CODE) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    /**
     * 修改状态（根据用户id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/updateStatus")
    @Lock4j(keys = {"#dto.id"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("修改状态（根据用户id）")
    public ApiResult<String> updateStatus(@Validated @RequestBody UserUpdateStatusDto dto) {
        ApiResult<String> apiResult = userFeignClient.updateStatus(dto);
        if (apiResult.getCode() != ApiResult.SUCCESS_CODE) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    /**
     * 重置为默认头像（根据用户id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/updateAvatarDefault")
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("重置为默认头像（根据用户id）")
    public ApiResult<String> updateAvatarDefault(@Validated @RequestBody IdDto dto) {
        ApiResult<String> apiResult = userFeignClient.updateAvatarDefault(dto);
        if (apiResult.getCode() != ApiResult.SUCCESS_CODE) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }


}
