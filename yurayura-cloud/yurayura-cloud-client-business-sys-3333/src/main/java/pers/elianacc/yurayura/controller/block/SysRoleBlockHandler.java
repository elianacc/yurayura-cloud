package pers.elianacc.yurayura.controller.block;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import pers.elianacc.yurayura.dto.SysRoleSelectDto;
import pers.elianacc.yurayura.vo.ApiResult;

/**
 * 系统角色 自定义限流处理逻辑
 *
 * @author ELiaNaCc
 * @since 2022-11-16
 */
public class SysRoleBlockHandler {

    public static ApiResult getPageBlockHandler(SysRoleSelectDto dto, BlockException exception) {
        return ApiResult.warn("操作过于频繁！");
    }

}
