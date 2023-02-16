package pers.elianacc.yurayura.component;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import pers.elianacc.yurayura.bo.MailBo;
import pers.elianacc.yurayura.util.MailUtil;
import pers.elianacc.yurayura.vo.ApiResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局捕获异常（500） component
 *
 * @author ELiaNaCc
 * @since 2019-3-29
 */
@Component
@Slf4j
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    @Value("${yurayura.receive-email}")
    private String receiveEmail;
    @Value("${spring.application.name}")
    private String applicationName;

    @SneakyThrows
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception exception) {

        String errorMsg; // 错误信息

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
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(ApiResult.fail(applicationName + "服务异常")));
        return new ModelAndView();

    }

}
