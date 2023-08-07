package pers.elianacc.yurayura.controller;


import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.dto.IdDto;
import pers.elianacc.yurayura.dto.UserSelectDto;
import pers.elianacc.yurayura.dto.UserUpdateStatusDto;
import pers.elianacc.yurayura.entity.user.User;
import pers.elianacc.yurayura.service.IUserService;
import pers.elianacc.yurayura.vo.ApiResult;

/**
 * 用户 controller
 *
 * @author ELiaNaCc
 * @since 2019-10-27
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     * 查询用户（根据用户id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<pers.elianacc.yurayura.entity.user.User>
     */
    @GetMapping("/getById")
    public ApiResult<User> getById(IdDto dto) {
        return ApiResult.success("查询成功", iUserService.getById(dto.getId()));
    }

    /**
     * 分页查询用户
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<PageInfo<User>>
     */
    @PostMapping("/getPage")
    public ApiResult<PageInfo<User>> getPage(@RequestBody UserSelectDto dto) {
        return ApiResult.success("分页查询成功", iUserService.getPage(dto));
    }

    /**
     * 修改状态（根据用户id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/updateStatus")
    public ApiResult<String> updateStatus(@RequestBody UserUpdateStatusDto dto) {
        iUserService.updateStatus(dto);
        return ApiResult.success("修改状态成功");
    }

    /**
     * 重置为默认头像（根据用户id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/updateAvatarDefault")
    public ApiResult<String> updateAvatarDefault(@RequestBody IdDto dto) {
        iUserService.updateAvatarDefault(dto);
        return ApiResult.success("重置为默认头像成功");
    }

}

