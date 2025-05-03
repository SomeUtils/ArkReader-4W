package vip.cdms.arkreader.ui.utils;

import android.content.res.ColorStateList;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;
import lombok.val;
import vip.cdms.arkreader.R;

public class FadedHorizontalScroll {
    public static FrameLayout attach(
            HorizontalScrollView scrollView,
            int fadeWidth,
            @ColorInt int fadeTint
    ) {
        return attach(scrollView, fadeWidth, fadeTint, -1, -1);
    }

    public static FrameLayout attach(
            HorizontalScrollView scrollView,
            int fadeWidth,
            @ColorInt int fadeTint,
            int fadeHeight,
            float fadeTranslationY
    ) {
        val context = scrollView.getContext();
        val frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        val mask = ContextCompat.getDrawable(context, R.drawable.gradient_transparent_mask);
        val tintList = ColorStateList.valueOf(fadeTint);

        val fadeLeft = new View(context);
//        val leftParams = new LayoutParams(fadeWidth, fadeLeftHeight);  // actually MATCH_PARENT == -1
        val leftParams = new FrameLayout.LayoutParams(fadeWidth,
                fadeHeight != -1 ? fadeHeight : FrameLayout.LayoutParams.MATCH_PARENT);
        leftParams.gravity = Gravity.START;
        fadeLeft.setLayoutParams(leftParams);
        if (fadeTranslationY != -1) fadeLeft.setTranslationY(fadeTranslationY);
        fadeLeft.setBackground(mask);
        fadeLeft.setBackgroundTintList(tintList);

        val fadeRight = new View(context);
        val rightParams = new FrameLayout.LayoutParams(fadeWidth,
                fadeHeight != -1 ? fadeHeight : FrameLayout.LayoutParams.MATCH_PARENT);
        rightParams.gravity = Gravity.END;
        fadeRight.setLayoutParams(rightParams);
        fadeRight.setScaleX(-1);
        if (fadeTranslationY != -1) fadeRight.setTranslationY(fadeTranslationY);
        fadeRight.setBackground(mask);
        fadeRight.setBackgroundTintList(tintList);

        val scrollViewLinearLayout = scrollView.getChildAt(0);
        if (scrollViewLinearLayout instanceof LinearLayout) scrollViewLinearLayout.setPadding(
                scrollViewLinearLayout.getPaddingLeft() + fadeWidth,
                scrollViewLinearLayout.getPaddingTop(),
                scrollViewLinearLayout.getPaddingRight() + fadeWidth,
                scrollViewLinearLayout.getPaddingBottom()
        );

        frameLayout.addView(scrollView);
        frameLayout.addView(fadeLeft);
        frameLayout.addView(fadeRight);
        return frameLayout;
    }
}
