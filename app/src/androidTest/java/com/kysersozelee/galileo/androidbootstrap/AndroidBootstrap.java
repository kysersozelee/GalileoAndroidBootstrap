package com.kysersozelee.galileo.androidbootstrap;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.kysersozelee.galileo.androidbootstrap.server.HttpServer;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
/**
 * Instrumentation test, which wilwwwwwwwwwwwwl execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AndroidBootstrap {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        HttpServer httpServer = new HttpServer(12868);
        httpServer.startServer(true);

    }
}
