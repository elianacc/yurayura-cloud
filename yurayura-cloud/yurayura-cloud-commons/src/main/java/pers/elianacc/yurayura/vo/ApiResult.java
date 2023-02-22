package pers.elianacc.yurayura.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 接口返回 vo
 *
 * @author ELiaNaCc
 * @since 2023-02-16
 */
@Data
@ApiModel(value = "接口返回VO", description = "接口返回对象")
public class ApiResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final static int SUCCESS_CODE = 200; // 服务器处理成功状态码
    private final static int FAIL_CODE = 500; // 服务器错误状态码
    private final static int WARN_CODE = 102; // 服务器警告状态码

    private final static int NOT_AUTHENTICATION = 401; // 未认证
    private final static int NOT_AUTHORIZATION = 405; // 未授权

    private final static String NOT_AUTHENTICATION_MSG = "登入认证过期，请重新登入"; // 未认证提示信息
    private final static String NOT_AUTHORIZATION_MSG = "还未授权此操作"; // 未授权提示信息

    /**
     * 状态码
     */
    @ApiModelProperty("状态码")
    private Integer code;
    /**
     * 返回体
     */
    @ApiModelProperty("返回体")
    private T data;
    /**
     * 返回信息
     */
    @ApiModelProperty("返回信息")
    private String msg;
    /**
     * 时间戳
     */
    @ApiModelProperty("时间戳")
    private long timestamp = System.currentTimeMillis();

    public static <T> ApiResult<T> success() {
        ApiResult<T> r = new ApiResult();
        r.setCode(SUCCESS_CODE);
        return r;
    }

    public static <T> ApiResult<T> success(String msg) {
        ApiResult<T> r = new ApiResult();
        r.setCode(SUCCESS_CODE);
        r.setMsg(msg);
        return r;
    }

    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> r = new ApiResult();
        r.setCode(SUCCESS_CODE);
        r.setData(data);
        return r;
    }

    public static <T> ApiResult<T> success(String msg, T data) {
        ApiResult<T> r = new ApiResult();
        r.setCode(SUCCESS_CODE);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static <T> ApiResult<T> warn() {
        ApiResult<T> r = new ApiResult();
        r.setCode(WARN_CODE);
        return r;
    }

    public static <T> ApiResult<T> warn(String msg) {
        ApiResult<T> r = new ApiResult();
        r.setCode(WARN_CODE);
        r.setMsg(msg);
        return r;
    }

    public static <T> ApiResult<T> fail() {
        ApiResult<T> r = new ApiResult();
        r.setCode(FAIL_CODE);
        return r;
    }

    public static <T> ApiResult<T> fail(String msg) {
        ApiResult<T> r = new ApiResult();
        r.setCode(FAIL_CODE);
        r.setMsg(msg);
        return r;
    }

    public static <T> ApiResult<T> notAuthentication() {
        ApiResult<T> r = new ApiResult();
        r.setCode(NOT_AUTHENTICATION);
        r.setMsg(NOT_AUTHENTICATION_MSG);
        return r;
    }

    public static <T> ApiResult<T> notAuthorization() {
        ApiResult<T> r = new ApiResult();
        r.setCode(NOT_AUTHORIZATION);
        r.setMsg(NOT_AUTHORIZATION_MSG);
        return r;
    }

}
