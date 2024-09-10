package pers.elianacc.yurayura.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import pers.elianacc.yurayura.dao.ComicMapper;
import pers.elianacc.yurayura.dao.ComicUserDataMapper;
import pers.elianacc.yurayura.dto.ComicInsertDTO;
import pers.elianacc.yurayura.dto.ComicSelectDTO;
import pers.elianacc.yurayura.dto.ComicUpdateDTO;
import pers.elianacc.yurayura.dto.IdsDTO;
import pers.elianacc.yurayura.entity.Comic;
import pers.elianacc.yurayura.entity.ComicUserData;
import pers.elianacc.yurayura.enumerate.ComicStatusEnum;
import pers.elianacc.yurayura.enumerate.ImgUploadCategoryEnum;
import pers.elianacc.yurayura.enumerate.ImgUploadResultEnum;
import pers.elianacc.yurayura.service.IComicService;
import pers.elianacc.yurayura.utils.FileUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 番剧 service impl
 *
 * @author ELiaNaCc
 * @since 2019-10-27
 */
@Service
public class ComicServiceImpl extends ServiceImpl<ComicMapper, Comic> implements IComicService {

    @Autowired
    private ComicMapper comicMapper;
    @Autowired
    private ComicUserDataMapper comicUserDataMapper;

    @Value("${yurayura.default-upload.comic-image}")
    private String defaultUplCmImg;

    @Override
    public PageInfo<Comic> getPage(ComicSelectDTO dto) {
        // 设置分页
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<Comic> comicList = comicMapper.getListBySelectDTO(dto);
        PageInfo<Comic> pageInfo = new PageInfo<>(comicList, 5);
        Assert.isTrue(pageInfo.getTotal() != 0, "查询不到数据");
        return pageInfo;
    }

    @Override
    public void insert(ComicInsertDTO dto) throws IOException {
        Integer comicStatus;
        // 更新状态为非完结，且更新时间不为空，更新状态为更新时间
        if (dto.getComicStatus().intValue() == ComicStatusEnum.UPDATING.getStatusId()
                && !ObjectUtils.isEmpty(dto.getComicUdTime())) {
            comicStatus = dto.getComicUdTime();
        } else {
            comicStatus = dto.getComicStatus();
        }

        // 获取图片上传结果
        String imgUplRes = FileUtil.imageUpload(dto.getComicImgFile()
                , 100, ImgUploadCategoryEnum.COMICIMG.getCategory());

        Comic comic = new Comic();
        ComicUserData comicUserData = new ComicUserData();

        Assert.isTrue(!imgUplRes.equals(ImgUploadResultEnum.SIZEBEYOND.getResult())
                        && !imgUplRes.equals(ImgUploadResultEnum.FORMATNOTALLOW.getResult())
                , imgUplRes);

        BeanUtils.copyProperties(dto, comic);
        comic.setComicTime(LocalDateTimeUtil.parseDate(dto.getComicTime()));
        comic.setComicStatus(comicStatus);
        // 番剧图片地址使用默认图片
        comic.setComicImageUrl(defaultUplCmImg);
        // 上传图片不为空，番剧图片地址使用上传图片地址
        if (!imgUplRes.equals(ImgUploadResultEnum.NULL.getResult())) {
            comic.setComicImageUrl(imgUplRes);
        }
        comic.setComicCreateTime(LocalDateTime.now());
        comic.setComicUpdateTime(null);
        comicMapper.insert(comic);
        comicUserData.setComicId(comic.getId());
        comicUserData.setComicName(comic.getComicName());
        comicUserData.setComicPlayNum(0);
        comicUserData.setComicFavoriteNum(0);
        comicUserData.setComicUserDataCreateTime(LocalDateTime.now());
        comicUserData.setComicUserDataUpdateTime(null);
        comicUserDataMapper.insert(comicUserData);
    }

    @Override
    public void deleteBatchByIds(IdsDTO dto) {
        List<Comic> delComicList = comicMapper.selectBatchIds(dto.getIds());
        comicMapper.deleteBatchIds(dto.getIds());
        comicUserDataMapper.deleteBatchByComicId(dto.getIds());
        delComicList.forEach(comic -> {
            // 如果用的是默认图片的，则不删除
            if (!(comic.getComicImageUrl().equals(defaultUplCmImg))) {
                // 删除番剧图片
                FileUtil.fileDelete(comic.getComicImageUrl());
            }
        });
    }

    @Override
    public void update(ComicUpdateDTO dto) throws IOException {
        Integer comicStatus;
        // 更新状态为非完结，且更新时间不为空，更新状态为更新时间
        if (dto.getComicStatus().intValue() == ComicStatusEnum.UPDATING.getStatusId()
                && !ObjectUtils.isEmpty(dto.getComicUdTime())) {
            comicStatus = dto.getComicUdTime();
        } else {
            comicStatus = dto.getComicStatus();
        }

        // 获取图片上传结果
        String imgUplRes = FileUtil.imageUpload(dto.getComicImgFile()
                , 100, ImgUploadCategoryEnum.COMICIMG.getCategory());

        Comic comic = new Comic();

        Assert.isTrue(!imgUplRes.equals(ImgUploadResultEnum.SIZEBEYOND.getResult())
                        && !imgUplRes.equals(ImgUploadResultEnum.FORMATNOTALLOW.getResult())
                , imgUplRes);

        BeanUtils.copyProperties(dto, comic);
        comic.setComicTime(LocalDateTimeUtil.parseDate(dto.getComicTime()));
        comic.setComicStatus(comicStatus);
        comic.setComicUpdateTime(LocalDateTime.now());
        // 上传图片不为空，番剧图片地址使用上传图片地址
        if (!imgUplRes.equals(ImgUploadResultEnum.NULL.getResult())) {
            comic.setComicImageUrl(imgUplRes);
            Comic aComic = comicMapper.selectById(comic.getId());
            // 如果用的是默认图片的，则不删除
            if (!(aComic.getComicImageUrl().equals(defaultUplCmImg))) {
                // 删除番剧图片
                FileUtil.fileDelete(aComic.getComicImageUrl());
            }
        }
        comicMapper.updateById(comic);
    }
}
