package arch.record.kit;

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

public class MediaRecorderImpl extends IRecordScreen implements MediaRecorder.OnInfoListener, MediaRecorder.OnErrorListener {
    private boolean statePrepare = false;
    private boolean stateRecording = false;
    private MediaRecorder recorder;
    private MediaProjectionManager mediaProjectionManager;
    private MediaProjection mMediaProjection;
    private VirtualDisplay mVirtualDisplay;
    private final int REQUEST_CODE_RECORD_PERMISSION = 400;

    public MediaRecorderImpl() {
        L.i(TAG, "new MediaRecorderImpl()...");
        recorder = new MediaRecorder();
    }

    @Override
    public boolean hasRecordPermission() {
        if (mMediaProjection != null) {
            return true;
        }
        return false;
    }
    private int scDpi;
    private int scWidth;
    private int scHeight;
    private String mFile;
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
            mMediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data);
        }
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
        recorder.setVideoSize(scWidth/2, scHeight/2);
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

        if(mVirtualDisplay==null){
            mVirtualDisplay = mMediaProjection.createVirtualDisplay("MediaRecorder",
                    scWidth/2, scHeight/2, scDpi,
                    DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                    recorder.getSurface(),
                    null, null);
        }else{
            mVirtualDisplay.setSurface(recorder.getSurface());
        }
    }

    @Override
    public String getRecordFile() {
        return mFile;
    }

    @Override
    public void startRecord() {
        L.i(TAG, "startRecord");
        if (!stateRecording) {
            recorder.start();
            stateRecording = true;
        }
    }

    @Override
    public void stopRecord() {
        L.i(TAG, "stopRecord->", stateRecording);
        if (stateRecording) {
            recorder.stop();
            stateRecording = false;
        }
    }

    @Override
    public void reset() {
        statePrepare = false;
        L.i(TAG, "reset");
        recorder.reset();
    }

    @Override
    public boolean isRecording() {
        L.i(TAG, "isRecording=", stateRecording);
        return stateRecording;
    }

    @Override
    public void release() {
        L.i(TAG, "release");
        recorder.release();
        if(mVirtualDisplay!=null){
            mVirtualDisplay.release();
            mVirtualDisplay = null;
        }
        statePrepare = false;
        stateRecording = false;
        mFile = null;
    }

    @Override
    public void onInfo(MediaRecorder mr, int what, int extra) {
        L.i(TAG, "onInfo->what=", what, " extra=", extra);
    }

    @Override
    public void onError(MediaRecorder mr, int what, int extra) {
        L.i(TAG, "onInfo->what=", what, " extra=", extra);
    }
}
