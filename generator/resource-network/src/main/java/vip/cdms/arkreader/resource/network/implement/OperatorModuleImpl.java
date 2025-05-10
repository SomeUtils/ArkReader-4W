package vip.cdms.arkreader.resource.network.implement;

import lombok.Builder;
import lombok.Getter;
import vip.cdms.arkreader.resource.OperatorModule;
import vip.cdms.arkreader.resource.network.Network;
import vip.cdms.arkreader.resource.network.ResourceRoots;

@Builder
@Getter
public class OperatorModuleImpl implements OperatorModule {
    private String name;
    private String description;
    private String tagText;
    private String tagColor;

    private String uniEquipId;

    @Override
    public byte[] getEquipIcon() {
        if (uniEquipId == null) return null;
        return Network.fetchRaw(ResourceRoots.FEXLI_RESOURCE + "/equip/" + uniEquipId + ".png");
    }
}
