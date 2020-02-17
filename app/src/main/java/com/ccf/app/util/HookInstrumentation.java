package com.ccf.app.util;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.Method;

public class HookInstrumentation extends Instrumentation {

    final String TAG = "HookInstrumentation";

    private Instrumentation mBase;

    public HookInstrumentation(Instrumentation mBase) {
        this.mBase = mBase;
    }

    public Activity newActivity(ClassLoader cl, String className, Intent intent)
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        Log.e(TAG, "newActivity: " + className);
        if (className.equals("com.ccf.app.RegisteredActivity")) {
            className = "com.ccf.app.HookedActivity";
        }
        return mBase.newActivity(cl, className, intent);
    }

    /**
     * not work
     *
     * @param who
     * @param contextThread
     * @param token
     * @param target
     * @param intent
     * @param requestCode
     * @param options
     * @return
     */
    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {
        Log.e(TAG, "execStartActivity");
        try {
            Method m = Instrumentation.class.getDeclaredMethod("execStartActivity",
                    Context.class, IBinder.class, IBinder.class, Activity.class
                    , Intent.class, int.class, Bundle.class);
            m.setAccessible(true);

            return (ActivityResult) m.invoke(mBase, who, contextThread, token, target, intent, requestCode, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
