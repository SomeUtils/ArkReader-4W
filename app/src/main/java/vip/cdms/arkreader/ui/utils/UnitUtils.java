package vip.cdms.arkreader.ui.utils;

import android.content.Context;
import lombok.val;

public class UnitUtils {
    public static int dp2px(Context context, float dpValue) {
        val scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
