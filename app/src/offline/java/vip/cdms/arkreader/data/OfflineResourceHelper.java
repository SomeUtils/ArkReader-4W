package vip.cdms.arkreader.data;

import lombok.SneakyThrows;
import lombok.val;
import vip.cdms.arkreader.resource.utils.IOUtils;

public class OfflineResourceHelper {
    @SneakyThrows
    public static byte[] readAssets(String name) {
        if (name == null) return null;
        val stream = ResourceAccessor.assetManager.open(name);
        return IOUtils.readAll(stream);
    }
}
