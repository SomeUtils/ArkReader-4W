package vip.cdms.arkreader.resource;

import vip.cdms.arkreader.resource.story.StoryContent;

public interface EventStoryInfo extends WordCountable {
    String getName();

    String getAvgName();

    String getSummary();

    StoryContent getContent();
}
