package vip.cdms.arkreader.resource;

import vip.cdms.arkreader.resource.story.StoryContent;

public interface StoryInfo extends WordCountable {
    String getName();

    String getStageName();

    String getSummary();

    StoryContent getContent();
}
