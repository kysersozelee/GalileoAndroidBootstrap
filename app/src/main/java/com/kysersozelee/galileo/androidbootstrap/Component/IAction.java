package com.kysersozelee.galileo.androidbootstrap.Component;

import com.kysersozelee.galileo.androidbootstrap.Component.impl.ActionResult;
import com.kysersozelee.galileo.androidbootstrap.server.HttpRequest;
import com.kysersozelee.galileo.androidbootstrap.server.HttpResponse;

import org.json.JSONObject;

/**
 * Created by kysersoze.lee on 2017-04-11.
 */


public interface IAction {
    ActionResult doAction(HttpRequest request, HttpResponse response);
}
