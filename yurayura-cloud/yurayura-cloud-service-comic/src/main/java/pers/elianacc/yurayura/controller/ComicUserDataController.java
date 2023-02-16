package pers.elianacc.yurayura.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.elianacc.yurayura.dto.IdDto;
import pers.elianacc.yurayura.entity.comic.ComicUserData;
import pers.elianacc.yurayura.service.IComicUserDataService;
import pers.elianacc.yurayura.vo.ApiResult;

/**
 * 番剧用户数据 controller
 *
 * @author ELiaNaCc
 * @since 2020-04-02
 */
@RestController
@RequestMapping("/comicUserData")
public class ComicUserDataController {

    @Autowired
    private IComicUserDataService iComicUserDataService;

    /**
     * 查询番剧用户数据（根据番剧用户数据id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<pers.elianacc.yurayura.entity.comic.ComicUserData>
     */
    @GetMapping("/getById")
    public ApiResult<ComicUserData> getById(IdDto dto) {
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        }
        return ApiResult.success("查询成功", iComicUserDataService.getById(dto.getId()));
    }
}

