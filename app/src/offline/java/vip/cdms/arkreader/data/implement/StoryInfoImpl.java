package vip.cdms.arkreader.data.implement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import vip.cdms.arkreader.resource.StoryInfo;

@Getter
@RequiredArgsConstructor
public abstract class StoryInfoImpl implements StoryInfo {
    private final String name;
    private final String stageName;
    private final String summary;
    private final int wordcount;
}
