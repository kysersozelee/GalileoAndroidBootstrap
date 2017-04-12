package com.kysersozelee.galileo.androidbootstrap.server.route;

import com.kysersozelee.galileo.androidbootstrap.server.IHandler;

import io.netty.handler.codec.http.HttpMethod;

/**
 * Created by kysersoze.lee on 2017-04-11.
 */

/**
 * The Route class represents a single entry in the RouteTable.
 */
public class Route {
    private final HttpMethod method;
    private final String path;
    private final IHandler handler;

    public Route(final HttpMethod method, final String path, final IHandler handler) {
        this.method = method;
        this.path = path;
        this.handler = handler;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public IHandler getHandler() {
        return handler;
    }

    public boolean matches(final HttpMethod method, final String path) {
        return this.method.equals(method) && this.path.equals(path);
    }
}
