package pers.elianacc.yurayura.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import pers.elianacc.yurayura.dto.ComicInsertDTO;
import pers.elianacc.yurayura.dto.ComicSelectDTO;
import pers.elianacc.yurayura.dto.ComicUpdateDTO;
import pers.elianacc.yurayura.dto.IdsDTO;
import pers.elianacc.yurayura.entity.Comic;

import java.io.IOException;

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
    public PageInfo<Comic> getPage(ComicSelectDTO dto);

    /**
     * 添加番剧
     *
     * @param dto
     * @return void
     */
    public void insert(ComicInsertDTO dto) throws IOException;

    /**
     * 批量删除番剧（根据番剧id组）
     *
     * @param dto
     * @return void
     */
    public void deleteBatchByIds(IdsDTO dto);

    /**
     * 修改番剧
     *
     * @param dto
     * @return void
     */
    public void update(ComicUpdateDTO dto) throws IOException;

}
