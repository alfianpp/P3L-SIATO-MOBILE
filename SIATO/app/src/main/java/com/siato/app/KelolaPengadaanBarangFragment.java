package com.siato.app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.siato.app.POJO.PengadaanBarang;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KelolaPengadaanBarangFragment extends Fragment {
    private PengadaanBarangListAdapter adapter = null;
    private RecyclerView recyclerView;
    private Button btnTambahUbahPengadaanBarang;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_kelola_pengadaan_barang, container, false);

        btnTambahUbahPengadaanBarang = view.findViewById(R.id.btnTambahUbahPengadaanBarang);

        API service =  RetrofitClientInstance.getRetrofitInstance().create(API.class);
        Call<APIResponse<List<PengadaanBarang>>> call = service.getAllPengadaanBarang(((MainActivity)getActivity()).logged_in_user.getApiKey());
        call.enqueue(new Callback<APIResponse<List<PengadaanBarang>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<PengadaanBarang>>> call, Response<APIResponse<List<PengadaanBarang>>> response) {
                List<PengadaanBarang> result = response.body().getData();
                generateDataList(result, view);
            }

            @Override
            public void onFailure(Call<APIResponse<List<PengadaanBarang>>> call, Throwable t) {
                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btnTambahUbahPengadaanBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFragment(3);
            }
        });

        return view;
    }

    private void generateDataList(List<PengadaanBarang> pengadaanBarangList, View view) {
        recyclerView = view.findViewById(R.id.recyclerview_spareparts);
        adapter = new PengadaanBarangListAdapter(getContext(), pengadaanBarangList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
