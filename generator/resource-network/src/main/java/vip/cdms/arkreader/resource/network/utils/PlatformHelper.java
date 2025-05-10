package vip.cdms.arkreader.resource.network.utils;

public class PlatformHelper {
    private static Boolean isAndroid = null;

    public static boolean isAndroid() {
        if (isAndroid != null) return isAndroid;
        if ("true".equals(System.getenv("CI"))) return isAndroid = false;
        try {
            Class.forName("android.os.Build");
            return isAndroid = true;
        } catch (ClassNotFoundException e) {
            return isAndroid = false;
        }
    }
}
