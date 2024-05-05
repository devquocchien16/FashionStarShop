package com.group4.fashionstarshop.sellercontroller.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(RedirectException.class)
    public void handleRedirect(RedirectException re, HttpServletResponse res) throws IOException {
        res.sendRedirect(re.getRedirectUrl());
    }
}
