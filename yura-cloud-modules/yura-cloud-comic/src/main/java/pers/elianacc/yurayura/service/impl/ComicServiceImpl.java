package pers.elianacc.yurayura.service.impl;

import cn.afterturn.easypoi.entity.ImageEntity;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import pers.elianacc.yurayura.bo.ComicExportBO;
import pers.elianacc.yurayura.dao.ComicMapper;
import pers.elianacc.yurayura.dao.ComicUserDataMapper;
import pers.elianacc.yurayura.dto.ComicInsertDTO;
import pers.elianacc.yurayura.dto.ComicSelectDTO;
import pers.elianacc.yurayura.dto.ComicUpdateDTO;
import pers.elianacc.yurayura.dto.IdsDTO;
import pers.elianacc.yurayura.entity.Comic;
import pers.elianacc.yurayura.entity.ComicUserData;
import pers.elianacc.yurayura.enumerate.ComicShelfStatusEnum;
import pers.elianacc.yurayura.enumerate.ComicStatusEnum;
import pers.elianacc.yurayura.enumerate.ImgUploadCategoryEnum;
import pers.elianacc.yurayura.enumerate.ImgUploadResultEnum;
import pers.elianacc.yurayura.feign.SysFeignClient;
import pers.elianacc.yurayura.service.IComicService;
import pers.elianacc.yurayura.utils.EasyPoiUtil;
import pers.elianacc.yurayura.utils.FileUtil;
import pers.elianacc.yurayura.vo.ApiResult;
import pers.elianacc.yurayura.vo.SysDictVO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Autowired
    private SysFeignClient sysFeignClient;

    @Value("${yurayura.default-upload.comic-image}")
    private String defaultUplCmImg;
    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

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

    @Override
    public void exportExcel(ComicSelectDTO dto, HttpServletResponse response) throws IOException {
        List<Comic> comicList = comicMapper.getListBySelectDTO(dto);
        ApiResult<List<SysDictVO>> apiResult = sysFeignClient.getByDictCode("comicStatus");
        Assert.isTrue(apiResult.getCode() == ApiResult.SUCCESS_CODE, "获取状态字典列表失败");
        List<SysDictVO> dictVOS = apiResult.getData();
        List<ComicExportBO> exportBOList = comicList
                .stream()
                .map(comic -> {
                    ComicExportBO comicExportBO = new ComicExportBO();
                    BeanUtils.copyProperties(comic, comicExportBO);
                    if (comic.getComicShelfStatus().equals(ComicShelfStatusEnum.FINISHED.getStatusId())) {
                        comicExportBO.setComicName(comicExportBO.getComicName() + "(已下架)");
                    }
                    if (comic.getComicStatus().equals(ComicStatusEnum.FINISHED.getStatusId())) {
                        comicExportBO.setComicCurtStatus("已完结：全" + comic.getComicCurrentEpisodes() + "话");
                    } else {
                        dictVOS.forEach(vo -> {
                            if (vo.getDictVal().equals(comic.getComicStatus().toString())) {
                                comicExportBO.setComicCurtStatus(vo.getDictName()
                                        + "：更新至第" + comic.getComicCurrentEpisodes() + "话");
                            }
                        });
                    }
                    List<String> labelList = CollUtil
                            .newArrayList(comic
                                    .getComicLabel()
                                    .split(","))
                            .stream()
                            .filter(label -> StringUtils.isNotBlank(label))
                            .collect(Collectors.toList());
                    comicExportBO.setComicLabel(CollUtil.join(labelList, ","));

                    String imgUrl = "";
                    if (comic.getComicImageUrl().equals(defaultUplCmImg)) {
                        imgUrl = "static" + defaultUplCmImg;
                    } else {
                        imgUrl = uploadPath.substring(0, 28) + comic.getComicImageUrl();
                    }

                    ImageEntity imageEntity = new ImageEntity(imgUrl, 165, 217);
                    comicExportBO.setComicImage(imageEntity);

                    return comicExportBO;
                })
                .collect(Collectors.toList());

        Map<String, Object> data = new HashMap<>();
        data.put("comicList", exportBOList);
        TemplateExportParams exportParams = new TemplateExportParams("templates/comic-export-tplt.xlsx"
                , 0);

        EasyPoiUtil.exportExcelByTemplate("番剧信息" + System.currentTimeMillis()
                , data, exportParams, response);
    }
}
