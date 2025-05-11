package vip.cdms.arkreader.data.implement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import vip.cdms.arkreader.resource.OperatorRecord;

@RequiredArgsConstructor
@Getter
public abstract class OperatorRecordAvgImpl implements OperatorRecord.Avg {
    private final String intro;
}
