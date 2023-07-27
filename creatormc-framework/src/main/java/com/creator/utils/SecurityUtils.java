package com.creator.utils;

import com.creator.domain.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static final Long ADMIN_USER_ID = 1L;

    /**
     * 获取LoginUser
     * @return
     */
    public static LoginUser getLoginUser() {
        return (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 获取Authentication
     * @return
     */
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取当前用户是否是管理员
     * @return
     */
    public static Boolean isAdmin() {
        Long id = getLoginUser().getUser().getId();
        return id != null && id.equals(ADMIN_USER_ID);
    }

    /**
     * 获取用户id
     * @return
     */
    public static Long getUserId() {
        return getLoginUser().getUser().getId();
    }
}
