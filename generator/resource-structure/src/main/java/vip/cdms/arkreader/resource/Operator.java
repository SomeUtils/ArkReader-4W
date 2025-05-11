package vip.cdms.arkreader.resource;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface Operator {
    String getName();

    byte[] getAvatarImage();

    String getAppellation();

    String getDisplayNumber();

    OperatorProfession getProfession();

    @IntRange(from = 1, to = 6)
    byte getRarity();

    @NonNull
    OperatorArchive[] getArchives();

    @Nullable
    OperatorModule[] getModules();

    @Nullable
    OperatorRecord[] getRecords();
}
