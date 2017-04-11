package com.kysersozelee.galileo.androidbootstrap.server;

import java.util.HashMap;
import java.util.Map;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.CharsetUtil;

public class HttpRequest {
    private FullHttpRequest request;
    private Map<String, Object> data;

    public HttpRequest(FullHttpRequest request) {
        this.request = request;
        this.data = new HashMap<String, Object>();
    }

    public String method() {
        return request.method().name();
    }

    public String uri() {
        return request.uri();
    }

    public String body() {
        return request.content().toString(CharsetUtil.UTF_8);
    }

    public String header(String name) {
        return request.headers().get(name);
    }

    public Map<String, Object> data() {
        return data;
    }
}
