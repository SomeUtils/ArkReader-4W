package vip.cdms.arkreader.resource;

public interface Operator {
    String getName();

    byte[] getAvatarImage();

    String getAppellation();

    String getDisplayNumber();

    OperatorProfession getProfession();

    byte getRarity();

    OperatorArchive[] getArchives();

    OperatorModule[] getModules();

    OperatorRecord[] getRecords();
}
