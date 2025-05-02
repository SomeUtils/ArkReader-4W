package vip.cdms.arkreader.data;

import android.content.Context;
import lombok.Getter;
import vip.cdms.arkreader.resource.AppScore;
import vip.cdms.arkreader.resource.network.Network;
import vip.cdms.arkreader.resource.network.NetworkCache;
import vip.cdms.arkreader.resource.network.implement.AppScoreImpl;

public class ResourceAccessor implements IResourceAccessor {
    public static final ResourceAccessor INSTANCE = new ResourceAccessor();

    public static void initialize(Context context) {
        Network.cache = new NetworkCache(context.getCacheDir());
    }

    @Getter
    private final AppScore score = AppScoreImpl.INSTANCE;
}
