package com.kysersozelee.galileo.androidbootstrap;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.kysersozelee.galileo.androidbootstrap.server.HttpServer;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by kysersoze.lee on 2017-04-11.
 */
@RunWith(AndroidJUnit4.class)
public class AndroidBootstrap {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        HttpServer httpServer = new HttpServer(12868);

        httpServer.get("/test", ((request, response) -> "TEST OK"))
                .get("/test2", ((request, response) -> "TEST2 OK"))
                .post("/test3", ((request, response) -> "TEST3 OK body : " + request.body()));


        httpServer.startServer(true);
    }
}
