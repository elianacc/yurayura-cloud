package pers.elianacc.yurayura.controller;


import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.dto.*;
import pers.elianacc.yurayura.entity.comic.Comic;
import pers.elianacc.yurayura.service.IComicService;
import pers.elianacc.yurayura.vo.ApiResult;

/**
 * 番剧 controller
 *
 * @author ELiaNaCc
 * @since 2019-10-27
 */
@RestController
@RequestMapping("/comic")
public class ComicController {

    @Autowired
    private IComicService iComicService;

    /**
     * 查询番剧（根据番剧id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<pers.elianacc.yurayura.entity.comic.Comic>
     */
    @GetMapping("/getById")
    public ApiResult<Comic> getById(IdDto dto) {
        return ApiResult.success("查询成功", iComicService.getById(dto.getId()));
    }

    /**
     * 分页查询番剧
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<PageInfo<Comic>>
     */
    @PostMapping("/getPage")
    public ApiResult<PageInfo<Comic>> getPage(@RequestBody ComicSelectDto dto) {
        PageInfo<Comic> pageInfo = iComicService.getPage(dto);
        if (pageInfo.getTotal() == 0) {
            return ApiResult.warn("查询不到数据");
        }
        return ApiResult.success("分页查询成功", pageInfo);
    }

    /**
     * 添加番剧
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/insert")
    public ApiResult<String> insert(ComicInsertDto dto) {
        String warn = iComicService.insert(dto);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("添加成功");
    }

    /**
     * 批量删除番剧（根据番剧id组）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/deleteBatchByIds")
    public ApiResult<String> deleteBatchByIds(@RequestBody IdsDto dto) {
        iComicService.deleteBatchByIds(dto);
        return ApiResult.success("删除成功");
    }

    /**
     * 修改番剧
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/update")
    public ApiResult<String> update(ComicUpdateDto dto) {
        String warn = iComicService.update(dto);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("修改成功");
    }
}
