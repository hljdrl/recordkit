package arch.record.kit;

import android.hardware.display.VirtualDisplay;

public class VirtualDisplayCallback extends VirtualDisplay.Callback {

    @Override
    public void onPaused() {
        super.onPaused();
        L.i(IRecordScreen.TAG,"VirtualDisplayCallback->onPaused");
    }

    @Override
    public void onResumed() {
        super.onResumed();
        L.i(IRecordScreen.TAG,"VirtualDisplayCallback->onResumed");
    }

    @Override
    public void onStopped() {
        super.onStopped();
        L.i(IRecordScreen.TAG,"VirtualDisplayCallback->onStopped");
    }
}
