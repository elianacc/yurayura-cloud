package pers.elianacc.yurayura.service.impl;

import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.elianacc.yurayura.dto.ComicInsertDto;
import pers.elianacc.yurayura.dto.ComicSelectDto;
import pers.elianacc.yurayura.dto.ComicUpdateDto;
import pers.elianacc.yurayura.dto.IdsDto;
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
    public ApiResult getPage(ComicSelectDto dto) {
        ApiResult apiResult = comicFeignClient.getPage(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @Override
    public ApiResult insert(ComicInsertDto dto) {
        ApiResult apiResult = comicFeignClient.insert(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @Override
    public ApiResult deleteBatchByIds(IdsDto dto) {
        ApiResult apiResult = comicFeignClient.deleteBatchByIds(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }

    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @Override
    public ApiResult update(ComicUpdateDto dto) {
        ApiResult apiResult = comicFeignClient.update(dto);
        if (apiResult.getCode() != 200) {
            throw new BusinessException(apiResult.getCode(), apiResult.getMsg());
        }
        return apiResult;
    }
}
