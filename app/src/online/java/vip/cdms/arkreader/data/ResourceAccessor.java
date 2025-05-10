package vip.cdms.arkreader.data;

import android.content.Context;
import lombok.Getter;
import vip.cdms.arkreader.resource.AppOperator;
import vip.cdms.arkreader.resource.AppScore;
import vip.cdms.arkreader.resource.network.Network;
import vip.cdms.arkreader.resource.network.NetworkCache;
import vip.cdms.arkreader.resource.network.implement.AppOperatorImpl;
import vip.cdms.arkreader.resource.network.implement.AppScoreImpl;

@Getter
public class ResourceAccessor implements IResourceAccessor {
    public static final ResourceAccessor INSTANCE = new ResourceAccessor();

    public static void initialize(Context context) {
        Network.cache = new NetworkCache(context.getCacheDir());
    }

    private final AppScore score = AppScoreImpl.INSTANCE;
    private final AppOperator operator = AppOperatorImpl.INSTANCE;
}
