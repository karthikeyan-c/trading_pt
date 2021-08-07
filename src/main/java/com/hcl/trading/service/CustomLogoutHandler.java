package com.hcl.trading.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@Slf4j
public class CustomLogoutHandler implements LogoutHandler {

    private final UserService userService;

    public CustomLogoutHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) {
        userService.auditLogout(UserUtils.getAuthenticatedUserName());
        log.info("custom logout handler");
    }
}
