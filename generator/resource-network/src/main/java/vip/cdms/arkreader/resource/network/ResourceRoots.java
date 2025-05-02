package vip.cdms.arkreader.resource.network;

public class ResourceRoots {
    public static final String FULL_RESOURCE =
            "https://raw.githubusercontent.com/fexli/ArknightsResource/main";
    public static final String ASTR_CDN =
            "https://r2.m31ns.top";  // thanks 050644zf/ArknightsStoryTextReader !
    public static final String GAME_DATA_ONLY =
            ASTR_CDN + "/zh_CN";

    public static final String STORY_INFO =
            GAME_DATA_ONLY + "/storyinfo.json";
    public static final String CHARACTER_DICT =
            GAME_DATA_ONLY + "/chardict.json";
    public static final String WORDCOUNT =
            GAME_DATA_ONLY + "/wordcount.json";

    public static final String STORY_REVIEW_TABLE =
            resolve("/gamedata/excel/story_review_table.json");
    public static final String STAGE_TABLE =
            resolve("/gamedata/excel/stage_table.json");

    public static String resolve(String path) {
        if (path.startsWith("/gamedata")) return GAME_DATA_ONLY + path;
        return FULL_RESOURCE + path;
    }
}
