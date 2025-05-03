package vip.cdms.arkreader.data.implement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import vip.cdms.arkreader.data.OfflineResourceHelper;
import vip.cdms.arkreader.resource.Event;
import vip.cdms.arkreader.resource.EventType;

@RequiredArgsConstructor
public abstract class EventImpl implements Event {
    final private String coverImageAssets;

    @Getter
    final private String name;
    @Getter
    final private EventType type;
    @Getter
    final private String appCategory;
    @Getter
    final private long startTime;
    @Getter
    final private int wordcount;

    @Override
    public byte[] getCoverImage() {
        return OfflineResourceHelper.readAssets(coverImageAssets);
    }
}
