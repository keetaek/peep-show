package com.kakaw.peepshow.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.common.base.Strings;
import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by keetaekhong on 1/2/15.
 */
public class SharedPreferenceHelper {


    private static final String DEFAULT_PREFS = "default";

    public static SharedPreferences getSharedPreferences(Context context) {
        //set to mode private
        return context.getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return getSharedPreferences(context).edit();
    }

    public static void putObject(Context context, String key, Object object, Type type) {
        Gson gson = new Gson();
        String json = gson.toJson(object, type);
        getEditor(context).putString(key, json).commit();
    }

    public static <T> T getObject(Context context, String key, String def, Type T) {
        Gson gson = new Gson();
        T obj = null;
        String json = getSharedPreferences(context).getString(key, def);
        if (!Strings.isNullOrEmpty(json)) {
            obj = gson.fromJson(json, T);
        }
        return obj;
    }

    public static void putString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();
    }

    public static void putBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();
    }

    public static void putFloat(Context context, String key, float value) {
        getEditor(context).putFloat(key, value).commit();
    }

    public static void putInt(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();
    }

    public static void putLong(Context context, String key, long value) {
        getEditor(context).putLong(key, value).commit();
    }

    public static void remove(Context context, String key) {
        getEditor(context).remove(key).commit();
    }

    public static String getString(Context context, String key, String def) {
        return getSharedPreferences(context).getString(key, def);
    }

    public static boolean contains(Context context, String key) {
        return getSharedPreferences(context).contains(key);
    }

    public static boolean getBoolean(Context context, String key, boolean def) {
        return getSharedPreferences(context).getBoolean(key, def);
    }

    public static float getFloat(Context context, String key, float def) {
        return getSharedPreferences(context).getFloat(key, def);
    }

    public static int getInt(Context context, String key, int def) {
        return getSharedPreferences(context).getInt(key, def);
    }

    public static long getLong(Context context, String key, long def) {
        return getSharedPreferences(context).getLong(key, def);
    }


}
