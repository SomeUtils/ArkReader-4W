package vip.cdms.arkreader.ui.main.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import lombok.var;
import vip.cdms.arkreader.BuildConfig;
import vip.cdms.arkreader.databinding.FragmentAboutBinding;

public class AboutFragment extends Fragment {
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        var binding = FragmentAboutBinding.inflate(inflater);

        binding.versionApp.setText(BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ")");

        binding.versionCore.setText(BuildConfig.CORE_VERSION_NAME + " (" + BuildConfig.CORE_VERSION_CODE + ")");

        return binding.getRoot();
    }
}
