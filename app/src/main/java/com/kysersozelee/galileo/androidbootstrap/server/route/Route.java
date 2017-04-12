package com.kysersozelee.galileo.androidbootstrap.server.route;

import com.kysersozelee.galileo.androidbootstrap.Component.IAction;

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
    private final IAction action;

    public Route(final HttpMethod method, final String path, final IAction action) {
        this.method = method;
        this.path = path;
        this.action = action;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public IAction getAction() {
        return action;
    }

    public boolean matches(final HttpMethod method, final String path) {
        return this.method.equals(method) && this.path.equals(path);
    }
}
