package org.record.util;

public final class StringCompat {

    public static String strings(String... list) {
        StringBuilder builder = new StringBuilder();
        for (String s : list) {
            if (s != null) {
                builder.append(s);
            }
        }
        return builder.toString();
    }

    public static String strings(Object... list) {
        StringBuilder builder = new StringBuilder();
        for (Object s : list) {
            if (s != null) {
                builder.append(s);
            }
        }
        return builder.toString();
    }
}
