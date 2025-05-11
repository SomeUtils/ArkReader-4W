package vip.cdms.arkreader.data.implement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import vip.cdms.arkreader.data.OfflineResourceHelper;
import vip.cdms.arkreader.resource.*;

@RequiredArgsConstructor
public abstract class OperatorImpl implements Operator {
    private final String avatarImageAssets;

    @Getter
    private final String name;
    @Getter
    private final String appellation;
    @Getter
    private final String displayNumber;
    @Getter
    private final OperatorProfession profession;
    @Getter
    private final byte rarity;

    @Override
    public byte[] getAvatarImage() {
        return OfflineResourceHelper.readAssets(avatarImageAssets);
    }
}
