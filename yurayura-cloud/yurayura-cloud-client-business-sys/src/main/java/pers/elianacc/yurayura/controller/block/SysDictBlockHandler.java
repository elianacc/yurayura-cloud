package pers.elianacc.yurayura.controller.block;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.github.pagehelper.PageInfo;
import pers.elianacc.yurayura.dto.SysDictSelectDto;
import pers.elianacc.yurayura.entity.sys.dict.SysDict;
import pers.elianacc.yurayura.vo.ApiResult;

/**
 * 系统数据字典 自定义限流处理逻辑
 *
 * @author ELiaNaCc
 * @since 2022-10-21
 */
public class SysDictBlockHandler {

    public static ApiResult<PageInfo<SysDict>> getPageBlockHandler(SysDictSelectDto dto, BlockException exception) {
        return ApiResult.warn("操作过于频繁！");
    }

}
