package pers.elianacc.yurayura.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.lock.annotation.Lock4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.controller.block.UserBlockHandler;
import pers.elianacc.yurayura.dto.IdDto;
import pers.elianacc.yurayura.dto.UserSelectDto;
import pers.elianacc.yurayura.dto.UserUpdateStatusDto;
import pers.elianacc.yurayura.service.UserService;
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
    private UserService userService;

    /**
     * 分页查询用户
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    @PostMapping("/getPage")
    @SentinelResource(value = "user-getPage",
            blockHandlerClass = UserBlockHandler.class,
            blockHandler = "getPageBlockHandler")
    @ApiOperation("分页查询用户")
    public ApiResult getPage(@RequestBody UserSelectDto dto) {
        return userService.getPage(dto);
    }

    /**
     * 修改状态（根据用户id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    @PutMapping("/updateStatus")
    @Lock4j(keys = {"#dto.id"}, autoRelease = false)
    @ApiOperation("修改状态（根据用户id）")
    public ApiResult updateStatus(@RequestBody UserUpdateStatusDto dto) {
        return userService.updateStatus(dto);
    }

    /**
     * 重置为默认头像（根据用户id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    @PutMapping("/updateAvatarDefault")
    @ApiOperation("重置为默认头像（根据用户id）")
    public ApiResult updateAvatarDefault(@RequestBody IdDto dto) {
        return userService.updateAvatarDefault(dto);
    }


}
