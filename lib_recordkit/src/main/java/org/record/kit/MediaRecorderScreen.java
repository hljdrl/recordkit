package org.record.kit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.util.DisplayMetrics;

import java.io.IOException;

public class MediaRecorderScreen extends MediaStudio implements MediaRecorder.OnInfoListener, MediaRecorder.OnErrorListener {
    private final String Version = "1.0.0";
    private int scDpi;
    private int scWidth;
    private int scHeight;
    private String mFile;
    private boolean statePrepare = false;
    private boolean stateRecording = false;
    private MediaRecorder recorder;
    private MediaProjectionManager mediaProjectionManager;
    private MediaProjection mMediaProjection;
    private VirtualDisplay mVirtualDisplay;
    private final int REQUEST_CODE_RECORD_PERMISSION = 400;
    private Context mContext;
    public MediaRecorderScreen() {
        L.i(TAG, "new MediaRecorderScreen() Version=", Version);
        recorder = new MediaRecorder();
    }

    @Override
    public boolean hasRecordPermission() {
        if (mMediaProjection != null) {
            return true;
        }
        return false;
    }
    @Override
    public void startRecordPermission(Activity activity) {
        Context context = activity.getApplicationContext();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        scDpi = dm.densityDpi;
        scWidth = dm.widthPixels;
        scHeight = dm.heightPixels;
        mediaProjectionManager = (MediaProjectionManager) context.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        Intent intent = mediaProjectionManager.createScreenCaptureIntent();
        activity.startActivityForResult(intent, REQUEST_CODE_RECORD_PERMISSION);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_RECORD_PERMISSION) {
//            mMediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data);
            if(!isRecording()){
                ScreenRecorder.startScreenService(mContext,resultCode,data);
            }
        }
    }
    @Override
    void setupMediaProject(MediaProjection mediaProjection) {
        this.mMediaProjection = mediaProjection;
        if (!isPrepare()) {
            prepare(getRecordFile());
        }
        recorder.start();
        stateRecording = true;
    }

    @Override
    public boolean isPrepare() {
        return statePrepare;
    }

    @Override
    public void prepare(String saveFile) {
        L.i(TAG, "prepare->", saveFile);
        mFile = saveFile;
        //*******设置顺序-非常重要*******
        //*******设置顺序-非常重要*******
        if(recorder==null){
            recorder = new MediaRecorder();
        }
        //音频载体
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //视频载体
        recorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        //======================================================================
        //输出格式
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setOutputFile(saveFile);
        //===========================================================
        //size
        recorder.setVideoSize(scWidth / 2, scHeight / 2);
        //帧率
//        recorder.setVideoFrameRate(60);
        recorder.setVideoFrameRate(30);
        //比特率
        recorder.setVideoEncodingBitRate(5 * 1024 * 1024);
        //旋转角度
        recorder.setOrientationHint(0);

//        recorder.setCaptureRate(30);
//        recorder.setProfile();
        //音频格式
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        //视频格式
        recorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        recorder.setOnInfoListener(this);
        recorder.setOnErrorListener(this);
        try {
            recorder.prepare();
            statePrepare = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mVirtualDisplay == null) {
            mVirtualDisplay = mMediaProjection.createVirtualDisplay("MediaRecorder",
                    scWidth/2 , scHeight/2, scDpi,
                    DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                    recorder.getSurface(),
                    new VirtualDisplayCallback(), null);
        } else {
            mVirtualDisplay.setSurface(recorder.getSurface());
        }
    }

    @Override
    public String getRecordFile() {
        return mFile;
    }

    @Override
    public void startRecord(Activity activity, String saveFile) {
        L.i(TAG, "startRecord");
        if(mContext==null){
            mContext = activity.getApplicationContext();
        }
        if (!stateRecording) {
            mFile = saveFile;
            if (!hasRecordPermission()) {
                startRecordPermission(activity);
                return;
            }
            if (!isPrepare()) {
                prepare(saveFile);
            }
            recorder.start();
            stateRecording = true;
        }
    }

    @Override
    public void stopRecord() {
        L.ii(TAG, "stopRecord->", stateRecording);
        if (stateRecording) {
            recorder.stop();
            stateRecording = false;
        }
        if(mVirtualDisplay!=null){
            mVirtualDisplay.release();
            mVirtualDisplay = null;
        }
        if(mMediaProjection!=null){
            mMediaProjection.stop();
            mMediaProjection = null;
        }
        mContext.stopService(new Intent(mContext,ScreenRecorder.class));
    }

    @Override
    public void reset() {
        L.i(TAG, "reset");
        statePrepare = false;
        recorder.reset();
    }

    @Override
    public boolean isRecording() {
        L.ii(TAG, "isRecording=", stateRecording);
        return stateRecording;
    }

    @Override
    public void release() {
        L.i(TAG, "release");
        recorder.release();
        if (mVirtualDisplay != null) {
            mVirtualDisplay.release();
            mVirtualDisplay = null;
        }
        statePrepare = false;
        stateRecording = false;
        mFile = null;
        recorder = null;
        mMediaProjection=null;
    }

    @Override
    public void onInfo(MediaRecorder mr, int what, int extra) {
        L.ii(TAG, "onInfo->what=", what, " extra=", extra);
    }

    @Override
    public void onError(MediaRecorder mr, int what, int extra) {
        L.ii(TAG, "onInfo->what=", what, " extra=", extra);
    }
}
