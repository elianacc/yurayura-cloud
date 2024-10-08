package pers.elianacc.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户查询dto
 *
 * @author ELiaNaCc
 * @since 2020-03-30
 */
@Data
@ApiModel(value = "用户查询dto")
public class UserSelectDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名-昵称关键字
     */
    @ApiModelProperty(value = "用户名-昵称关键字")
    private String userNameKeyword;

    /**
     * 性别- 1：男，2：女
     */
    @ApiModelProperty(value = "性别- 1：男，2：女", example = "1")
    private Integer userSex;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String userPhoneNumber;

    /**
     * 状态- 0：正常，-3：小黑屋3天，-7：小黑屋7天，-30：小黑屋30天，-365：小黑屋365天，-999：小黑屋永久
     */
    @ApiModelProperty(value = "状态- 0：正常，-3：小黑屋3天，-7：小黑屋7天，-30：小黑屋30天，-365：小黑屋365天，-999：小黑屋永久", example = "0")
    private Integer userStatus;

    /**
     * 页码
     */
    @NotNull(message = "页码不能为空")
    @ApiModelProperty(value = "页码", required = true, example = "1")
    private Integer pageNum;

    /**
     * 页记录数
     */
    @NotNull(message = "页记录数不能为空")
    @ApiModelProperty(value = "页记录数", required = true, example = "10")
    private Integer pageSize;

}
