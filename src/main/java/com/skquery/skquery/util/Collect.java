package com.skquery.skquery.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Collect {
    public static String[] toStringArray(Object[] array) {
        String[] strings = new String[array.length];
        for (int i = 0; i < strings.length; i++) strings[i] = array[i].toString();
        return strings;
    }

    public static String[] toFriendlyStringArray(Object[] array) {
        String[] strings = new String[array.length];
        for (int i = 0; i < strings.length; i++) strings[i] = array[i].toString().toLowerCase().replace("_", " ");
        return strings;
    }

    @SafeVarargs
    public static <T> T[] asArray(T... objects) {
        return objects;
    }

    @SuppressWarnings("unchecked")
	public static <T> T[] newArray(Class<?> type, int size) {
        return (T[]) Array.newInstance(type, size);
    }

    public static String[] asSkriptProperty(String property, String fromType) {
        return asArray("[the] " + property + " of %" + fromType + "%", "%" + fromType + "%'[s] " + property);
    }

    public static <T> String toString(T[] array) {
        return toString(array, ',');
    }

    public static <T> String toString(T[] array, char separator) {
        return toString(array, separator, true);
    }


    public static <T> String toString(T[] array, char separator, boolean spaces) {
        String SEPARATOR = spaces ? " " : "";
        if (array == null)
            return "null";
        int max = array.length - 1;
        if (max == -1)
            return "";
        String b = "";
        for (int i = 0; ; i++) {
            b += String.valueOf(array[i]);
            if (i == max)
                return b;
            b += separator + SEPARATOR;
        }
    }

    public static String getPaths(Collection<String> list) {
        StringBuilder builder = new StringBuilder();
        for (String s : list) {
            builder.append(s).append(".");
        }
        return builder.toString().substring(0, builder.length() - 1);
    }

    @SuppressWarnings("resource")
	public static String textPart(InputStream is) {
        if (is == null) return "";
        try (Scanner s = new Scanner(is).useDelimiter("\\A")) {
            return s.hasNext() ? s.next() : "";
        }
    }

    private static ArrayList<File> getListFiles(File root, final FilenameFilter filter, ArrayList<File> toAdd) {
        for (File f : root.listFiles()) {
            if (f.isDirectory()) toAdd.addAll(getListFiles(f, filter, toAdd));
            else if (filter.accept(f, f.getName())) toAdd.add(f);
        }
        return toAdd;
    }

    public static File[] getFiles(File root, FilenameFilter filter) {
        ArrayList<File> files = getListFiles(root, filter, new ArrayList<File>());
        return files.toArray(new File[files.size()]);
    }

    public static File[] getFiles(File root) {
        return getFiles(root, new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return true;
            }
        });
    }
}
