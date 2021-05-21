package org.record.kit;

import android.hardware.display.VirtualDisplay;

public class VirtualDisplayCallback extends VirtualDisplay.Callback {

    @Override
    public void onPaused() {
        super.onPaused();
        L.i(MediaStudio.TAG,"VirtualDisplayCallback->onPaused");
    }

    @Override
    public void onResumed() {
        super.onResumed();
        L.i(MediaStudio.TAG,"VirtualDisplayCallback->onResumed");
    }

    @Override
    public void onStopped() {
        super.onStopped();
        L.i(MediaStudio.TAG,"VirtualDisplayCallback->onStopped");
    }
}
