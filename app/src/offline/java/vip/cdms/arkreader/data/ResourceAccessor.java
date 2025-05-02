package vip.cdms.arkreader.data;

import android.content.Context;
import lombok.Getter;
import vip.cdms.arkreader.resource.AppScore;

public class ResourceAccessor implements IResourceAccessor {
    public static final ResourceAccessor INSTANCE = new ResourceAccessor();

    public static void initialize(Context context) {}

    @Getter
    private final AppScore score = null;
}
