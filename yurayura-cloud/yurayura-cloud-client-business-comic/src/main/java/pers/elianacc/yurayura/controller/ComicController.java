package pers.elianacc.yurayura.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.lock.annotation.Lock4j;
import com.github.pagehelper.PageInfo;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.controller.block.ComicBlockHandler;
import pers.elianacc.yurayura.dto.ComicInsertDto;
import pers.elianacc.yurayura.dto.ComicSelectDto;
import pers.elianacc.yurayura.dto.ComicUpdateDto;
import pers.elianacc.yurayura.dto.IdsDto;
import pers.elianacc.yurayura.entity.comic.Comic;
import pers.elianacc.yurayura.exception.BusinessException;
import pers.elianacc.yurayura.feign.ComicFeignClient;
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
    private ComicFeignClient comicFeignClient;

    /**
     * 分页查询番剧
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<PageInfo<Comic>>
     */
    @PostMapping("/getPage")
    @SentinelResource(value = "comic-getPage",
            blockHandlerClass = ComicBlockHandler.class,
            blockHandler = "getPageBlockHandler")
    @ApiOperation("分页查询番剧")
    public ApiResult<PageInfo<Comic>> getPage(@Validated @RequestBody ComicSelectDto dto) {
        ApiResult<PageInfo<Comic>> apiResult = comicFeignClient.getPage(dto);
        if (apiResult.getCode() != ApiResult.SUCCESS_CODE) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    /**
     * 添加番剧
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/insert")
    @Lock4j(keys = {"#dto.comicName"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("添加番剧")
    public ApiResult<String> insert(@Validated ComicInsertDto dto) {
        ApiResult<String> apiResult = comicFeignClient.insert(dto);
        if (apiResult.getCode() != ApiResult.SUCCESS_CODE) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    /**
     * 批量删除番剧（根据番剧id组）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/deleteBatchByIds")
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("批量删除番剧（根据番剧id组）")
    public ApiResult<String> deleteBatchByIds(@Validated @RequestBody IdsDto dto) {
        ApiResult<String> apiResult = comicFeignClient.deleteBatchByIds(dto);
        if (apiResult.getCode() != ApiResult.SUCCESS_CODE) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    /**
     * 修改番剧
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/update")
    @Lock4j(keys = {"#dto.id"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("修改番剧")
    public ApiResult<String> update(@Validated ComicUpdateDto dto) {
        ApiResult<String> apiResult = comicFeignClient.update(dto);
        if (apiResult.getCode() != ApiResult.SUCCESS_CODE) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

}
