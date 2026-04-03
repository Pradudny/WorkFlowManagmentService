package com.company.Incident.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class UserUtil {

    public static final String USER_EMAIL_HEADER = "X-User-Email";

    public static String getCurrentUserEmail(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String email = request.getHeader(USER_EMAIL_HEADER);
        return email == null || email.isBlank() ? null : email.trim();
    }

    public static String getCurrentUserEmail() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            return null;
        }
        return getCurrentUserEmail(attrs.getRequest());
    }
}
