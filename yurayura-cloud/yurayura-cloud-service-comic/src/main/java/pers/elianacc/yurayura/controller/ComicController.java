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
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/getById")
    public ApiResult getById(IdDto dto) {
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        }
        return ApiResult.success("查询成功", iComicService.getById(dto.getId()));
    }

    /**
     * 分页查询番剧
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    @PostMapping("/getPage")
    public ApiResult getPage(@RequestBody ComicSelectDto dto) {
        if (ObjectUtils.isEmpty(dto.getPageNum())) {
            return ApiResult.warn("页码不能为空");
        } else if (ObjectUtils.isEmpty(dto.getPageSize())) {
            dto.setPageSize(10); // 页记录数默认10
        }
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
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    @PostMapping("/insert")
    public ApiResult insert(ComicInsertDto dto) {
        if (ObjectUtils.isEmpty(dto.getComicName())) {
            return ApiResult.warn("名称不能为空");
        } else if (ObjectUtils.isEmpty(dto.getComicTime())) {
            return ApiResult.warn("放送时间不能为空");
        } else if (ObjectUtils.isEmpty(dto.getComicStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (ObjectUtils.isEmpty(dto.getComicShelfStatus())) {
            return ApiResult.warn("上架状态不能为空");
        } else if (dto.getComicContent().length() > 500) {
            return ApiResult.warn("简介不能超过500个字符");
        } else if (dto.getComicName().length() > 30) {
            return ApiResult.warn("名称不能超过30个字符");
        }
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
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    @PutMapping("/deleteBatchByIds")
    public ApiResult deleteBatchByIds(@RequestBody IdsDto dto) {
        if (dto.getIds().isEmpty()) {
            return ApiResult.warn("id组不能为空");
        }
        iComicService.deleteBatchByIds(dto);
        return ApiResult.success("删除成功");
    }

    /**
     * 修改番剧
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    @PutMapping("/update")
    public ApiResult update(ComicUpdateDto dto) {
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        } else if (ObjectUtils.isEmpty(dto.getComicName())) {
            return ApiResult.warn("名称不能为空");
        } else if (ObjectUtils.isEmpty(dto.getComicTime())) {
            return ApiResult.warn("放送时间不能为空");
        } else if (ObjectUtils.isEmpty(dto.getComicStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (ObjectUtils.isEmpty(dto.getComicShelfStatus())) {
            return ApiResult.warn("上架状态不能为空");
        } else if (dto.getComicContent().length() > 500) {
            return ApiResult.warn("简介不能超过500个字符");
        } else if (dto.getComicName().length() > 30) {
            return ApiResult.warn("名称不能超过30个字符");
        }
        String warn = iComicService.update(dto);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("修改成功");
    }
}
