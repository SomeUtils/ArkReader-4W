package vip.cdms.arkreader.resource;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@SuppressWarnings("ClassCanBeRecord")
@RequiredArgsConstructor
@Getter
public class OperatorArchive {
    private final String title;
    private final String[] stories;
}
