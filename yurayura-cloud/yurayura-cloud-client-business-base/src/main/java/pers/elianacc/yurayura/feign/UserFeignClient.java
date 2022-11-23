package pers.elianacc.yurayura.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pers.elianacc.yurayura.dto.IdDto;
import pers.elianacc.yurayura.dto.UserSelectDto;
import pers.elianacc.yurayura.dto.UserUpdateStatusDto;
import pers.elianacc.yurayura.vo.ApiResult;

/**
 * 用户 feign client
 *
 * @author ELiaNaCc
 * @since 2022-10-14
 */
@FeignClient(value = "yurayura-cloud-service-user")
public interface UserFeignClient {

    @PostMapping("/user/getPage")
    public ApiResult getPage(@RequestBody UserSelectDto dto);

    @PutMapping("/user/updateStatus")
    public ApiResult updateStatus(@RequestBody UserUpdateStatusDto dto);

    @PutMapping("/user/updateAvatarDefault")
    public ApiResult updateAvatarDefault(@RequestBody IdDto dto);

}
