package pers.elianacc.yurayura.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.lock.annotation.Lock4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.controller.block.ComicBlockHandler;
import pers.elianacc.yurayura.dto.ComicInsertDto;
import pers.elianacc.yurayura.dto.ComicSelectDto;
import pers.elianacc.yurayura.dto.ComicUpdateDto;
import pers.elianacc.yurayura.dto.IdsDto;
import pers.elianacc.yurayura.service.ComicService;
import pers.elianacc.yurayura.vo.ApiResult;

/**
 * 番剧 controller
 *
 * @author ELiaNaCc
 * @since 2022-10-09
 */
@RestController
@RequestMapping("/api/comic")
@Api(tags = "番剧API")
public class ComicController {

    @Autowired
    private ComicService comicService;

    /**
     * 分页查询番剧
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    @PostMapping("/getPage")
    @SentinelResource(value = "comic-getPage",
            blockHandlerClass = ComicBlockHandler.class,
            blockHandler = "getPageBlockHandler")
    @ApiOperation("分页查询番剧")
    public ApiResult getPage(@RequestBody ComicSelectDto dto) {
        return comicService.getPage(dto);
    }

    /**
     * 添加番剧
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    @PostMapping("/insert")
    @Lock4j(keys = {"#dto.comicName"}, autoRelease = false)
    @ApiOperation("添加番剧")
    public ApiResult insert(ComicInsertDto dto) {
        return comicService.insert(dto);
    }

    /**
     * 批量删除番剧（根据番剧id组）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    @PutMapping("/deleteBatchByIds")
    @ApiOperation("批量删除番剧（根据番剧id组）")
    public ApiResult deleteBatchByIds(@RequestBody IdsDto dto) {
        return comicService.deleteBatchByIds(dto);
    }

    /**
     * 修改番剧
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    @PutMapping("/update")
    @Lock4j(keys = {"#dto.id"}, autoRelease = false)
    @ApiOperation("修改番剧")
    public ApiResult update(ComicUpdateDto dto) {
        return comicService.update(dto);
    }

}
