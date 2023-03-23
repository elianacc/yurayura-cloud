package pers.elianacc.yurayura.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pers.elianacc.yurayura.bo.MailBo;
import pers.elianacc.yurayura.util.MailUtil;
import pers.elianacc.yurayura.vo.ApiResult;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局捕获异常 component
 *
 * @author ELiaNaCc
 * @since 2023-03-23
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Value("${yurayura.receive-email}")
    private String receiveEmail;
    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 处理系统异常
     *
     * @param exception
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @ExceptionHandler(Exception.class)
    public ApiResult<String> exceptionHandler(Exception exception) {
        // 错误信息
        String errorMsg;
        // 异常为系统异常
        errorMsg = ExceptionUtils.getStackTrace(exception);
        log.error(errorMsg);
        // 发送错误信息邮件
        MailBo bo = new MailBo();
        bo.setTitle("【" + applicationName + "】服务报错信息");
        bo.setReceiveEmail(receiveEmail);
        Map<String, Object> map = new HashMap<>();
        map.put("errorMsg", errorMsg);
        bo.setAnnexOrData(map);
        MailUtil.sendTemplateMail(bo, "error/500");
        return ApiResult.fail(applicationName + "服务异常");
    }

}
