package arch.record.kit;

import android.util.Log;

public class L {

    public static void i(String tag, String... log) {
        StringBuilder builder = new StringBuilder();
        for (String s : log) {
            if (s != null) {
                builder.append(s);
            }
        }
        Log.i(tag, builder.toString());
    }

    public static void i(String tag, Object... log) {
        StringBuilder builder = new StringBuilder();
        for (Object s : log) {
            if (s != null) {
                builder.append(s.toString());
            }
        }
        Log.i(tag, builder.toString());
    }

    public static void d(String tag, String... log) {
        StringBuilder builder = new StringBuilder();
        for (String s : log) {
            if (s != null) {
                builder.append(s);
            }
        }
        Log.d(tag, builder.toString());
    }
    public static void d(String tag, Object... log) {
        StringBuilder builder = new StringBuilder();
        for (Object s : log) {
            if (s != null) {
                builder.append(s.toString());
            }
        }
        Log.d(tag, builder.toString());
    }
}
