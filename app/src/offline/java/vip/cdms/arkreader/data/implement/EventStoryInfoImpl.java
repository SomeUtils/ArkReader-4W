package vip.cdms.arkreader.data.implement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import vip.cdms.arkreader.resource.EventStoryInfo;

@RequiredArgsConstructor
@Getter
public abstract class EventStoryInfoImpl implements EventStoryInfo {
    private final String name;
    private final String avgName;
    private final String summary;
    private final int wordcount;
}
