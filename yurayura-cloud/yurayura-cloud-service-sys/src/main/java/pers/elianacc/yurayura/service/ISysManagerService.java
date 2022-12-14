package pers.elianacc.yurayura.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import pers.elianacc.yurayura.dto.*;
import pers.elianacc.yurayura.entity.sys.manager.SysManager;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 系统管理员 service
 *
 * @author ELiaNaCc
 * @since 2019-10-27
 */
public interface ISysManagerService extends IService<SysManager> {

    /**
     * 分页查询系统管理员
     *
     * @param dto
     * @return com.github.pagehelper.PageInfo<java.util.Map<java.lang.String,java.lang.Object>>
     */
    public PageInfo<Map<String, Object>> getPage(SysManagerSelectDto dto);

    /**
     * 添加系统管理员
     *
     * @param dto
     * @return java.lang.String
     */
    public String insert(SysManagerInsertDto dto);

    /**
     * 批量删除系统管理员（根据系统管理员id组）
     *
     * @param dto
     * @return void
     */
    public void deleteBatchByIds(IdsDto dto);

    /**
     * 修改系统管理员
     *
     * @param dto
     * @return java.lang.String
     */
    public String update(SysManagerUpdateDto dto);

    /**
     * 查询管理员拥有角色的所有权限（根据管理员id）
     *
     * @param managerId
     * @return java.lang.String
     */
    public String getManagerRolePermission(Integer managerId);

    /**
     * 查询启用的管理员（根据管理员名）
     *
     * @param managerName
     * @return pers.elianacc.yurayura.entity.sys.manager.SysManager
     */
    public SysManager getEnableManagerByName(String managerName);

}
