package arch.record.kit;

import android.app.Activity;
import android.content.Intent;
import android.media.projection.MediaProjection;

public class MediaMuxerRecordScreen extends IRecordScreen {

    @Override
    protected boolean hasRecordPermission() {
        return false;
    }

    @Override
    protected void startRecordPermission(Activity activity) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected boolean isPrepare() {
        return false;
    }

    @Override
    void setupMediaProject(MediaProjection mediaProjection) {

    }

    @Override
    protected void prepare(String saveFile) {

    }

    @Override
    public String getRecordFile() {
        return null;
    }

    @Override
    public void startRecord(Activity activity, String saveFile) {

    }

    @Override
    public void stopRecord() {

    }

    @Override
    public void reset() {

    }

    @Override
    public boolean isRecording() {
        return false;
    }

    @Override
    public void release() {

    }
}
