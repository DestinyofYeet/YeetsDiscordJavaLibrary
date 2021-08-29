package de.uwuwhatsthis.YeetsDiscordLibrary.utils;

import org.json.JSONObject;

public class Helper {
    public static String applyValueString(JSONObject data, String key){
        if (!data.has(key)) return null;
        return data.get(key).equals(JSONObject.NULL) ? null : data.getString(key);
    }

    public static boolean applyValueBool(JSONObject data, String key){
        if (!data.has(key)) return false;
        return data.getBoolean(key);
    }

    public static int applyValueInt(JSONObject data, String key){
        if (!data.has(key)) return -1;
        return data.getInt(key);
    }

    public static Long parseLong(String value){
        return Long.parseLong(value != null ? value : String.valueOf(-1));
    }

}
