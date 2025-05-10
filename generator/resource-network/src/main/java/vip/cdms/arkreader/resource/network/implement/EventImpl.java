package vip.cdms.arkreader.resource.network.implement;

import lombok.Builder;
import lombok.Getter;
import lombok.val;
import vip.cdms.arkreader.resource.Event;
import vip.cdms.arkreader.resource.EventType;
import vip.cdms.arkreader.resource.EventStoryInfo;
import vip.cdms.arkreader.resource.network.Network;
import vip.cdms.arkreader.resource.network.ResourceRoots;

@Builder
@Getter
public class EventImpl implements Event {
    private String eventId;
    public int appCategorySort;

    private String name;
    private String coverImageUrl;
    private EventType type;
    private String appCategory;
    private long startTime;
    private EventStoryInfo[] stories;

    @Override
    public byte[] getCoverImage() {
        return Network.fetchRawOrNull(coverImageUrl);
    }

    @Override
    public int getWordcount() {
        val stories = Network.fetchJson(ResourceRoots.WORDCOUNT).asObject()
                .get(eventId).asObject();
        var sum = 0;
        for (val name : stories.names()) sum += stories.get(name).asInt();
        return sum;
    }
}
