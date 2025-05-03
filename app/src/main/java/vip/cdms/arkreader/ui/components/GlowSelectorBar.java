package vip.cdms.arkreader.ui.components;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.core.content.res.ResourcesCompat;
import com.google.android.material.card.MaterialCardView;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import vip.cdms.arkreader.R;
import vip.cdms.arkreader.ui.utils.UnitUtils;
import vip.cdms.arkreader.ui.utils.ViewUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class GlowSelectorBar extends HorizontalScrollView {
    private LinearLayout container;

    private final ArrayList<String> textIcons = new ArrayList<>();
    private final ArrayList<Drawable> drawableIcons = new ArrayList<>();
    private final ArrayList<String> labels = new ArrayList<>();
    @Getter
    private final HashMap<Integer, Boolean> selectedIndexesMap = new HashMap<>();

    public ArrayList<Integer> getSelectedIndexes() {
        val selectedIndexes = new ArrayList<Integer>();
        selectedIndexesMap.forEach((index, selected) -> {
            if (selected) selectedIndexes.add(index);
        });
        return selectedIndexes;
    }

    @Setter
    @ColorInt
    private int barForeground;
    @Setter
    @ColorInt
    private int barBackground;

    public interface OnToggleListener {
        boolean onToggle(int index, boolean before);
    }

    @Setter
    private OnToggleListener onToggleListener = (_index, before) -> !before;

    public GlowSelectorBar(Context context) {
        super(context);
        init(context);
    }

    public GlowSelectorBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GlowSelectorBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        container = new LinearLayout(context);
        container.setLayoutParams(new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        container.setOrientation(LinearLayout.HORIZONTAL);
        ViewUtils.setNoClipChildren(container);
        addView(container);
    }

    public void addItem(String icon, String label) {
        textIcons.add(icon);
        drawableIcons.add(null);
        labels.add(label);
    }

    public void addItem(Drawable icon, String label) {
        textIcons.add(null);
        drawableIcons.add(icon);
        labels.add(label);
    }

    public void toggleItem(int index) {
        val context = getContext();
        val before = !Boolean.FALSE.equals(
                selectedIndexesMap.getOrDefault(index, false));
        selectedIndexesMap.put(index, onToggleListener.onToggle(index, before));
        container.removeAllViews();
        for (int i = 0; i < labels.size(); i++) {
            val iFixed = i;
            val selected = Boolean.TRUE.equals(
                    selectedIndexesMap.getOrDefault(i, false));
            val textIcon = textIcons.get(i);
            val drawableIcon = drawableIcons.get(i);
            val label = labels.get(i);

            View iconView;
            if (textIcon != null) iconView = createTextIconView(textIcon, selected);
            else iconView = createDrawableIconView(drawableIcon, selected);
            val labelView = createLabelView(label, selected);

            View itemView;
            val isPreviousSelected = i - 1 >= 0 && Boolean.TRUE.equals(
                    selectedIndexesMap.getOrDefault(i - 1, false));
            val marginStart = i == 0 ? 0 : (isPreviousSelected ? 0 : UnitUtils.dp2px(context, 8));
            if (selected) {
                val marginEnd = i == labels.size() - 1 ? UnitUtils.dp2px(context, -8) : 0;
                itemView = createSelectedItemView(iconView, labelView, marginStart, marginEnd);
            } else {
                itemView = createUnselectedItemView(iconView, labelView, marginStart);
            }

            itemView.setOnClickListener(v -> toggleItem(iFixed));
            container.addView(itemView);
        }
    }

    private ShadowRectLayout createSelectedItemView(
            View iconView, View labelView,
            int marginStart, int marginEnd) {
        val context = getContext();
        val shadowRectLayout = new ShadowRectLayout(context);
        val layoutParams = new LinearLayout.MarginLayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                UnitUtils.dp2px(context, 32.5f)
        );
        layoutParams.setMarginStart(marginStart);
        layoutParams.setMarginEnd(marginEnd);
        shadowRectLayout.setLayoutParams(layoutParams);
        val paddingHorizontal = UnitUtils.dp2px(context, 8);
        shadowRectLayout.setPadding(paddingHorizontal, 0, paddingHorizontal, 0);
        shadowRectLayout.setMRectColor(barForeground);
        shadowRectLayout.setMShadowColor(barForeground);
        shadowRectLayout.setMShadowRadius(UnitUtils.dp2px(context, 4));
        shadowRectLayout.updatePaint();
        shadowRectLayout.updatePosition();
        shadowRectLayout.invalidate();
        shadowRectLayout.addView(iconView);
        shadowRectLayout.addView(labelView);
        return shadowRectLayout;
    }

    private MaterialCardView createUnselectedItemView(
            View iconView, View labelView,
            int marginStart) {
        val context = getContext();
        val materialCardView = new MaterialCardView(context);
        val layoutParams = new LinearLayout.MarginLayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                UnitUtils.dp2px(context, 24)
        );
        layoutParams.setMarginStart(marginStart);
        materialCardView.setLayoutParams(layoutParams);
        materialCardView.setCardElevation(0);
        materialCardView.setRadius(0);
        materialCardView.setCardBackgroundColor(Color.TRANSPARENT);
        materialCardView.setStrokeWidth(UnitUtils.dp2px(context, 1));
        materialCardView.setStrokeColor(barForeground);

        val linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        val paddingHorizontal = UnitUtils.dp2px(context, 8);
        linearLayout.setPadding(paddingHorizontal, 0, paddingHorizontal, 0);

        linearLayout.addView(iconView);
        linearLayout.addView(labelView);
        materialCardView.addView(linearLayout);
        return materialCardView;
    }

    private TextView createTextIconView(String icon, boolean selected) {
        val context = getContext();
        val textView = new TextView(context);
        textView.setLayoutParams(new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTypeface(ResourcesCompat.getFont(context, R.font.ak_terminal));
        textView.setText(icon);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setTextColor(selected ? barBackground : barForeground);
        if (selected) textView.setAlpha(.9f);
        return textView;
    }

    private ImageView createDrawableIconView(Drawable icon, boolean selected) {
        val context = getContext();
        val imageView = new ImageView(context);
        imageView.setLayoutParams(new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        imageView.setImageDrawable(icon);
        imageView.setImageTintList(ColorStateList.valueOf(selected ? barBackground : barForeground));
        if (selected) imageView.setAlpha(.9f);
        return imageView;
    }

    private TextView createLabelView(String label, boolean selected) {
        val context = getContext();
        val textView = new TextView(context);
        val layoutParams = new LinearLayout.MarginLayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        layoutParams.setMarginStart(UnitUtils.dp2px(context, 2));
        layoutParams.topMargin = UnitUtils.dp2px(context, -2);
        textView.setLayoutParams(layoutParams);
        textView.setGravity(Gravity.CENTER);
        textView.setText(label);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        textView.setTextColor(selected ? barBackground : barForeground);
        if (selected) textView.setAlpha(.9f);
        return textView;
    }
}
