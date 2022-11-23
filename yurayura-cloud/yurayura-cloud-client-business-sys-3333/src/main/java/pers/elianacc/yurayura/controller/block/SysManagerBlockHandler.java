package pers.elianacc.yurayura.controller.block;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import pers.elianacc.yurayura.dto.SysManagerSelectDto;
import pers.elianacc.yurayura.vo.ApiResult;

/**
 * 系统管理员 自定义限流处理逻辑
 *
 * @author ELiaNaCc
 * @since 2022-10-21
 */
public class SysManagerBlockHandler {

    public static ApiResult getPageBlockHandler(SysManagerSelectDto dto, BlockException exception) {
        return ApiResult.warn("操作过于频繁！");
    }

}
