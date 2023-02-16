package pers.elianacc.yurayura.service;

import com.github.pagehelper.PageInfo;
import pers.elianacc.yurayura.dto.SysManagerInsertDto;
import pers.elianacc.yurayura.dto.SysManagerLoginDto;
import pers.elianacc.yurayura.dto.SysManagerSelectDto;
import pers.elianacc.yurayura.dto.SysManagerUpdateDto;
import pers.elianacc.yurayura.vo.ApiResult;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 系统管理员 service
 *
 * @author ELiaNaCc
 * @since 2022-10-21
 */
public interface SysManagerService {

    /**
     * 分页查询系统管理员
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<PageInfo<Map<String,Object>>>
     */
    public ApiResult<PageInfo<Map<String, Object>>> getPage(SysManagerSelectDto dto);

    /**
     * 添加系统管理员
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    public ApiResult<String> insert(SysManagerInsertDto dto);

    /**
     * 修改系统管理员
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    public ApiResult<String> update(SysManagerUpdateDto dto);

    /**
     * 系统管理员登入
     *
     * @param dto
     * @param session
     * @return java.lang.String
     */
    public String login(SysManagerLoginDto dto, HttpSession session);

    /**
     * 获取当前登入管理员信息
     *
     * @param
     * @return java.util.Map<java.lang.String, java.lang.Object>
     */
    public Map<String, Object> getCurrentManagerMsg();

}
