package com.kysersozelee.galileo.androidbootstrap.Component.impl;

import com.kysersozelee.galileo.androidbootstrap.Component.AAction;
import com.kysersozelee.galileo.androidbootstrap.core.Device;
import com.kysersozelee.galileo.androidbootstrap.server.HttpRequest;
import com.kysersozelee.galileo.androidbootstrap.server.HttpResponse;

import org.json.JSONObject;

import io.netty.handler.codec.http.HttpMethod;

/**
 * Created by kyser on 2017-04-12.
 */

public class GetOrientation extends AAction {
    private final String TAG = "GetOrientation";
    public GetOrientation(String url, HttpMethod method) {
        super(url, method);
    }

    @Override
    public ActionResult doAction(HttpRequest request, HttpResponse response){
        try {
            int rotation = Device.getUiDevice().getDisplayRotation();
            return new ActionResult(TAG, getCurrent(), rotation);
        }catch (Exception e){
            return new ActionResult(TAG, getCurrent(), -1);
        }
    }
}
