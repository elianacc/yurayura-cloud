package pers.elianacc.yurayura.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 权限授权-树形控件权限 bo
 *
 * @author ELiaNaCc
 * @since 2021-08-14
 */
@Data
public class SysPermissionAuthorPermBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限id
     */
    private Integer id;

    /**
     * 权限名
     */
    private String title;
}
