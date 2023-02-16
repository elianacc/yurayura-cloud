package pers.elianacc.yurayura.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 权限授权-树形控件查询 bo
 *
 * @author ELiaNaCc
 * @since 2021-08-14
 */
@Data
public class SysPermissionAuthorTreeSelectVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Double id;

    /**
     * 子菜单标题
     */
    private String title;

    /**
     * 权限列表
     */
    private List<SysPermissionAuthorPermVo> permissionList;
}
