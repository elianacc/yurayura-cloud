package pers.elianacc.yurayura.controller;


import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
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
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/getById")
    public ApiResult getById(IdDto dto) {
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        }
        return ApiResult.success("查询成功", iUserService.getById(dto.getId()));
    }

    /**
     * 分页查询用户
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    @PostMapping("/getPage")
    public ApiResult getPage(@RequestBody UserSelectDto dto) {
        if (ObjectUtils.isEmpty(dto.getPageNum())) {
            return ApiResult.warn("页码不能为空");
        } else if (ObjectUtils.isEmpty(dto.getPageSize())) {
            dto.setPageSize(10); // 页记录数默认10
        }
        PageInfo<User> pageInfo = iUserService.getPage(dto);
        if (pageInfo.getTotal() == 0) {
            return ApiResult.warn("查询不到数据");
        }
        return ApiResult.success("分页查询成功", pageInfo);
    }

    /**
     * 修改状态（根据用户id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    @PutMapping("/updateStatus")
    public ApiResult updateStatus(@RequestBody UserUpdateStatusDto dto) {
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        } else if (ObjectUtils.isEmpty(dto.getUserStatus())) {
            return ApiResult.warn("状态不能为空");
        }
        iUserService.updateStatus(dto);
        return ApiResult.success("修改状态成功");
    }

    /**
     * 重置为默认头像（根据用户id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    @PutMapping("/updateAvatarDefault")
    public ApiResult updateAvatarDefault(@RequestBody IdDto dto) {
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        }
        iUserService.updateAvatarDefault(dto);
        return ApiResult.success("重置为默认头像成功");
    }

}

