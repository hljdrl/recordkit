package org.record.kit;


import android.util.Log;

import org.record.util.StringCompat;

class Logger implements LogKit {

    @Override
    public void i(String tag, String... log) {
        Log.i(tag, StringCompat.strings(log));
    }
    @Override
    public void ii(String tag, Object... log) {
        Log.i(tag, StringCompat.strings(log));
    }

    public void d(String tag, String... log) {
        Log.d(tag, StringCompat.strings(log));
    }

    public void dd(String tag, Object... log) {
        Log.d(tag, StringCompat.strings(log));
    }

}
