package vip.cdms.arkreader.data;

import android.content.Context;
import android.content.res.AssetManager;
import lombok.Getter;
import vip.cdms.arkreader.generated.AppScoreImpl;
import vip.cdms.arkreader.resource.AppOperator;
import vip.cdms.arkreader.resource.AppScore;

public class ResourceAccessor implements IResourceAccessor {
    public static final ResourceAccessor INSTANCE = new ResourceAccessor();

    public static AssetManager assetManager;

    public static void initialize(Context context) {
        assetManager = context.getAssets();
    }

    @Getter
    private final AppScore score = AppScoreImpl.INSTANCE;
    @Getter
    private final AppOperator operator = null;
}
