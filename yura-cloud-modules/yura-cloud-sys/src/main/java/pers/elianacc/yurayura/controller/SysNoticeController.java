package pers.elianacc.yurayura.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import pers.elianacc.yurayura.service.ISysNoticeService;
import pers.elianacc.yurayura.dto.IdDTO;
import pers.elianacc.yurayura.entity.SysNotice;
import pers.elianacc.yurayura.vo.ApiResult;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 系统通知 controller
 *
 * @author ELiaNaCc
 * @since 2024-12-13
 */
@RestController
@RequestMapping("/api/sys/notice")
@Api(tags = "系统通知API")
public class SysNoticeController {

    @Autowired
    private ISysNoticeService iSysNoticeService;

    /**
     * 查询系统通知（根据系统通知id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<pers.elianacc.yurayura.entity.SysNotice>
     */
    @GetMapping("/getById")
    @ApiOperation("查询系统通知（根据系统通知id）")
    public ApiResult<SysNotice> getById(IdDTO dto) {
        return ApiResult.success("查询成功", iSysNoticeService.getById(dto.getId()));
    }
}

