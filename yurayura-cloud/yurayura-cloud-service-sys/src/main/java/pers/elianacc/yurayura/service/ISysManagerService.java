package pers.elianacc.yurayura.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import pers.elianacc.yurayura.dto.IdsDto;
import pers.elianacc.yurayura.dto.SysManagerInsertDto;
import pers.elianacc.yurayura.dto.SysManagerSelectDto;
import pers.elianacc.yurayura.dto.SysManagerUpdateDto;
import pers.elianacc.yurayura.entity.sys.manager.SysManager;
import pers.elianacc.yurayura.vo.SysManagerAndRoleVo;

import java.util.List;


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
     * @return com.github.pagehelper.PageInfo<pers.elianacc.yurayura.vo.SysManagerAndRoleVo>
     */
    public PageInfo<SysManagerAndRoleVo> getPage(SysManagerSelectDto dto);

    /**
     * 添加系统管理员
     *
     * @param dto
     * @return void
     */
    public void insert(SysManagerInsertDto dto);

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
     * @return void
     */
    public void update(SysManagerUpdateDto dto);

    /**
     * 查询管理员拥有角色的所有权限（根据管理员id）
     *
     * @param managerId
     * @return List<String>
     */
    public List<String> getManagerRolePermission(Integer managerId);

    /**
     * 查询启用的管理员（根据管理员名）
     *
     * @param managerName
     * @return pers.elianacc.yurayura.entity.sys.manager.SysManager
     */
    public SysManager getEnableManagerByName(String managerName);

}
