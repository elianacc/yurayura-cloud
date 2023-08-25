package pers.elianacc.yurayura.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * sa-token路由拦截鉴权 config
 *
 * @author ELiaNaCc
 * @since 2023-08-24
 */
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，定义详细认证规则
        registry.addInterceptor(new SaInterceptor(handler -> {
            // 指定一条 match 规则
            SaRouter
                    .match("/**") // 拦截的 path 列表，可以写多个 */
                    .notMatch("/api/sys/manager/getVerifyCode") // 排除掉的 path 列表，可以写多个
                    .notMatch("/api/sys/manager/login")
                    .notMatch("/api/sys/manager/logout")
                    .notMatch("/api/sys/manager/judgeAuthen")
                    .check(r -> StpUtil.checkLogin()); // 要执行的校验动作，可以写完整的 lambda 表达式

            SaRouter
                    .match("/api/sys/menu/get*")
                    .notMatch("/api/sys/menu/getSysSideMenu")
                    .check(r -> StpUtil.checkPermission("sys_menu_select"));
            SaRouter.match("/api/sys/menu/insert*", r -> StpUtil.checkPermission("sys_menu_insert"));
            SaRouter.match("/api/sys/menu/update*", r -> StpUtil.checkPermission("sys_menu_update"));
            SaRouter.match("/api/sys/menu/delete*", r -> StpUtil.checkPermission("sys_menu_delete"));

            SaRouter
                    .match("/api/sys/menuSub/get*")
                    .notMatch("/api/sys/menuSub/getByIndex")
                    .notMatch("/api/sys/menuSub/getAll")
                    .check(r -> StpUtil.checkPermission("sys_menu_select"));
            SaRouter.match("/api/sys/menuSub/insert*", r -> StpUtil.checkPermission("sys_menu_insert"));
            SaRouter.match("/api/sys/menuSub/update*", r -> StpUtil.checkPermission("sys_menu_update"));
            SaRouter.match("/api/sys/menuSub/delete*", r -> StpUtil.checkPermission("sys_menu_delete"));

            SaRouter
                    .match("/api/sys/dict/get*")
                    .notMatch("/api/sys/dict/getAll")
                    .notMatch("/api/sys/dict/getByDictCode")
                    .check(r -> StpUtil.checkPermission("sys_dict_select"));
            SaRouter.match("/api/sys/dict/insert*", r -> StpUtil.checkPermission("sys_dict_insert"));
            SaRouter.match("/api/sys/dict/update*", r -> StpUtil.checkPermission("sys_dict_update"));

            SaRouter
                    .match("/api/sys/manager/get*")
                    .notMatch("/api/sys/manager/getVerifyCode")
                    .notMatch("/api/sys/manager/getCurrentManagerMsg")
                    .check(r -> StpUtil.checkPermission("sys_manager_select"));
            SaRouter.match("/api/sys/manager/insert*", r -> StpUtil.checkPermission("sys_manager_insert"));
            SaRouter.match("/api/sys/manager/update*", r -> StpUtil.checkPermission("sys_manager_update"));

            SaRouter
                    .match("/api/sys/role/get*")
                    .notMatch("/api/sys/role/getAll")
                    .check(r -> StpUtil.checkPermission("sys_role_select"));
            SaRouter.match("/api/sys/role/insert*", r -> StpUtil.checkPermission("sys_role_insert"));
            SaRouter.match("/api/sys/role/update*", r -> StpUtil.checkPermission("sys_role_update"));

            SaRouter
                    .match("/api/sys/permission/get*")
                    .notMatch("/api/sys/permission/getPermissionAuthorTree")
                    .check(r -> StpUtil.checkPermission("sys_permission_select"));
            SaRouter.match("/api/sys/permission/insert*", r -> StpUtil.checkPermission("sys_permission_insert"));
            SaRouter.match("/api/sys/permission/update*", r -> StpUtil.checkPermission("sys_permission_update"));


            SaRouter.match("/api/comic/get*", r -> StpUtil.checkPermission("comic_info_select"));
            SaRouter.match("/api/comic/insert*", r -> StpUtil.checkPermission("comic_info_insert"));
            SaRouter.match("/api/comic/update*", r -> StpUtil.checkPermission("comic_info_update"));
            SaRouter.match("/api/comic/deleteBatchByIds", r -> StpUtil.checkPermission("comic_info_deleteBatch"));

            SaRouter.match("/api/user/get*", r -> StpUtil.checkPermission("user_info_select"));
            SaRouter.match("/api/user/update*", r -> StpUtil.checkPermission("user_info_update"));

        })).addPathPatterns("/**");
    }
}
