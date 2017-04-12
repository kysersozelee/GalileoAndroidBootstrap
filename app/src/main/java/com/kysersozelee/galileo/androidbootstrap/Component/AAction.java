package com.kysersozelee.galileo.androidbootstrap.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.netty.handler.codec.http.HttpMethod;

/**
 * Created by kyser on 2017-04-12.
 */

public abstract class AAction implements IAction {
    private final String mappedUrl;
    private final HttpMethod method;

    protected AAction(String mappedUrl, HttpMethod method) {
        this.mappedUrl = mappedUrl;
        this.method = method;
    }
    public String getCurrent(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.KOREA);
        Date date = new Date((System.currentTimeMillis() / 1000L + 2208988800L));
        return sdf.format(date);
    }

    public String getMappedUrl() {
        return mappedUrl;
    }

    public HttpMethod getMethod() {
        return method;
    }
}
