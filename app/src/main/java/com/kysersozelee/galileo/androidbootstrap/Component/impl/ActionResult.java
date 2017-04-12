package com.kysersozelee.galileo.androidbootstrap.Component.impl;

import com.kysersozelee.galileo.androidbootstrap.Component.AActionResult;
import com.kysersozelee.galileo.androidbootstrap.Component.IActionResult;

import org.json.JSONObject;

/**
 * Created by kysersoze.lee on 2017-04-12.
 */

public class ActionResult extends AActionResult {

    protected ActionResult(String actionName, String datetime, Object resultValue) {
        super(actionName, datetime, resultValue);
    }
}
