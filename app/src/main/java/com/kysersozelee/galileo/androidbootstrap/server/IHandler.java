package com.kysersozelee.galileo.androidbootstrap.server;

/**
 * Created by kysersoze.lee on 2017-04-11.
 */


public interface IHandler {
    Object handle(HttpRequest request, HttpResponse response) throws Exception;
}
