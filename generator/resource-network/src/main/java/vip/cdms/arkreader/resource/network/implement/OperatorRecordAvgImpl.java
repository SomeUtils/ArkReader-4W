package vip.cdms.arkreader.resource.network.implement;

import vip.cdms.arkreader.resource.OperatorRecord;
import vip.cdms.arkreader.resource.story.StoryContent;

public record OperatorRecordAvgImpl(
        String intro,
        String storyTxt
) implements OperatorRecord.Avg {
    @Override
    public StoryContent getContent() {
        return null;  // TODO
    }
}
