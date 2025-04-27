package vip.cdms.arkreader.ui.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import vip.cdms.arkreader.databinding.FragmentOperatorBinding;

public class OperatorFragment extends Fragment {
    private FragmentOperatorBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOperatorBinding.inflate(inflater);
        return binding.getRoot();
    }
}
