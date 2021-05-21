package org.record.kit;


public interface LogKit {

    void i(String tag, String... log);

    void ii(String tag, Object... log);

    void d(String tag, String... log);

    void dd(String tag, Object... log);
}
