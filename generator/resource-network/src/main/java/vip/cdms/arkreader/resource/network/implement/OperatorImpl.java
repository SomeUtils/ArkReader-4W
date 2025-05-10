package vip.cdms.arkreader.resource.network.implement;

import lombok.Builder;
import lombok.Getter;
import vip.cdms.arkreader.resource.*;
import vip.cdms.arkreader.resource.network.Network;
import vip.cdms.arkreader.resource.network.ResourceRoots;

@Builder
@Getter
public class OperatorImpl implements Operator {
    public String charId;

    private String name;
    private String appellation;
    public String displayNumber;
    public OperatorProfession profession;
    public byte rarity;
    public OperatorArchive[] archives;
    public OperatorModule[] modules;
    public OperatorRecord[] records;

    @Override
    public byte[] getAvatarImage() {
        var url = "char_1001_amiya2".equals(charId) || "char_1037_amiya3".equals(charId)
                ? charId + "_2"
                : charId;
        return Network.fetchRaw(ResourceRoots.FEXLI_RESOURCE + "/avatar/ASSISTANT/" + url + ".png");
    }
}
