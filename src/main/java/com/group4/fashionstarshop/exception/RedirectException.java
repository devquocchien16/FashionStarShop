package com.group4.fashionstarshop.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedirectException extends RuntimeException{
    private String redirectUrl;

    public RedirectException(String message, String rUrl) {
        super(message);
        this.redirectUrl = rUrl;
    }
}
