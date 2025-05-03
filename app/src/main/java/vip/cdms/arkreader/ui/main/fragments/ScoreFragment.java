package vip.cdms.arkreader.ui.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import lombok.val;
import vip.cdms.arkreader.R;
import vip.cdms.arkreader.databinding.FragmentScoreBinding;
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

        return binding.getRoot();
    }
}
