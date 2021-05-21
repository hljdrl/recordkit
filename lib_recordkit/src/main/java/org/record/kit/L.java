package org.record.kit;

public final class  L {

    private static boolean INFO  = true;
    private static boolean DEBUG = true;
    private static boolean ERROR = true;
    private static LogKit mLogKit = new Logger();
    public static void init(boolean info,boolean debug,boolean error){
        INFO = info;
        DEBUG = debug;
        ERROR = error;
        if(mLogKit==null){
            mLogKit = new Logger();
        }
    }

    public static void i(String tag, String... log) {
        if(INFO) {
            mLogKit.i(tag,log);
        }
    }
    public static void ii(String tag, Object... log) {
        if(INFO) {
            mLogKit.ii(tag,log);
        }
    }

    public static void d(String tag, String... log) {
        if(DEBUG) {
            mLogKit.d(tag,log);
        }
    }
    public static void dd(String tag, Object... log) {
        if(DEBUG) {
            mLogKit.dd(tag,log);
        }
    }
}
