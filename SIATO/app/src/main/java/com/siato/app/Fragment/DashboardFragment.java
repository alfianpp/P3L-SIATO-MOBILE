package com.siato.app.Fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.siato.app.MainActivity;
import com.siato.app.R;

public class DashboardFragment extends Fragment {
    public static final String TAG = DashboardFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).getSupportActionBar()
                .setTitle(R.string.dashboard);
    }
}
