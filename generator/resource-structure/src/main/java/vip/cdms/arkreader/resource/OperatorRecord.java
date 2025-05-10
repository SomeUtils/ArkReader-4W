package vip.cdms.arkreader.resource;

import vip.cdms.arkreader.resource.story.StoryContent;

public record OperatorRecord(
        String storySetName,
        Avg[] avgList
) {
    public interface Avg {
        String intro();

        StoryContent getContent();
    }
}
