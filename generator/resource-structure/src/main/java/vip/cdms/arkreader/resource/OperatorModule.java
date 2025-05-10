package vip.cdms.arkreader.resource;

public interface OperatorModule {
    String getName();

    String getDescription();

    String getTagText();

    /** @return blue | green | grey | purple | red | yellow */
    String getTagColor();

    byte[] getEquipIcon();
}
