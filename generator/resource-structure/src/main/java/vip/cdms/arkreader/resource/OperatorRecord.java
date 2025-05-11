package vip.cdms.arkreader.resource;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import vip.cdms.arkreader.resource.story.StoryContent;

@SuppressWarnings("ClassCanBeRecord")
@RequiredArgsConstructor
@Getter
public class OperatorRecord {
    private final String storySetName;
    private final Avg[] avgList;

    public interface Avg {
        String getIntro();

        StoryContent getContent();
    }
}
