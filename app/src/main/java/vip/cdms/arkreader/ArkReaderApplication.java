package vip.cdms.arkreader;

import android.app.Application;
import vip.cdms.arkreader.data.ResourceAccessor;

public class ArkReaderApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ResourceAccessor.initialize(this);
    }
}
