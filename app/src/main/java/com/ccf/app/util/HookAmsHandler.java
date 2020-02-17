package com.ccf.app.util;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class HookAmsHandler implements InvocationHandler {

    final String TAG = "HookAmsHandler";

    private Object iActivityManagerObject;

    public HookAmsHandler(Object iActivityManagerObject) {
        this.iActivityManagerObject = iActivityManagerObject;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        // Log.e(TAG, "invoke: " + o);
        // Log.e(TAG, "invoke: " + objects);
        Log.e(TAG, "invoke: " + method.getName());
        if (method.getName().equals("startActivity")) {
        }
        return method.invoke(iActivityManagerObject, objects);
    }
}
