package pers.elianacc.yurayura.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import pers.elianacc.yurayura.dto.ComicInsertDto;
import pers.elianacc.yurayura.dto.ComicSelectDto;
import pers.elianacc.yurayura.dto.ComicUpdateDto;
import pers.elianacc.yurayura.dto.IdsDto;
import pers.elianacc.yurayura.entity.comic.Comic;

/**
 * 番剧 service
 *
 * @author ELiaNaCc
 * @since 2019-10-27
 */
public interface IComicService extends IService<Comic> {

    /**
     * 分页查询番剧
     *
     * @param dto
     * @return com.github.pagehelper.PageInfo<pers.elianacc.yurayura.entity.comic.Comic>
     */
    public PageInfo<Comic> getPage(ComicSelectDto dto);

    /**
     * 添加番剧
     *
     * @param dto
     * @return void
     */
    public void insert(ComicInsertDto dto);

    /**
     * 批量删除番剧（根据番剧id组）
     *
     * @param dto
     * @return void
     */
    public void deleteBatchByIds(IdsDto dto);

    /**
     * 修改番剧
     *
     * @param dto
     * @return void
     */
    public void update(ComicUpdateDto dto);

}
