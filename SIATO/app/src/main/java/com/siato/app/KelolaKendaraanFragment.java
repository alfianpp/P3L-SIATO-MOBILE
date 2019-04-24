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

public class KelolaKendaraanFragment extends Fragment{
    private KendaraanListAdapter adapter = null;
    private RecyclerView recyclerView;
    private Button btnTambahUbahKendaraan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_kelola_kendaraan, container, false);

        btnTambahUbahKendaraan = view.findViewById(R.id.btnTambahUbahKendaraan);

        API service =  RetrofitClientInstance.getRetrofitInstance().create(API.class);
        Call<APIResponse<List<Kendaraan>>> call = service.getAllKendaraan(((MainActivity)getActivity()).logged_in_user.getApiKey());
        call.enqueue(new Callback<APIResponse<List<Kendaraan>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<Kendaraan>>> call, Response<APIResponse<List<Kendaraan>>> response) {
                List<Kendaraan> result = response.body().getData();
                generateDataList(result, view);
            }

            @Override
            public void onFailure(Call<APIResponse<List<Kendaraan>>> call, Throwable t) {
                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btnTambahUbahKendaraan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFragment(3);
            }
        });

        return view;
    }

    private void generateDataList(List<Kendaraan> kendaraanList, View view) {
        recyclerView = view.findViewById(R.id.recyclerview_kendaraan);
        adapter = new KendaraanListAdapter(getContext(), kendaraanList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
