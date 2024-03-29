package pers.elianacc.yurayura.controller.block;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.github.pagehelper.PageInfo;
import pers.elianacc.yurayura.dto.UserSelectDto;
import pers.elianacc.yurayura.entity.user.User;
import pers.elianacc.yurayura.vo.ApiResult;

/**
 * 用户 自定义限流处理逻辑
 *
 * @author ELiaNaCc
 * @since 2022-08-30
 */
public class UserBlockHandler {

    public static ApiResult<PageInfo<User>> getPageBlockHandler(UserSelectDto dto, BlockException exception) {
        return ApiResult.warn("操作过于频繁！");
    }

}
