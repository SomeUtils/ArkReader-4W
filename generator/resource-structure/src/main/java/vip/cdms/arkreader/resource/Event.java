package vip.cdms.arkreader.resource;

public interface Event extends WordCountable {
    String getName();

    byte[] getCoverImage();

    EventType getType();

    String getAppCategory();

    long getStartTime();

    StoryInfo[] getStories();
}
