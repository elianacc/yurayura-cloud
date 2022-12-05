package pers.elianacc.yurayura.controller.block;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import pers.elianacc.yurayura.vo.ApiResult;

/**
 * 系统菜单 自定义限流处理逻辑
 *
 * @author ELiaNaCc
 * @since 2022-10-26
 */
public class SysMenuBlockHandler {

    public static ApiResult getTreeListBlockHandler(BlockException exception) {
        return ApiResult.warn("操作过于频繁！");
    }

}
