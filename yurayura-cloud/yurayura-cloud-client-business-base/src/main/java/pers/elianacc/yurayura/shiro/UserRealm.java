package pers.elianacc.yurayura.shiro;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.util.ObjectUtils;
import pers.elianacc.yurayura.entity.sys.manager.SysManager;
import pers.elianacc.yurayura.feign.SysFeignClient;
import pers.elianacc.yurayura.vo.ApiResult;

import javax.annotation.Resource;

/**
 * 自定义shiro Realm
 *
 * @author ELiaNaCc
 * @since 2020-12-02
 */
public class UserRealm extends AuthorizingRealm {

    @Resource
    private SysFeignClient sysFeignClient;

    /**
     * 用户授权
     *
     * @param principalCollection
     * @return org.apache.shiro.authz.AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Subject subject = SecurityUtils.getSubject();
        // 当前用户未认证，清空授权信息
        if (!subject.isAuthenticated()) {
            doClearCache(principalCollection);
            subject.logout();
            return null;
        }

        SysManager currentSysManager = (SysManager) principalCollection.getPrimaryPrincipal();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        ApiResult<String> apiResult = sysFeignClient.getManagerRolePermission(currentSysManager.getId());
        if (apiResult.getCode() == 200) {
            info.addStringPermission(apiResult.getData());
        }

        return info;
    }

    /**
     * 用户认证
     *
     * @param authenticationToken
     * @return org.apache.shiro.authc.AuthenticationInfo
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;

        String username = userToken.getUsername();
        SysManager sysManager = new SysManager();
        ApiResult<SysManager> apiResult = sysFeignClient.getEnableManagerByName(username);
        if (apiResult.getCode() == 200) {
            sysManager = JSON.toJavaObject(JSON.parseObject(JSON.toJSONString(apiResult.getData())), SysManager.class);
        }

        // 判断用户是否存在
        if (ObjectUtils.isEmpty(sysManager)) {
            return null;
        }

        // 密码认证 shiro来处理
        return new SimpleAuthenticationInfo(sysManager, sysManager.getManagerPassword(), getName());
    }
}
