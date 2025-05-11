package vip.cdms.arkreader.resource.network.implement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import vip.cdms.arkreader.resource.OperatorRecord;
import vip.cdms.arkreader.resource.story.StoryContent;

@SuppressWarnings("ClassCanBeRecord")
@RequiredArgsConstructor
@Getter
public class OperatorRecordAvgImpl implements OperatorRecord.Avg {
    private final String intro;
    private final String storyTxt;

    @Override
    public StoryContent getContent() {
        return null;  // TODO
    }
}
