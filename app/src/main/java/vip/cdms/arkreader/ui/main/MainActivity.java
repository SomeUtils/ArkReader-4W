package vip.cdms.arkreader.ui.main;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import vip.cdms.arkreader.R;
import vip.cdms.arkreader.databinding.ActivityMainBinding;
import vip.cdms.arkreader.ui.utils.TimeTextHelper;
import vip.cdms.arkreader.ui.utils.UnitUtils;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private TimeTextHelper timeTextHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.appbarDrawerButton.setOnClickListener(v ->
                binding.drawerLayout.openDrawer(GravityCompat.START));

        timeTextHelper = new TimeTextHelper(binding.appbarTime);

        ViewGroup.LayoutParams layoutParams = binding.drawerContentLayout.getLayoutParams();
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

        addFragment("当前曲谱");
        addFragment("作战干员");
        addFragment("本地书签");
        addFragment("统计信息");
        addFragment("关于应用");
    }

    private void addFragment(String title) {
        TextView textView = new TextView(binding.drawerContentList.getContext());
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                getResources().getDimensionPixelSize(R.dimen.appbar)
        ));
        int paddingHorizontal = UnitUtils.dp2px(this, 11.5f);
        textView.setPadding(paddingHorizontal, 0, paddingHorizontal, 0);
        textView.setBackgroundResource(R.drawable.ripple_square);
        textView.setClickable(true);
        textView.setFocusable(true);
        textView.setText(title);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        binding.drawerContentList.addView(textView);
        // TODO
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
