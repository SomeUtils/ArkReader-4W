package vip.cdms.arkreader.resource.network.implement;

import com.eclipsesource.json.JsonArray;
import lombok.val;
import vip.cdms.arkreader.resource.*;
import vip.cdms.arkreader.resource.network.Network;
import vip.cdms.arkreader.resource.network.ResourceRoots;

import java.util.ArrayList;
import java.util.Collections;

public class AppOperatorImpl implements AppOperator {
    public static final AppOperatorImpl INSTANCE = new AppOperatorImpl();

    @SuppressWarnings({"ComparatorCombinators", "Java8ListSort"})
    @Override
    public Operator[] getSortedOperators() {
        val operators = new ArrayList<OperatorImpl>();

        val charInfo = Network.fetchJson(ResourceRoots.CHARACTER_INFO).asObject();
        for (val charId : charInfo.names()) {
            if (charId.startsWith("npc_")) continue;
            val character = charInfo.get(charId).asObject();
            val profession = switch (character.get("profession").asString()) {
                case "PIONEER" -> OperatorProfession.VANGUARD;
                case "CASTER" -> OperatorProfession.CASTER;
                case "WARRIOR" -> OperatorProfession.GUARD;
                case "MEDIC" -> OperatorProfession.MEDIC;
                case "TANK" -> OperatorProfession.DEFENDER;
                case "SUPPORT" -> OperatorProfession.SUPPORTER;
                case "SNIPER" -> OperatorProfession.SNIPER;
                case "SPECIAL" -> OperatorProfession.SPECIALIST;
                default -> throw new IllegalArgumentException("Unknown profession: "
                        + character.get("profession").asString());
            };
            val displayNumber = character.get("displayNumber");
            operators.add(OperatorImpl.builder()
                    .charId(charId)
                    .name(character.get("name").asString())
                    .appellation(character.get("appellation").asString())
                    .displayNumber(displayNumber.isNull() ? null : displayNumber.asString())
                    .profession(profession)
                    .rarity(Byte.parseByte(character.get("rarity").asString().substring(5)))
                    .archives(parseArchives(character.get("storyTextAudio").asArray()))
                    .modules(parseModules(character.get("equips").asArray()))
                    .records(parseRecords(character.get("handbookAvgList").asArray()))
                    .build());
        }

        Collections.sort(operators, (a, b) -> a.charId.compareTo(b.charId));
        return operators.toArray(new Operator[0]);
    }

    private static OperatorArchive[] parseArchives(JsonArray storyTextAudio) {
        val archives = new OperatorArchive[storyTextAudio.size()];
        for (int i = 0; i < archives.length; i++) {
            val story = storyTextAudio.get(i).asObject();
            val title = story.get("storyTitle").asString();
            val stories = story.get("stories").asArray();
            val storyStrings = new String[stories.size()];
            for (int j = 0; j < storyStrings.length; j++)
                storyStrings[j] = stories.get(j).asObject().get("storyText").asString();
            archives[i] = new OperatorArchive(title, storyStrings);
        }
        return archives;
    }

    private static OperatorModule[] parseModules(JsonArray equips) {
        val modules = new OperatorModule[equips.size()];
        for (int i = 0; i < modules.length; i++) {
            val equip = equips.get(i).asObject();
            val isAdvanced = "ADVANCED".equals(equip.get("type").asString());
            var tagText = equip.get("typeName1").asString();
            if (isAdvanced) tagText += " - " + equip.get("typeName2").asString();
            modules[i] = OperatorModuleImpl.builder()
                    .name(equip.get("uniEquipName").asString())
                    .description(equip.get("uniEquipDesc").asString())
                    .tagText(tagText)
                    .tagColor(isAdvanced ? equip.get("equipShiningColor").asString() : null)
                    .uniEquipId(isAdvanced ? equip.get("uniEquipId").asString() : null)
                    .build();
        }
        return modules;
    }

    private static OperatorRecord[] parseRecords(JsonArray handbookAvgList) {
        val records = new OperatorRecord[handbookAvgList.size()];
        for (int i = 0; i < records.length; i++) {
            val record = handbookAvgList.get(i).asObject();
            val storySetName = record.get("storySetName").asString();
            val avgList = record.get("avgList").asArray();
            val avgListImpl = new OperatorRecord.Avg[avgList.size()];
            for (int j = 0; j < avgListImpl.length; j++) {
                val avg = avgList.get(j).asObject();
                avgListImpl[j] = new OperatorRecordAvgImpl(
                        avg.get("storyIntro").asString(),
                        avg.get("storyTxt").asString()
                );
            }
            records[i] = new OperatorRecord(storySetName, avgListImpl);
        }
        return records;
    }

    @Override
    public byte[] getProfessionIcon(OperatorProfession profession) {
        //noinspection SpellCheckingInspection
        return Network.fetchRaw(ResourceRoots.github("https://raw.githubusercontent.com" +
                "/Aceship/Arknight-Images/main/classes/class_" + switch (profession) {
            case VANGUARD -> "vanguard";
            case CASTER -> "caster";
            case GUARD -> "guard";
            case MEDIC -> "medic";
            case DEFENDER -> "defender";
            case SUPPORTER -> "supporter";
            case SNIPER -> "sniper";
            case SPECIALIST -> "specialist";
        } + ".png"));
    }
}
