package com.dispatch.system.utils;

import java.util.HashMap;
import java.util.Map;

public class BusinessHelper {

    private static Map<String, String> map = new HashMap<>();

    public static void insert(String key, String value) {
        map.put(key, value);
    }

    public static String get(String key) {
        if (!map.containsKey(key)) {
            return "其他快递";
        }
        return map.get(key);
    }

    public static String getKey(String value) {
        if (map.containsValue(value)) {
            Object[] objects = map.keySet().toArray();
            for (int i = 0; i < objects.length; i++) {
                if (map.get(objects[i]).equals(value)) {
                    return (String)objects[i];
                }
            }
        }
        return "";
    }

    public static Map<String, String> getMap() {
        return map;
    }
}
