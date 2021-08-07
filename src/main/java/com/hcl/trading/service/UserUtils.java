package com.hcl.trading.service;

import com.hcl.trading.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {

    public static String getAuthenticatedUserName() {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        return auth != null ? ((User) auth.getPrincipal()).getUsername() : null;
    }

}
