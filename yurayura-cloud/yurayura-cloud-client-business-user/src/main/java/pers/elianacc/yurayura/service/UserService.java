package pers.elianacc.yurayura.service;

import pers.elianacc.yurayura.dto.IdDto;
import pers.elianacc.yurayura.dto.UserSelectDto;
import pers.elianacc.yurayura.dto.UserUpdateStatusDto;
import pers.elianacc.yurayura.vo.ApiResult;

/**
 * 用户 service
 *
 * @author ELiaNaCc
 * @since 2022-10-14
 */
public interface UserService {

    /**
     * 分页查询用户
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    public ApiResult getPage(UserSelectDto dto);

    /**
     * 修改状态（根据用户id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    public ApiResult updateStatus(UserUpdateStatusDto dto);

    /**
     * 重置为默认头像（根据用户id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult
     */
    public ApiResult updateAvatarDefault(IdDto dto);

}
