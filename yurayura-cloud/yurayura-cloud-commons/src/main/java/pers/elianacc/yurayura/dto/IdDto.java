package pers.elianacc.yurayura.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 传递实体主键值 dto
 *
 * @author ELiaNaCc
 * @since 2022-02-17
 */
@Data
@ApiModel(value = "传递实体主键值dto")
public class IdDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id", required = true, example = "1")
    private Integer id;

}
