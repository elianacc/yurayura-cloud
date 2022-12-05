package pers.elianacc.yurayura.service;

import pers.elianacc.yurayura.dto.ComicInsertDto;
import pers.elianacc.yurayura.dto.ComicSelectDto;
import pers.elianacc.yurayura.dto.ComicUpdateDto;
import pers.elianacc.yurayura.dto.IdsDto;
import pers.elianacc.yurayura.vo.ApiResult;

/**
 * 番剧 service
 *
 * @author ELiaNaCc
 * @since 2022-10-09
 */
public interface ComicService {

    /**
     * 分页查询番剧
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    public ApiResult getPage(ComicSelectDto dto);

    /**
     * 添加番剧
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    public ApiResult insert(ComicInsertDto dto);

    /**
     * 批量删除番剧（根据番剧id组）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    public ApiResult deleteBatchByIds(IdsDto dto);

    /**
     * 修改番剧
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    public ApiResult update(ComicUpdateDto dto);

}
