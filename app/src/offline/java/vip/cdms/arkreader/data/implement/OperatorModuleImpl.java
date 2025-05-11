package vip.cdms.arkreader.data.implement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import vip.cdms.arkreader.data.OfflineResourceHelper;
import vip.cdms.arkreader.resource.OperatorModule;

@RequiredArgsConstructor
public class OperatorModuleImpl implements OperatorModule {
    @Getter
    private final String name;
    @Getter
    private final String description;
    @Getter
    private final String tagText;
    @Getter
    private final String tagColor;

    private final String equipIconAssets;

    @Override
    public byte[] getEquipIcon() {
        return OfflineResourceHelper.readAssets(equipIconAssets);
    }
}
