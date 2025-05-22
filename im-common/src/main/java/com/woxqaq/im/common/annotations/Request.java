package com.woxqaq.im.common.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Request {
    String method() default "POST";

    String url() default "";

    String GET = "GET";
    String POST = "POST";
}
