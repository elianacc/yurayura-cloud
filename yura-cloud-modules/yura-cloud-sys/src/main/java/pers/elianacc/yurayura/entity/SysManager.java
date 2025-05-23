package pers.elianacc.yurayura.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统管理员 entity
 *
 * @author ELiaNaCc
 * @since 2022-02-26
 */
@Data
@TableName("yurayura_sys_manager")
@ApiModel(value = "Manager对象", description = "系统管理员")
public class SysManager implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 管理员名
     */
    @TableField("manager_name")
    @ApiModelProperty(value = "管理员名")
    private String managerName;

    /**
     * 管理员密码
     */
    @TableField("manager_password")
    @ApiModelProperty(value = "管理员密码")
    private String managerPassword;

    /**
     * 管理员密码盐
     */
    @TableField("manager_pass_salt")
    @ApiModelProperty(value = "管理员密码盐")
    private String managerPassSalt;

    /**
     * 状态- 0：禁用，1：启用
     */
    @TableField("manager_status")
    @ApiModelProperty(value = "状态- 0：禁用，1：启用")
    private Integer managerStatus;

    /**
     * 管理员组织
     */
    @TableField("manager_org")
    @ApiModelProperty(value = "管理员组织")
    private Integer managerOrg;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("manager_create_time")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime managerCreateTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("manager_update_time")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime managerUpdateTime;


}
