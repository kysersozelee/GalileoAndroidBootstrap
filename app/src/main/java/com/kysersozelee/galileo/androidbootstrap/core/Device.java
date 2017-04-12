package com.kysersozelee.galileo.androidbootstrap.core;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;

/**
 * Created by kyser on 2017-04-12.
 */

public class Device {

    public static final UiDevice getUiDevice() {
        return UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }
/*
TODO

    public static AndroidElement getAndroidElement(String id, Object element, By by) throws UiAutomator2Exception {
        if (element instanceof UiObject2) {
            return new UiObject2Element(id, (UiObject2) element, by);
        } else if (element instanceof UiObject) {
            return new UiObjectElement(id, (UiObject) element, by);
        } else {
            throw new UiAutomator2Exception("Unknown Element type: " + element.getClass().getName());
        }
    }
*/

    public static void wake() throws RemoteException {
        getUiDevice().wakeUp();
    }

    public static void scrollTo(String scrollToString) throws UiObjectNotFoundException {
        // TODO This logic needs to be changed according to the request body from the Driver
        UiScrollable uiScrollable = new UiScrollable(new UiSelector().scrollable(true).instance(0));
        uiScrollable.scrollIntoView(new UiSelector().descriptionContains(scrollToString).instance(0));
        uiScrollable.scrollIntoView(new UiSelector().textContains(scrollToString).instance(0));
    }

    public static boolean back() {
        return getUiDevice().pressBack();
    }

    /**
     * reason for explicit method, in some cases google UiAutomator2 throwing exception
     * while calling waitForIdle() which is causing appium UiAutomator2 server to fall in
     * unexpected behaviour.
     * for more info please refer
     * https://code.google.com/p/android/issues/detail?id=73297
     */
    public static void waitForIdle() {
        try {
            getUiDevice().waitForIdle();
        }catch (Exception e) {
            //TODO
            //Logger.error("Unable wait for AUT to idle");
        }
    }

    public static void waitForIdle(long timeInMS) {
        try {
            getUiDevice().waitForIdle(timeInMS);
        }catch (Exception e) {
            //TODO
            // Logger.error(String.format("Unable wait %d for AUT to idle", timeInMS));
        }
    }
}
