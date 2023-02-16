package pers.elianacc.yurayura.feign;

import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import pers.elianacc.yurayura.dto.ComicInsertDto;
import pers.elianacc.yurayura.dto.ComicSelectDto;
import pers.elianacc.yurayura.dto.ComicUpdateDto;
import pers.elianacc.yurayura.dto.IdsDto;
import pers.elianacc.yurayura.entity.comic.Comic;
import pers.elianacc.yurayura.vo.ApiResult;

/**
 * 番剧 feign client
 *
 * @author ELiaNaCc
 * @since 2022-10-09
 */
@FeignClient(value = "yurayura-cloud-service-comic")
public interface ComicFeignClient {

    @PostMapping("/comic/getPage")
    public ApiResult<PageInfo<Comic>> getPage(@RequestBody ComicSelectDto dto);

    @PostMapping(value = "/comic/insert", headers = "content-type=" + MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResult<String> insert(@RequestPart ComicInsertDto dto);

    @PutMapping("/comic/deleteBatchByIds")
    public ApiResult<String> deleteBatchByIds(@RequestBody IdsDto dto);

    @PutMapping(value = "/comic/update", headers = "content-type=" + MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResult<String> update(@RequestPart ComicUpdateDto dto);

}
