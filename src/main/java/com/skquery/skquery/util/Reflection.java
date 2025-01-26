package com.skquery.skquery.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflection {

	public static Class<?> getNMSClass(String nms) {
		try {
			return Class.forName("net.minecraft.server." + getServerVersion() + "." + nms);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static Class<?> getOBCClass(String obc) {
		try {
			return Class.forName("org.bukkit.craftbukkit." + getServerVersion() + "." + obc);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static Object getHandle(Object obj) {
		try {
			return getMethod(obj.getClass(), "getHandle").invoke(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Field getField(Class<?> clazz, String name) {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			return field;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Method getMethod(Class<?> clazz, String name, Class<?>... args) {
		for (Method m : clazz.getMethods())
			if (m.getName().equals(name) && (args.length == 0 || classesEqual(args, m.getParameterTypes()))) {
				m.setAccessible(true);
				return m;
			}
		return null;
	}

	public static boolean classesEqual(Class<?>[] l1, Class<?>[] l2) {
		boolean equal = true;
		if (l1.length != l2.length)
			return false;
		for (int i = 0; i < l1.length; i++)
			if (l1[i] != l2[i]) {
				equal = false;
				break;
			}
		return equal;
	}

	public static void sendPacket(Object packet, Player... players) {
		try {
			for (Player p : players) {
				Object craftPlayer = Reflection.getHandle(p);
				Object connection = Reflection.getField(craftPlayer.getClass(), "playerConnection").get(craftPlayer);
				Reflection.getMethod(connection.getClass(), "sendPacket").invoke(connection, packet);
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public static String getServerVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().replace(".", "@").split("@")[3];
	}

	public static Class<?> getCaller() {
		try {
			return Class.forName(Thread.currentThread().getStackTrace()[3].getClassName(), false, Reflection.class.getClassLoader());
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
}
