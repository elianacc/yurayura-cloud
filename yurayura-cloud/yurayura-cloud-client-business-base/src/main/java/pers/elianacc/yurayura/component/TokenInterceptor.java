package pers.elianacc.yurayura.component;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import pers.elianacc.yurayura.exception.BusinessException;
import pers.elianacc.yurayura.vo.ApiResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Token验证 interceptor
 *
 * @author ELiaNaCc
 * @since 2024-03-31
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 获取前端请求头中的token
        String token = request.getHeader("Token");

        if (ObjectUtils.isEmpty(token)) {
            throw new BusinessException(ApiResult.NOT_AUTHENTICATION, "Token已过期，请重新登入");
        }

        String expirationStr = StpUtil.getExtra("expiration").toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date expiration = sdf.parse(expirationStr);

        if(expiration.before(new Date())) {
            throw new BusinessException(ApiResult.NOT_AUTHENTICATION, "Token已过期，请重新登入");
        }

        return true;
    }
}
