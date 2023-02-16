package pers.elianacc.yurayura.feign;

import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pers.elianacc.yurayura.dto.IdDto;
import pers.elianacc.yurayura.dto.UserSelectDto;
import pers.elianacc.yurayura.dto.UserUpdateStatusDto;
import pers.elianacc.yurayura.entity.user.User;
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
    public ApiResult<PageInfo<User>> getPage(@RequestBody UserSelectDto dto);

    @PutMapping("/user/updateStatus")
    public ApiResult<String> updateStatus(@RequestBody UserUpdateStatusDto dto);

    @PutMapping("/user/updateAvatarDefault")
    public ApiResult<String> updateAvatarDefault(@RequestBody IdDto dto);

}
