package com.kysersozelee.galileo.androidbootstrap.server;

import java.nio.charset.Charset;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;

public class HttpResponse {

    private final FullHttpResponse response;
    private boolean closed;
    private Charset charset = CharsetUtil.UTF_8;
    private final String CONTENT_TYPE = "Content-Type";
    private final String CONTENT_ENCODING = "Content-Encoding";
    private final String CONTENT_LENGTH = "Content-Length";
    private final String LOCATION = "location";


    public HttpResponse(FullHttpResponse response) {
        this.response = response;
        response.headers().add(CONTENT_ENCODING, "identity");
    }

    public HttpResponse setStatus(int status) {
        response.setStatus(HttpResponseStatus.valueOf(status));
        return this;
    }

    public HttpResponse setContentType(String mimeType) {
        response.headers().add(CONTENT_TYPE, mimeType);
        return this;
    }

    public HttpResponse setContent(byte[] data) {
        response.headers().add(CONTENT_LENGTH, data.length);
        response.content().writeBytes(data);
        return this;
    }

    public HttpResponse setContent(String message) {
        setContent(message.getBytes(charset));
        return this;
    }

    public HttpResponse sendRedirect(String to) {
        //setStatus(HttpStatusCode.MOVED_PERMANENTLY.getStatusCode());
        response.headers().add(LOCATION, to);
        return this;
    }

    public HttpResponse sendTemporaryRedirect(String to) {
        //setStatus(HttpStatusCode.FOUND.getStatusCode());
        response.headers().add(LOCATION, to);
        return this;
    }

    public void end() {
        closed = true;
    }

    public boolean isClosed() {
        return closed;
    }

    public HttpResponse setEncoding(Charset charset) {
        this.charset = charset;
        return this;
    }
}
