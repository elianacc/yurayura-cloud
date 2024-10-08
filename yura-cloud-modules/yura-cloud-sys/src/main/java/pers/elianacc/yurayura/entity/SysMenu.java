package pers.elianacc.yurayura.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统菜单 entity
 *
 * @author ELiaNaCc
 * @since 2022-02-26
 */
@Data
@TableName("yurayura_sys_menu")
@ApiModel(value = "Menu对象", description = "系统菜单")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 标题
     */
    @TableField("menu_title")
    @ApiModelProperty(value = "标题")
    private String menuTitle;

    /**
     * 标识
     */
    @TableField("menu_name")
    @ApiModelProperty(value = "标识")
    private String menuName;

    /**
     * 图标样式
     */
    @TableField("menu_icon_class")
    @ApiModelProperty(value = "图标样式")
    private String menuIconClass;

    /**
     * 类型- 1：一级菜单，2：二级菜单
     */
    @TableField("menu_type")
    @ApiModelProperty(value = "类型- 1：一级菜单，2：二级菜单")
    private Integer menuType;

    /**
     * 序号
     */
    @TableField("menu_seq")
    @ApiModelProperty(value = "序号")
    private Integer menuSeq;

    /**
     * 状态- 0：隐藏，1：显示
     */
    @TableField("menu_status")
    @ApiModelProperty(value = "状态- 0：隐藏，1：显示")
    private Integer menuStatus;


}
