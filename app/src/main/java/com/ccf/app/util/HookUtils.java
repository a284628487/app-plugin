package com.ccf.app.util;

import android.app.Activity;
import android.app.Instrumentation;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HookUtils {

    final static String TAG = "HookUtils";

    public static void hookActivityManager() {
        try {
            Class activityManagerClazz = Class.forName("android.app.ActivityManager");
            Field iAMSingletonField = activityManagerClazz.getDeclaredField("IActivityManagerSingleton");
            iAMSingletonField.setAccessible(true);
            Object iAMSingleton = iAMSingletonField.get(null);
            Log.e(TAG, "IActivityManagerSingleton = " + iAMSingleton);

            Method getInstanceMethod = Class.forName("android.util.Singleton").getDeclaredMethod("get");
            getInstanceMethod.setAccessible(true);
            Object iActivityManagerObject = getInstanceMethod.invoke(iAMSingleton);
            Log.e(TAG, "iActivityManager = " + iActivityManagerObject);

            Class iActivityManagerClazz = Class.forName("android.app.IActivityManager");
            Object proxyObject = Proxy.newProxyInstance(
                    Thread.currentThread().getContextClassLoader(),
                    new Class[]{iActivityManagerClazz},
                    new HookAmsHandler(iActivityManagerObject));

            Field mInstanceField = Class.forName("android.util.Singleton").getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            mInstanceField.set(iAMSingleton, proxyObject);
            // mInstance
        } catch (Exception e) {
            Log.e(TAG, "" + e);
        }
    }

    public static void hookActivityThreadInstrumentation() {
        try {
            Class activityThreadClazz = Class.forName("android.app.ActivityThread");
            Field atyThreadField = activityThreadClazz.getDeclaredField("sCurrentActivityThread");
            atyThreadField.setAccessible(true);
            Object activityThread = atyThreadField.get(null);
            Log.e(TAG, "ActivityThread = " + activityThread);

            Field mInstrumentationField = activityThreadClazz.getDeclaredField("mInstrumentation");
            mInstrumentationField.setAccessible(true);

            Instrumentation originalIns = (Instrumentation) mInstrumentationField.get(activityThread);
            mInstrumentationField.set(activityThread, new HookInstrumentation(originalIns));

            Log.e(TAG, "mInstrumentation = " + mInstrumentationField.get(activityThread));
        } catch (Exception e) {
            Log.e(TAG, "" + e);
        }
    }

    /**
     * not work
     * @param activity
     */
    public static void hookActivityInstrumentation(Activity activity) {
        try {
            Class activityClazz = Class.forName("android.app.Activity");

            Field mInstrumentationField = activityClazz.getDeclaredField("mInstrumentation");
            mInstrumentationField.setAccessible(true);

            Instrumentation originalIns = (Instrumentation) mInstrumentationField.get(activity);
            mInstrumentationField.set(activity, new HookInstrumentation(originalIns));

            Log.e(TAG, "mInstrumentation = " + mInstrumentationField.get(activity));
        } catch (Exception e) {
            Log.e(TAG, "" + e);
        }
    }
}