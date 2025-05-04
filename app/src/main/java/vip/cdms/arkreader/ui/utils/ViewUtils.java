package vip.cdms.arkreader.ui.utils;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import lombok.val;

public class ViewUtils {
    public static void setNoClipChildren(ViewGroup viewGroup) {
        viewGroup.setClipChildren(false);
        viewGroup.setClipToPadding(false);
    }

    @SuppressLint("ClickableViewAccessibility")
    public static void showTooltip(View anchor, String text) {
        val context = anchor.getContext();
        val tooltipView = new TextView(context);
        tooltipView.setText(text);
        tooltipView.setPadding(16, 8, 16, 8);
        tooltipView.setBackgroundColor(Color.WHITE);
        tooltipView.setTextColor(Color.BLACK);
        tooltipView.setAlpha(0f);

        val container = new FrameLayout(context);
        container.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        container.setBackgroundColor(Color.TRANSPARENT);

        container.addView(tooltipView);
        container.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        val tooltipWidth = tooltipView.getMeasuredWidth();
        val tooltipHeight = tooltipView.getMeasuredHeight();
        container.removeView(tooltipView);

        val location = new int[2];
        anchor.getLocationOnScreen(location);
        val anchorX = location[0];
        val anchorY = location[1] + anchor.getHeight();

        val dm = context.getResources().getDisplayMetrics();
        val screenWidth = dm.widthPixels;
        val screenHeight = dm.heightPixels;

        val finalX = Math.min(anchorX, screenWidth - tooltipWidth);
        val finalY = Math.min(anchorY, screenHeight - tooltipHeight);

        val tooltipParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tooltipParams.leftMargin = finalX;
        tooltipParams.topMargin = finalY;
        container.addView(tooltipView, tooltipParams);

        val popup = new PopupWindow(container,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                true);
        popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popup.setFocusable(true);
        popup.setOutsideTouchable(false);
        popup.showAtLocation(anchor, Gravity.NO_GRAVITY, 0, 0);

        tooltipView.setTranslationY(-tooltipHeight);
        tooltipView.animate()
                .alpha(1f)
                .translationY(0)
                .setDuration(100)
                .start();

        container.setOnTouchListener((v, event) -> {
            val rect = new Rect();
            tooltipView.getGlobalVisibleRect(rect);
            if (rect.contains((int) event.getRawX(), (int) event.getRawY())) return false;
            tooltipView.animate()
                    .alpha(0f)
                    .translationY(-tooltipHeight)
                    .setDuration(100)
                    .withEndAction(popup::dismiss)
                    .start();
            return true;
        });
    }
}
