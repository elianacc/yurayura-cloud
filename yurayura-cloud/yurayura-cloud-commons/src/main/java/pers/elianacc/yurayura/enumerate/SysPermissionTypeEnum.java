package pers.elianacc.yurayura.enumerate;

import lombok.Getter;

/**
 * 权限类型 enum
 *
 * @author ELiaNaCc
 * @since 2021-03-25
 */
@Getter
public enum SysPermissionTypeEnum {

    /**
     * 菜单
     */
    MENU(1),
    /**
     * 按钮
     */
    BUTTON(2);

    private Integer typeId;

    SysPermissionTypeEnum(int typeId) {
        this.typeId = typeId;
    }

}
