package com.skquery.skquery.util;

public class Reflection {

	public static Class<?> getCaller() {
		try {
			return Class.forName(Thread.currentThread().getStackTrace()[3].getClassName(), false, Reflection.class.getClassLoader());
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
}
