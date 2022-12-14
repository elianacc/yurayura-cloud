package pers.elianacc.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 系统管理员修改 dto
 *
 * @author ELiaNaCc
 * @since 2022-02-22
 */
@Data
@ApiModel(value = "系统管理员修改dto")
public class SysManagerUpdateDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id", required = true, example = "1")
    private Integer id;

    /**
     * 管理员密码
     */
    @ApiModelProperty(value = "管理员密码")
    private String managerPassword;

    /**
     * 状态- 0：禁用，1：启用
     */
    @ApiModelProperty(value = "状态- 0：禁用，1：启用", required = true, example = "1")
    private Integer managerStatus;

    /**
     * 拥有角色id组
     */
    @ApiModelProperty(value = "拥有角色id组")
    private List<Integer> roleIdArr;

}
