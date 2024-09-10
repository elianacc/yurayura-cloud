package pers.elianacc.yurayura.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pers.elianacc.yurayura.vo.ApiResult;

import java.util.List;

/**
 * 系统模块 feign client
 *
 * @author ELiaNaCc
 * @since 2022-10-20
 */
@FeignClient(value = "yura-cloud-sys")
public interface SysFeignClient {

    @RequestMapping("/remote/sys/manager/getManagerRolePermission")
    public ApiResult<List<String>> getManagerRolePermission(@RequestParam Integer managerId);

}
