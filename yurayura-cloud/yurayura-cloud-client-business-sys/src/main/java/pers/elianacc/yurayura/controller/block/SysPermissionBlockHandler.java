package pers.elianacc.yurayura.controller.block;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.github.pagehelper.PageInfo;
import pers.elianacc.yurayura.dto.SysPermissionSelectDto;
import pers.elianacc.yurayura.entity.sys.permission.SysPermission;
import pers.elianacc.yurayura.vo.ApiResult;

/**
 * 系统权限 自定义限流处理逻辑
 *
 * @author ELiaNaCc
 * @since 2022-11-16
 */
public class SysPermissionBlockHandler {

    public static ApiResult<PageInfo<SysPermission>> getPageBlockHandler(SysPermissionSelectDto dto, BlockException exception) {
        return ApiResult.warn("操作过于频繁！");
    }

}
