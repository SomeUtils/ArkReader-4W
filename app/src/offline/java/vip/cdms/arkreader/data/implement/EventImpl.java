package vip.cdms.arkreader.data.implement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import vip.cdms.arkreader.data.OfflineResourceHelper;
import vip.cdms.arkreader.resource.Event;
import vip.cdms.arkreader.resource.EventType;

@RequiredArgsConstructor
public abstract class EventImpl implements Event {
    private final String coverImageAssets;

    @Getter
    private final String name;
    @Getter
    private final EventType type;
    @Getter
    private final String appCategory;
    @Getter
    private final long startTime;
    @Getter
    private final int wordcount;

    @Override
    public byte[] getCoverImage() {
        return OfflineResourceHelper.readAssets(coverImageAssets);
    }
}
