package vip.cdms.arkreader.ui.main.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import lombok.val;
import vip.cdms.arkreader.R;
import vip.cdms.arkreader.databinding.FragmentScoreBinding;
import vip.cdms.arkreader.ui.components.FlexibleTextView;
import vip.cdms.arkreader.ui.components.GlowSelectorBar;
import vip.cdms.arkreader.ui.utils.FadedHorizontalScroll;
import vip.cdms.arkreader.ui.utils.UnitUtils;
import vip.cdms.arkreader.ui.utils.ViewUtils;

public class ScoreFragment extends Fragment {
    private FragmentScoreBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentScoreBinding.inflate(inflater);
        val context = binding.getRoot().getContext();
        val colorAppContent = ContextCompat.getColor(context, R.color.app_content);
        val colorAppBackground = ContextCompat.getColor(context, R.color.app_background);

        val eventTypeSelector = new GlowSelectorBar(context);
        ViewUtils.setNoClipChildren(eventTypeSelector);
        eventTypeSelector.setBarForeground(colorAppContent);
        eventTypeSelector.setBarBackground(colorAppBackground);
        eventTypeSelector.addItem("\ue901", "主题曲");
        eventTypeSelector.addItem("\ue903", "别传");
        eventTypeSelector.addItem("\ue904", "故事集");
        eventTypeSelector.toggleItem(0);
        eventTypeSelector.setOnToggleListener((index, before) -> {
            if (eventTypeSelector.getSelectedIndexes().size() < 2) return true;
            return !before;
        });
        val eventTypeSelectorWrapped = FadedHorizontalScroll.attach(
                eventTypeSelector,
                getResources().getDimensionPixelSize(R.dimen.main_appbar_padding_horizontal),
                colorAppBackground,
                UnitUtils.dp2px(context, 36),
                UnitUtils.dp2px(context, -5)
        );
        ViewUtils.setNoClipChildren(eventTypeSelectorWrapped);
        binding.content.addView(eventTypeSelectorWrapped, 0);

        binding.content.addView(createTitleView("YEAR-1"), 1);
//        binding.content.addView(createTitleView("YEAR-2"), 1);

        return binding.getRoot();
    }

    private LinearLayout createTitleView(String title) {
        val context = getContext();
        assert context != null;
        val linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.END);
        val paddingHorizontal = getResources().getDimensionPixelSize(R.dimen.main_appbar_padding_horizontal);
        linearLayout.setPadding(paddingHorizontal, 0, paddingHorizontal, 0);

        val view = new View(context);
        view.setLayoutParams(new LinearLayout.LayoutParams(
                UnitUtils.dp2px(context, 4),
                UnitUtils.dp2px(context, 4)));
        view.setTranslationY(UnitUtils.dp2px(context, 2));
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.accent_red));
        view.setAlpha(.2f);

        //noinspection ExtractMethodRecommender
        val flexibleTextView = new FlexibleTextView(context);
        flexibleTextView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        flexibleTextView.setGravity(Gravity.END);
        flexibleTextView.setText(title);
        flexibleTextView.setTextColor(Color.WHITE);
        flexibleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        flexibleTextView.setLetterSpacing(.3f);
        flexibleTextView.setAlpha(.2f);
        flexibleTextView.setFontWeight(5);

        linearLayout.addView(view);
        linearLayout.addView(flexibleTextView);
        return linearLayout;
    }

//    private RecyclerView createStorySetScrollView() {}
}
