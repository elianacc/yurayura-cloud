package pers.elianacc.yurayura.service.impl;

import com.github.pagehelper.PageInfo;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.elianacc.yurayura.dto.ComicInsertDto;
import pers.elianacc.yurayura.dto.ComicSelectDto;
import pers.elianacc.yurayura.dto.ComicUpdateDto;
import pers.elianacc.yurayura.dto.IdsDto;
import pers.elianacc.yurayura.entity.comic.Comic;
import pers.elianacc.yurayura.exception.BusinessException;
import pers.elianacc.yurayura.feign.ComicFeignClient;
import pers.elianacc.yurayura.service.ComicService;
import pers.elianacc.yurayura.vo.ApiResult;

/**
 * 番剧 service impl
 *
 * @author ELiaNaCc
 * @since 2022-10-09
 */
@Service
public class ComicServiceImpl implements ComicService {

    @Autowired
    private ComicFeignClient comicFeignClient;

    @Override
    public ApiResult<PageInfo<Comic>> getPage(ComicSelectDto dto) {
        ApiResult<PageInfo<Comic>> apiResult = comicFeignClient.getPage(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @Override
    public ApiResult<String> insert(ComicInsertDto dto) {
        ApiResult<String> apiResult = comicFeignClient.insert(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @Override
    public ApiResult<String> deleteBatchByIds(IdsDto dto) {
        ApiResult<String> apiResult = comicFeignClient.deleteBatchByIds(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @Override
    public ApiResult<String> update(ComicUpdateDto dto) {
        ApiResult<String> apiResult = comicFeignClient.update(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }
}
