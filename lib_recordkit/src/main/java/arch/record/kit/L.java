package arch.record.kit;

import android.util.Log;

public class L {
    private static boolean INFO  = true;
    private static boolean DEBUG = true;
    private static boolean ERROR = true;
    public static void init(boolean info,boolean debug,boolean error){
        INFO = info;
        DEBUG = debug;
        ERROR = error;
    }
    public static void i(String tag, String... log) {
        if(INFO) {
            StringBuilder builder = new StringBuilder();
            for (String s : log) {
                if (s != null) {
                    builder.append(s);
                }
            }
            Log.i(tag, builder.toString());
        }
    }

    public static void i(String tag, Object... log) {
        if(INFO) {
            StringBuilder builder = new StringBuilder();
            for (Object s : log) {
                if (s != null) {
                    builder.append(s.toString());
                }
            }
            Log.i(tag, builder.toString());
        }
    }

    public static void d(String tag, String... log) {
        if(DEBUG) {
            StringBuilder builder = new StringBuilder();
            for (String s : log) {
                if (s != null) {
                    builder.append(s);
                }
            }
            Log.d(tag, builder.toString());
        }
    }
    public static void d(String tag, Object... log) {
        if(DEBUG) {
            StringBuilder builder = new StringBuilder();
            for (Object s : log) {
                if (s != null) {
                    builder.append(s.toString());
                }
            }
            Log.d(tag, builder.toString());
        }
    }

}
