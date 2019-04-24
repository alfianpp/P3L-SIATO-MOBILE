package com.siato.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TambahUbahKendaraanFragment extends Fragment {
    private TextInputEditText etNoPolisi;
    private TextInputEditText etMerk;
    private TextInputEditText etTipe;
    private TextInputEditText etPemilik;
    private Button btnTambahUbah;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah_ubah_kendaraan, container, false);

        etNoPolisi = view.findViewById(R.id.etKendaraanNoPolisi);
        etMerk = view.findViewById(R.id.etSKendaraanMerk);
        etTipe = view.findViewById(R.id.etKendaraanTipe);
        etPemilik = view.findViewById(R.id.etKendaraanPemilik);
        btnTambahUbah = view.findViewById(R.id.btnSparepartsTambahUbah);

        btnTambahUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API service = RetrofitClientInstance.getRetrofitInstance().create(API.class);
                Call<APIResponse> call = service.createKendaraan(
                        etNoPolisi.getText().toString(),
                        etMerk.getText().toString(),
                        etTipe.getText().toString(),
                        etPemilik.getText().toString(),
                        ((MainActivity)getActivity()).logged_in_user.getApiKey()
                );
                call.enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        APIResponse<Pegawai> apiResponse = response.body();

                        Toast.makeText(getContext(), apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }
}