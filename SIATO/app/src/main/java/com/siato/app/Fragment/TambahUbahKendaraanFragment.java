package com.siato.app.Fragment;

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
import com.siato.app.API;
import com.siato.app.APIResponse;
import com.siato.app.MainActivity;
import com.siato.app.POJO.Kendaraan;
import com.siato.app.POJO.Konsumen;
import com.siato.app.R;
import com.siato.app.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TambahUbahKendaraanFragment extends Fragment {
    public static final String TAG = TambahUbahKendaraanFragment.class.getSimpleName();
    private String ACTION = "TAMBAH";
    private TextInputEditText etNomorPolisi;
    private TextInputEditText etMerk;
    private TextInputEditText etTipe;
    private Spinner spPemilik;
    private Button btnTambahUbah;
    private List<Konsumen> listPemilik;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah_ubah_kendaraan, container, false);

        etNomorPolisi = view.findViewById(R.id.etKendaraanNomorPolisi);
        etMerk = view.findViewById(R.id.etKendaraanMerk);
        etTipe = view.findViewById(R.id.etKendaraanTipe);
        spPemilik = view.findViewById(R.id.spKendaraanPemilik);
        btnTambahUbah = view.findViewById(R.id.btnTambahUbahKendaraan);

        if(getArguments() != null && getArguments().getParcelable("kendaraan") != null) {
            ACTION = "UBAH";
            Kendaraan kendaraan = getArguments().getParcelable("kendaraan");
            etNomorPolisi.setText(kendaraan.getNomorPolisi());
            etMerk.setText(kendaraan.getMerk());
            etTipe.setText(kendaraan.getTipe());
            btnTambahUbah.setText("Ubah");
        }

        setSpinerPemilik();

        btnTambahUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
                switch (ACTION) {
                    case "TAMBAH":
                        Call<APIResponse> create = APIService.createKendaraan(
                                etNomorPolisi.getText().toString(),
                                etMerk.getText().toString(),
                                etTipe.getText().toString(),
                                String.valueOf(listPemilik.get(spPemilik.getSelectedItemPosition()).getID()),
                                ((MainActivity)getActivity()).logged_in_user.getApiKey()
                        );
                        create.enqueue(new Callback<APIResponse>() {
                            @Override
                            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                                APIResponse apiResponse = response.body();

                                if(!apiResponse.getError()) {
                                    ((MainActivity)getActivity()).changeFragment(R.id.nav_data_kendaraan);
                                }

                                Toast.makeText(getContext(), apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<APIResponse> call, Throwable t) {
                                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;

                    case "UBAH":
                        Call<APIResponse> update = APIService.updateKendaraan(
                                etNomorPolisi.getText().toString(),
                                etMerk.getText().toString(),
                                etTipe.getText().toString(),
                                String.valueOf(listPemilik.get(spPemilik.getSelectedItemPosition()).getID()),
                                ((MainActivity)getActivity()).logged_in_user.getApiKey()
                        );
                        update.enqueue(new Callback<APIResponse>() {
                            @Override
                            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                                APIResponse apiResponse = response.body();

                                if(!apiResponse.getError()) {
                                    ((MainActivity)getActivity()).changeFragment(R.id.nav_data_kendaraan);
                                }

                                Toast.makeText(getContext(), apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<APIResponse> call, Throwable t) {
                                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                }
            }
        });

        return view;
    }

    private void setSpinerPemilik() {
        API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
        Call<APIResponse<List<Konsumen>>> call = APIService.getAllKonsumen(((MainActivity) getActivity()).logged_in_user.getApiKey());
        call.enqueue(new Callback<APIResponse<List<Konsumen>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<Konsumen>>> call, Response<APIResponse<List<Konsumen>>> response) {
                APIResponse<List<Konsumen>> apiResponse = response.body();

                if(!apiResponse.getError()) {
                    List<String> listPemilik = new ArrayList<>();

                    for(int i=0; i<apiResponse.getData().size(); i++) {
                        listPemilik.add(apiResponse.getData().get(i).getNama());
                    }

                    ArrayAdapter<String> listPemilikAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listPemilik);
                    listPemilikAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spPemilik.setAdapter(listPemilikAdapter);
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<Konsumen>>> call, Throwable t) {
                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).getSupportActionBar()
                .setTitle(ACTION.substring(0, 1) + ACTION.substring(1).toLowerCase() + " " + getResources().getString(R.string.data_kendaraan));
    }
}