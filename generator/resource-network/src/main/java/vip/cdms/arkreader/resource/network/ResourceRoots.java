package vip.cdms.arkreader.resource.network;

import vip.cdms.arkreader.resource.network.utils.PlatformHelper;

@SuppressWarnings("SpellCheckingInspection")
public class ResourceRoots {
    public static final String FEXLI_RESOURCE =
            "https://raw.githubusercontent.com/fexli/ArknightsResource/main";
    public static final String GIT_MIRROR =
            "https://hub.gitmirror.com";    // 7ed.net 先生大义 !!!
    public static final String ASTR_CDN =
            "https://r2.m31ns.top";  // thanks 050644zf/ArknightsStoryTextReader !

    private static final String ASTR_CDN_ZH_CN =
            ASTR_CDN + "/zh_CN";
    public static final String STORY_INFO =
            ASTR_CDN_ZH_CN + "/storyinfo.json";
    public static final String WORDCOUNT =
            ASTR_CDN_ZH_CN + "/wordcount.json";
    public static final String CHARACTER_INFO =
            ASTR_CDN_ZH_CN + "/charinfo.json";

    public static final String STORY_REVIEW_TABLE =
            gamedata("/excel/story_review_table.json");
    public static final String STAGE_TABLE =
            gamedata("/excel/stage_table.json");

    public static String gamedata(String path) {
        path = "/gamedata" + path;
        if (PlatformHelper.isAndroid()) return ASTR_CDN_ZH_CN + path;
        return github(FEXLI_RESOURCE + path);
    }

    public static String github(String url) {
        // 构建时就不要浪费 TA 的流量了!
        if (!PlatformHelper.isAndroid()) return url;
        return GIT_MIRROR + "/" + url;
    }
}
