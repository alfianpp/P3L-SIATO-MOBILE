package com.siato.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaSparepartsFragment extends Fragment {
    private SparepartsListAdapter adapter = null;
    private RecyclerView recyclerView;
    private Button btnTambahUbahSpareparts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_kelola_spareparts, container, false);

        btnTambahUbahSpareparts = view.findViewById(R.id.btnTambahUbahSpareparts);

        API service =  RetrofitClientInstance.getRetrofitInstance().create(API.class);
        Call<APIResponse<List<Spareparts>>> call = service.getAllSpareparts(((MainActivity)getActivity()).logged_in_user.getApiKey());
        call.enqueue(new Callback<APIResponse<List<Spareparts>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<Spareparts>>> call, Response<APIResponse<List<Spareparts>>> response) {
                List<Spareparts> result = response.body().getData();
                generateDataList(result, view);
            }

            @Override
            public void onFailure(Call<APIResponse<List<Spareparts>>> call, Throwable t) {
                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btnTambahUbahSpareparts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFragment(1);
            }
        });

        return view;
    }

    private void generateDataList(List<Spareparts> sparepartsList, View view) {
        recyclerView = view.findViewById(R.id.recyclerview_spareparts);
        adapter = new SparepartsListAdapter(getContext(), sparepartsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
