package com.siato.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.siato.app.POJO.Spareparts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahUbahSparepartsFragment extends Fragment {
    private String ACTION = "TAMBAH";
    private TextInputEditText etKode;
    private TextInputEditText etNama;
    private TextInputEditText etMerk;
    private TextInputEditText etTipe;
    private TextInputEditText etKodePeletakan;
    private TextInputEditText etHargaJual;
    private TextInputEditText etHargaBeli;
    private TextInputEditText etStok;
    private TextInputEditText etStokMinimal;
    private Button btnTambahUbah;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah_ubah_spareparts, container, false);

        etKode = view.findViewById(R.id.etSparepartsKode);
        etNama = view.findViewById(R.id.etSparepartsNama);
        etMerk = view.findViewById(R.id.etSparepartsMerk);
        etTipe = view.findViewById(R.id.etSparepartsTipe);
        etKodePeletakan = view.findViewById(R.id.etSparepartsKodePeletakan);
        etHargaJual = view.findViewById(R.id.etSparepartsHargaJual);
        etHargaBeli = view.findViewById(R.id.etSparepartsHargaBeli);
        etStok = view.findViewById(R.id.etSparepartsStok);
        etStokMinimal = view.findViewById(R.id.etSparepartsStokMinimal);
        btnTambahUbah = view.findViewById(R.id.btnSparepartsTambahUbah);

        if(getArguments() != null && getArguments().getParcelable("tes") != null) {
            ACTION = "UBAH";
            Spareparts spareparts = getArguments().getParcelable("tes");
            etKode.setText(spareparts.getKode());
            etNama.setText(spareparts.getNama());
            etMerk.setText(spareparts.getMerk());
            etTipe.setText(spareparts.getTipe());
            etKodePeletakan.setText(spareparts.getKodePeletakan());
            etHargaJual.setText(String.valueOf(spareparts.getHargaJual()));
            etHargaBeli.setText(String.valueOf(spareparts.getHargaBeli()));
            etStok.setText(String.valueOf(spareparts.getStok()));
            etStokMinimal.setText(String.valueOf(spareparts.getStokMinimal()));
            btnTambahUbah.setText("Ubah");
        }

        btnTambahUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API service = RetrofitClientInstance.getRetrofitInstance().create(API.class);
                switch (ACTION){
                    case "TAMBAH":
                        Call<APIResponse> create = service.createSpareparts(
                                etKode.getText().toString(),
                                etNama.getText().toString(),
                                etMerk.getText().toString(),
                                etTipe.getText().toString(),
                                etKodePeletakan.getText().toString(),
                                etHargaJual.getText().toString(),
                                etHargaBeli.getText().toString(),
                                etStok.getText().toString(),
                                etStokMinimal.getText().toString(),
                                ((MainActivity)getActivity()).logged_in_user.getApiKey()
                        );
                        create.enqueue(new Callback<APIResponse>() {
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
                        break;

                    case "UBAH":
                        Call<APIResponse> update = service.updateSpareparts(
                                etKode.getText().toString(),
                                etNama.getText().toString(),
                                etMerk.getText().toString(),
                                etTipe.getText().toString(),
                                etKodePeletakan.getText().toString(),
                                etHargaJual.getText().toString(),
                                etHargaBeli.getText().toString(),
                                etStokMinimal.getText().toString(),
                                ((MainActivity)getActivity()).logged_in_user.getApiKey()
                        );
                        update.enqueue(new Callback<APIResponse>() {
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
                        break;
                }
            }
        });

        return view;
    }
}
