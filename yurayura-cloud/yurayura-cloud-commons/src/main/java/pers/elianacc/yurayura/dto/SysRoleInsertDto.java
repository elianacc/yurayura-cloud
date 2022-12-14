package pers.elianacc.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 系统角色添加 dto
 *
 * @author ELiaNaCc
 * @since 2022-03-09
 */
@Data
@ApiModel(value = "系统角色添加dto")
public class SysRoleInsertDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名
     */
    @ApiModelProperty(value = "角色名", required = true)
    private String roleName;

    /**
     * 状态- 0：禁用，1：启用
     */
    @ApiModelProperty(value = "状态- 0：禁用，1：启用", required = true, example = "1")
    private Integer roleStatus;

    /**
     * 拥有权限id组
     */
    @ApiModelProperty(value = "拥有权限id组")
    private List<Integer> permissionIdArr;

}
