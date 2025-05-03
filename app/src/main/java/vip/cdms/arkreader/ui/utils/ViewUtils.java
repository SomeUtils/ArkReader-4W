package vip.cdms.arkreader.ui.utils;

import android.view.ViewGroup;

public class ViewUtils {
    public static void setNoClipChildren(ViewGroup viewGroup) {
        viewGroup.setClipChildren(false);
        viewGroup.setClipToPadding(false);
    }
}
