package org.record.kit;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class ScreenRecorder extends Service {

    public static void startScreenService(Context context, int resultCode, Intent data) {
        Intent service = new Intent(context, ScreenRecorder.class);
        service.putExtra("code", resultCode);
        service.putExtra("data", data);
        context.startForegroundService(service);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private MediaStudio recordScreen;
    private MediaProjection mMediaProjection;
    private MediaProjectionManager mediaProjectionManager;
    private String CHANNEL_ID="222";
    private String CHANNEL_NAME="ScreenRecorder";
    private String TAG="ScreenRecorder";
    @Override
    public void onCreate() {
        super.onCreate();
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
        Notification notification = new Notification.Builder(getApplicationContext(),CHANNEL_ID).build();
        startForeground(111, notification);
        L.i(TAG, "onCreate");
        mediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        L.i(TAG, "onTaskRemoved");
        super.onTaskRemoved(rootIntent);
        if(recordScreen!=null){
            if(recordScreen.isRecording()){
                recordScreen.stopRecord();
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (recordScreen == null) {
            recordScreen = MediaStudio.getInstance();
        }
        if (mMediaProjection == null) {
            int mResultCode = intent.getIntExtra("code", -1);
            Intent mResultData = intent.getParcelableExtra("data");
            mMediaProjection = mediaProjectionManager.getMediaProjection(mResultCode,
                    mResultData);
            recordScreen.setupMediaProject(mMediaProjection);

        }
        L.i(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.i(TAG, "onDestroy");
    }

}
