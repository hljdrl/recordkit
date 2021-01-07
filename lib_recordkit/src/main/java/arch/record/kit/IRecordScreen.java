package arch.record.kit;

import android.app.Activity;
import android.content.Intent;

public abstract class IRecordScreen {

    private OnRecordListener onRecordListener;
    public final String TAG = "IRecord";
    private static IRecordScreen INSTANCE = null;

    public static final IRecordScreen getInstance() {
        return INSTANCE;
    }

    public static final void install(Class<? extends IRecordScreen> cls) {
        if (INSTANCE == null) {
            try {
                INSTANCE = cls.newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    public IRecordScreen() {
    }
    public abstract boolean hasRecordPermission();

    public abstract void startRecordPermission(Activity activity);

    public abstract void onActivityResult(int requestCode, int resultCode, Intent data);

    public abstract boolean isPrepare();

    public abstract void prepare(String saveFile);
    public abstract String getRecordFile();
    public abstract void startRecord();

    public abstract void stopRecord();

    public abstract void reset();

    public abstract boolean isRecording();

    public abstract void release();

    protected final OnRecordListener getOnRecordListener() {
        return onRecordListener;
    }

    public final void setOnRecordListener(OnRecordListener onRecordListener) {
        this.onRecordListener = onRecordListener;
    }
}
