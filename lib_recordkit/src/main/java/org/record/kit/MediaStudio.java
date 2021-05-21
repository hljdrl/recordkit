package org.record.kit;

import android.app.Activity;
import android.content.Intent;
import android.media.projection.MediaProjection;

public abstract class MediaStudio {

    private OnRecordListener onRecordListener;
    public static final String TAG = "IRecord";
    private static MediaStudio INSTANCE = null;

    public static final MediaStudio getInstance() {
        return INSTANCE;
    }

    public static final void install(Class<? extends MediaStudio> cls) {
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

    public MediaStudio() {
    }
    public void handlePause(){
    }
    public void handleResume(){

    }
    public void handleStart(){

    }
    public  void handleStop(){

    }
    public  void handleDestroy(){

    }

    protected abstract boolean hasRecordPermission();

    protected abstract void startRecordPermission(Activity activity);

    public abstract void onActivityResult(int requestCode, int resultCode, Intent data);

    protected abstract boolean isPrepare();

    abstract void setupMediaProject(MediaProjection mediaProjection);

    protected abstract void prepare(String saveFile);

    public abstract String getRecordFile();

    public abstract void startRecord(Activity activity, String saveFile);

    public abstract void stopRecord();

    public abstract void reset();

    public abstract boolean isRecording();

    public abstract void release();

    public final void setOnRecordListener(OnRecordListener onRecordListener) {
        this.onRecordListener = onRecordListener;
    }
}
