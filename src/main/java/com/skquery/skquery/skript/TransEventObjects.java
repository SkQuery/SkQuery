package com.skquery.skquery.skript;

import org.bukkit.event.Event;

import java.util.HashMap;

public class TransEventObjects {

    private static HashMap<Event, Object> limbo = new HashMap<>();

    public static void store(Event key, Object value) {
        limbo.put(key, value);
    }

    public static Object retrieve(Event key) {
        Object value = limbo.get(key);
        limbo.remove(key);
        return value;
    }
}
