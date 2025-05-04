package vip.cdms.arkreader.ui.main.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import vip.cdms.arkreader.R;
import vip.cdms.arkreader.data.ResourceAccessor;
import vip.cdms.arkreader.data.ResourceHelper;
import vip.cdms.arkreader.databinding.FragmentScoreBinding;
import vip.cdms.arkreader.resource.Event;
import vip.cdms.arkreader.ui.components.FlexibleTextView;
import vip.cdms.arkreader.ui.components.GlowSelectorBar;
import vip.cdms.arkreader.ui.utils.FadedHorizontalScroll;
import vip.cdms.arkreader.ui.utils.HorizontalSpacingItemDecoration;
import vip.cdms.arkreader.ui.utils.UnitUtils;
import vip.cdms.arkreader.ui.utils.ViewUtils;

public class ScoreFragment extends Fragment {
    private FragmentScoreBinding binding;
    private Context context;

    private Event[] sortedEvents;

    public static String ICON_MAIN_THEME = "\ue901",
            ICON_SIDE_STORY = "\ue903",
            ICON_STORY_SET = "\ue904";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentScoreBinding.inflate(inflater);
        context = binding.getRoot().getContext();
        val colorAppBackground = ContextCompat.getColor(context, R.color.app_background);

        val eventTypeSelector = new GlowSelectorBar(context);
        ViewUtils.setNoClipChildren(eventTypeSelector);
        eventTypeSelector.setBarForeground(ContextCompat.getColor(context, R.color.app_content));
        eventTypeSelector.setBarBackground(colorAppBackground);
        eventTypeSelector.addItem(ICON_MAIN_THEME, "主题曲");
        eventTypeSelector.addItem(ICON_SIDE_STORY, "别传");
        eventTypeSelector.addItem(ICON_STORY_SET, "故事集");
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

        ResourceHelper.runThread(() -> {
            sortedEvents = ResourceAccessor.INSTANCE.getScore().getSortedEvents();
            binding.content.post(() -> {
                binding.content.removeView(binding.loading);
                binding.content.addView(createTitleView("YEAR-1"));
                val eventsViewer = createEventsViewer(sortedEvents);
                binding.content.addView(eventsViewer.first);
            });
        });

        return binding.getRoot();
    }

    private LinearLayout createTitleView(String title) {
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

    @AllArgsConstructor
    private class EventsAdapter extends RecyclerView.Adapter<EventViewHolder> {
        private Event[] events;

        public void updateEvents(Event[] events) {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new EventsDiffCallback(this.events, events));
            this.events = events;
            diffResult.dispatchUpdatesTo(this);
//            this.events = events;
//            notifyDataSetChanged();
        }

        @Override
        public @NotNull EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new EventViewHolder(parent.getContext());
        }

        @Override
        public void onBindViewHolder(@NotNull EventViewHolder holder, int position) {
            val event = events[position];
            holder.iconView.setText(switch (event.getType()) {
                case MAIN_THEME -> ICON_MAIN_THEME;
                case SIDE_STORY -> ICON_SIDE_STORY;
                case STORY_SET -> ICON_STORY_SET;
            });
            ResourceHelper.runThread(() -> {
                val cover = ResourceHelper.decodeImage(event.getCoverImage());
                holder.imageView.post(() -> {
                    if (cover == null) holder.setName(event.getName());
                    else holder.setBitmap(cover);
                });
            });
        }

        @Override
        public int getItemCount() {
            return events.length;
        }
    }

    @RequiredArgsConstructor
    private static class EventsDiffCallback extends DiffUtil.Callback {
        private final Event[] oldList;
        private final Event[] newList;

        @Override
        public int getOldListSize() {
            return oldList.length;
        }

        @Override
        public int getNewListSize() {
            return newList.length;
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList[oldItemPosition].getName().equals(newList[newItemPosition].getName());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return areItemsTheSame(oldItemPosition, newItemPosition);
        }
    }

    private static class EventViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        public ImageView imageView;
        public TextView iconView;
        public TextView nameView;

        public EventViewHolder(Context context) {
            super(new FrameLayout(context));
            this.context = context;
            val frameLayout = (FrameLayout) itemView;
            imageView = new ImageView(context);
            val imageViewSize = context.getResources().getDimensionPixelSize(R.dimen.score_event_cover_size);
            imageView.setLayoutParams(new FrameLayout.LayoutParams(imageViewSize, imageViewSize));
            imageView.setBackgroundColor(0x11ffffff);
            frameLayout.addView(imageView);

            iconView = new TextView(context);
            val iconLayoutParams = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            iconLayoutParams.gravity = Gravity.BOTTOM | Gravity.END;
            iconLayoutParams.setMargins(0, 0, 0, UnitUtils.dp2px(context, 4));
            iconLayoutParams.setMarginEnd(UnitUtils.dp2px(context, 4));
            iconView.setLayoutParams(iconLayoutParams);
            iconView.setTypeface(ResourcesCompat.getFont(context, R.font.ak_terminal));
            iconView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            iconView.setTextColor(ContextCompat.getColor(context, R.color.app_content));
            frameLayout.addView(iconView);
        }

        public void setBitmap(Bitmap bitmap) {
            if (nameView != null) ((FrameLayout) itemView).removeView(nameView);
            imageView.setImageBitmap(bitmap);
        }

        public void setName(String name) {
            if (nameView == null) createName();
            imageView.setImageDrawable(null);
            nameView.setText(name);
        }
        private void createName() {
            if (nameView != null) return;
            nameView = new TextView(context);
            val nameLayoutParams = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            nameLayoutParams.gravity = Gravity.TOP | Gravity.START;
            nameLayoutParams.setMargins(0, UnitUtils.dp2px(context, 4), 0, 0);
            nameLayoutParams.setMarginStart(UnitUtils.dp2px(context, 4));
            nameView.setLayoutParams(nameLayoutParams);
            nameView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            nameView.setTextColor(ContextCompat.getColor(context, R.color.app_content));
            ((FrameLayout) itemView).addView(nameView);
        }
    }

    private Pair<FrameLayout, EventsAdapter> createEventsViewer(Event[] events) {
        val recyclerView = new RecyclerView(context);

        val layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        val paddingHorizontal = getResources().getDimensionPixelSize(R.dimen.main_appbar_padding_horizontal);
        recyclerView.setPadding(paddingHorizontal - 1, 0, paddingHorizontal, 0);
        recyclerView.setClipToPadding(false);
        recyclerView.addItemDecoration(new HorizontalSpacingItemDecoration(UnitUtils.dp2px(context, 8)));

        val adapter = new EventsAdapter(events);
        recyclerView.setAdapter(adapter);

        val frameLayout = FadedHorizontalScroll.attach(
                recyclerView,
                getResources().getDimensionPixelSize(R.dimen.main_appbar_padding_horizontal),
                ContextCompat.getColor(context, R.color.app_background));
        val frameLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        frameLayoutParams.setMargins(0, UnitUtils.dp2px(context, -8), 0, 0);
        frameLayout.setLayoutParams(frameLayoutParams);

        return new Pair<>(frameLayout, adapter);
    }
}
