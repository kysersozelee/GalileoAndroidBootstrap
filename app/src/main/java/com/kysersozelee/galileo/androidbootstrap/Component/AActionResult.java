package com.kysersozelee.galileo.androidbootstrap.Component;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/**
 * Created by kyser on 2017-04-12.
 */

public class AActionResult implements  IActionResult{
    private String actionName;
    private String datetime;
    private Object resultValue;

    protected AActionResult(String actionName, String datetime, Object resultValue) {
        this.actionName = actionName;
        this.datetime = datetime;
        this.resultValue = resultValue;
    }



    @Override
    public JSONObject getResultByJson() {
        JSONObject resultJsonObj = new JSONObject();
        try {
            resultJsonObj.put("actionName", actionName);
            resultJsonObj.put("datetime", datetime);
            resultJsonObj.put("resultValue", resultValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return resultJsonObj;
    }


    public String getActionName() {
        return actionName;
    }

    public String getDatetime() {
        return datetime;
    }

    public Object getResultValue() {
        return resultValue;
    }
}
