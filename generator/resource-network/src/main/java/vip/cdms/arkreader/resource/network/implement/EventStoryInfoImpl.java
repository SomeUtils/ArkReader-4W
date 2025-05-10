package vip.cdms.arkreader.resource.network.implement;

import lombok.Builder;
import lombok.Getter;
import vip.cdms.arkreader.resource.EventStoryInfo;
import vip.cdms.arkreader.resource.network.Network;
import vip.cdms.arkreader.resource.network.ResourceRoots;
import vip.cdms.arkreader.resource.story.StoryContent;

@Builder
@Getter
public class EventStoryInfoImpl implements EventStoryInfo {
    private String storyGroup;
    private String storyTxt;

    private String name;
    private String avgName;

    @Override
    public String getSummary() {
        return Network.fetchJson(ResourceRoots.STORY_INFO).asObject()
                .get(storyTxt).asString();
    }

    @Override
    public int getWordcount() {
        return Network.fetchJson(ResourceRoots.WORDCOUNT).asObject()
                .get(storyGroup).asObject()
                .get(storyTxt).asInt();
    }

    @Override
    public StoryContent getContent() {
        return null;
    }
}
