package br.com.shapeup.common.utils;

import jakarta.servlet.http.HttpServletRequest;

public class TokenUtils {
    public static String getToken(HttpServletRequest request) {
        return request.getHeader("Authorization").substring(7);
    }
}
