package com.emailService.utils;

import jakarta.servlet.http.HttpServletRequest;

public class ApplicationUrl {

    public static String getApplicationUrl(HttpServletRequest request){
        String appUrl = request.getRequestURL().toString();
        return appUrl.replace(request.getServletPath(),"");
    }
}
