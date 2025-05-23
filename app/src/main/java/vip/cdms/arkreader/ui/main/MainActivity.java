package vip.cdms.arkreader.ui.main;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import lombok.SneakyThrows;
import lombok.val;
import vip.cdms.arkreader.R;
import vip.cdms.arkreader.databinding.ActivityMainBinding;
import vip.cdms.arkreader.ui.main.fragments.*;
import vip.cdms.arkreader.ui.utils.TimeTextHelper;
import vip.cdms.arkreader.ui.utils.UnitUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private TimeTextHelper timeTextHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        binding.getRoot().postDelayed(() -> ViewUtils.disableAllClipping(binding.getRoot()), 1000);

        binding.appbarDrawerButton.setOnClickListener(v ->
                binding.drawerLayout.openDrawer(GravityCompat.START));

        timeTextHelper = new TimeTextHelper(binding.appbarTime);

        val layoutParams = binding.drawerContentLayout.getLayoutParams();
        layoutParams.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.8);
        binding.drawerContentLayout.setLayoutParams(layoutParams);

        binding.drawerCloseButton.setOnClickListener(v ->
                binding.drawerLayout.closeDrawer(GravityCompat.START));

        binding.drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                binding.drawerCloseButton.setX(drawerView.getWidth() * (1 - slideOffset));
            }
        });

        binding.drawerContentList.getViewTreeObserver().addOnScrollChangedListener(() -> binding.drawerContentDivider.setVisibility(
                ((ScrollView) binding.drawerContentList.getParent()).getScrollY() == 0 ? View.GONE : View.VISIBLE));

        addFragment("当前曲谱", ScoreFragment.class);
        addFragment("作战干员", OperatorFragment.class);
        addFragment("本地书签", BookmarkFragment.class);
        addFragment("统计信息", StatisticsFragment.class);
        addFragment("关于应用", AboutFragment.class);
    }

    private final LinkedHashMap<Class<? extends Fragment>, Fragment> fragments = new LinkedHashMap<>();
    private Fragment fragment = null;

    @SneakyThrows
    private Fragment getOrNewFragment(final Class<? extends Fragment> clazz) {
        var fragment = fragments.get(clazz);
        if (fragment == null) fragments.put(clazz, fragment = clazz.getDeclaredConstructor().newInstance());
        return fragment;
    }

    private void addFragment(final String title, final Class<? extends Fragment> clazz) {
        val isFirstFragment = fragments.isEmpty();

        val themedContext = binding.drawerContentList.getContext();
        val themeTypedValue = new TypedValue();
        themedContext.getTheme().resolveAttribute(android.R.attr.colorControlHighlight, themeTypedValue, true);
        val rippleColor = themeTypedValue.data;

        val textView = new TextView(themedContext);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                getResources().getDimensionPixelSize(R.dimen.appbar)
        ));
        val paddingHorizontal = UnitUtils.dp2px(this, 11.5f);
        textView.setPadding(paddingHorizontal, 0, paddingHorizontal, 0);
        if (isFirstFragment) textView.setBackgroundColor(rippleColor);
        else textView.setBackgroundResource(R.drawable.ripple_square);
        textView.setClickable(true);
        textView.setFocusable(true);
        textView.setText(title);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setOnClickListener(v -> {
            if (fragment.getClass().equals(clazz)) return;

            val indexBefore = new ArrayList<>(fragments.values()).indexOf(fragment);
            binding.drawerContentList.getChildAt(indexBefore).setBackgroundResource(R.drawable.ripple_square);
            v.postDelayed(() -> v.setBackgroundColor(rippleColor), 250);
            ((ScrollView) binding.drawerContentList.getParent()).smoothScrollTo(0, v.getTop());

            val transaction = getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.fragment_in, R.anim.fragment_out);
            transaction.hide(fragment);
            fragment = fragments.get(clazz);
            if (fragment == null) {
                fragments.put(clazz, fragment = getOrNewFragment(clazz));
                transaction.add(R.id.fragment_container, fragment);
            } else transaction.show(fragment);
            transaction.commit();

            binding.drawerLayout.closeDrawer(GravityCompat.START);
        });
        binding.drawerContentList.addView(textView);

        if (isFirstFragment) {
            fragments.put(clazz, fragment = getOrNewFragment(clazz));
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .show(fragment)
                    .commit();
        } else fragments.put(clazz, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        timeTextHelper.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        timeTextHelper.cancel();
    }
}
