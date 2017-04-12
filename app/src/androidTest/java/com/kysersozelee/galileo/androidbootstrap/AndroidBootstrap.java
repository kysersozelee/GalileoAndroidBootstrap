package com.kysersozelee.galileo.androidbootstrap;

import android.app.Instrumentation;
import android.app.UiAutomation;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import com.kysersozelee.galileo.androidbootstrap.server.HttpServer;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by kysersoze.lee on 2017-04-11.
 */
@RunWith(AndroidJUnit4.class)
public class AndroidBootstrap {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        UiAutomation uiAutomation = instrumentation.getUiAutomation();
        UiDevice mUiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        HttpServer httpServer = new HttpServer(12868);
        httpServer.startServer(true);
    }
}
