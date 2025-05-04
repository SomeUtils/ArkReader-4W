package vip.cdms.arkreader.resource.network.implement;

import com.eclipsesource.json.JsonArray;
import lombok.val;
import vip.cdms.arkreader.resource.Event;
import vip.cdms.arkreader.resource.AppScore;
import vip.cdms.arkreader.resource.EventType;
import vip.cdms.arkreader.resource.StoryInfo;
import vip.cdms.arkreader.resource.network.Network;
import vip.cdms.arkreader.resource.network.ResourceRoots;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Pattern;

public class AppScoreImpl implements AppScore {
    public static final AppScoreImpl INSTANCE = new AppScoreImpl();

    // https://github.com/050644zf/ArknightsStoryTextReader/blob/master/reader/src/ASTRv2/menupage/ms.vue#L197
    @SuppressWarnings({"ComparatorCombinators", "Java8ListSort"})
    @Override
    public Event[] getSortedEvents() {
        val events = new ArrayList<EventImpl>();

        val eventBuildersWithId = new HashMap<String, EventImpl.EventImplBuilder>();
        val reviewTable = Network.fetchJson(ResourceRoots.STORY_REVIEW_TABLE).asObject();
        for (val eventId : reviewTable.names()) {
            val event = reviewTable.get(eventId).asObject();
            val entryType = event.get("entryType").asString();
            if (entryType.equals("NONE")) continue;
            val coverImageUrl = ResourceRoots.ASTR_CDN + "/img"
                    + (entryType.equals("MAINLINE") ? "/icons" : "/banners")
                    + "/" + eventId + ".png";
            val type = entryType.equals("MAINLINE") ? EventType.MAIN_THEME :
                    entryType.equals("ACTIVITY") ? EventType.SIDE_STORY :
                    entryType.equals("MINI_ACTIVITY") ? EventType.STORY_SET : null;
            val eventImplBuilder = EventImpl.builder()
                    .eventId(eventId)
                    .name(event.get("name").asString())
                    .coverImageUrl(coverImageUrl)
                    .type(type)
                    .startTime(event.get("startTime").asLong())
                    .stories(getStoryInfos(event.get("infoUnlockDatas").asArray()));
            eventBuildersWithId.put(eventId, eventImplBuilder);
        }

        val storylineStorySets = Network.fetchJson(ResourceRoots.STAGE_TABLE).asObject()
                .get("storylineStorySets").asObject();
        for (val key : storylineStorySets.names()) {
            val storySet = storylineStorySets.get(key).asObject();
            val storySetId = storySet.get("storySetId").asString();
            val year = storySet.get("sortByYear").asInt();
            val eventId = storySetId.equals("setId_mainline_3_1") ? "main_15" :
                    storySet.get("storySetType").asString().equals("MAINLINE") ?
                        storySet.get("mainlineData").asObject().get("zoneId").asString() :
                    storySet.get("relevantActivityId").asString();
            val eventBuilder = eventBuildersWithId.get(eventId);
            eventBuilder.appCategorySort(year * 114 + storySet.getInt("sortWithinYear", 0));
            eventBuilder.appCategory("YEAR-" + year);
            events.add(eventBuilder.build());
        }
        Collections.sort(events, (a, b) ->
                Integer.compare(a.appCategorySort, b.appCategorySort));

        val collaborationEventIds = getCollaborationEventIds();
        if (collaborationEventIds != null) for (val eventId : collaborationEventIds) {
            val eventBuilder = eventBuildersWithId.get(eventId);
            events.add(eventBuilder.appCategory("COLLABORATIONS").build());
        }

        return events.toArray(new EventImpl[0]);
    }

    private static StoryInfo[] getStoryInfos(JsonArray infoUnlockDatas) {
        val infos = new StoryInfo[infoUnlockDatas.size()];
        for (int i = 0; i < infoUnlockDatas.size(); i++) {
            val storyInfo = infoUnlockDatas.get(i).asObject();
            val storyCode = storyInfo.get("storyCode").asString();
            val avgTag = storyInfo.get("avgTag").asString();
            infos[i] = StoryInfoImpl.builder()
                    .storyGroup(storyInfo.get("storyGroup").asString())
                    .storyTxt(storyInfo.get("storyTxt").asString())
                    .name(storyInfo.get("storyName").asString())
                    .stageName(storyCode.isBlank() ? avgTag : (storyCode + " " + avgTag))
                    .build();
        }
        return infos;
    }

    private static final String funcJs = "https://raw.githubusercontent.com" +
            "/050644zf/ArknightsStoryTextReader/refs/heads/master/reader/src/ASTRv2/func.js";
    private static final Pattern collabArrayBlockPattern = Pattern.compile("collab:\\s*\\[([\\s\\S]*?)]");
    private static String[] getCollaborationEventIds() {
        val jsContent = Network.fetchString(funcJs);
        val arrayBlockMatcher = collabArrayBlockPattern.matcher(jsContent);
        if (!arrayBlockMatcher.find()) return null;
        val ids = arrayBlockMatcher.group(1).split(",");
        for (int i = 0; i < ids.length; i++) {
            val id = ids[i].trim();
            ids[i] = id.substring(1, id.length() - 1);
        }
        return ids;
    }
}
