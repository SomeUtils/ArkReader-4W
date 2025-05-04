package vip.cdms.arkreader.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ResourceHelper {
    public static Bitmap decodeImage(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return null;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static void runThread(final Runnable runnable) {
        new Thread(runnable).start();
    }
}
