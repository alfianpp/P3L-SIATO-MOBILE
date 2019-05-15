package com.siato.mysiato.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.siato.mysiato.R;

public class informasiFragment extends Fragment {
    public static final String TAG = informasiFragment.class.getSimpleName();
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_informasi, container, false);

        return view;
    }
}
