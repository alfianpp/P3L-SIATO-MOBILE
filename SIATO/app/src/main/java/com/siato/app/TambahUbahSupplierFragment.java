package com.siato.app;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TambahUbahSupplierFragment extends Fragment {
    private String ACTION = "TAMBAH";
    private Integer idSupplier = null;
    private TextInputEditText etNama;
    private TextInputEditText etAlamat;
    private TextInputEditText etNamaSales;
    private TextInputEditText etNomorTelepon;
    private Button btnTambahUbah;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah_ubah_supplier, container, false);

        etNama = view.findViewById(R.id.etSupplierNama);
        etAlamat = view.findViewById(R.id.etSupplierAlamat);
        etNamaSales = view.findViewById(R.id.etSupplierNamaSales);
        etNomorTelepon = view.findViewById(R.id.etSupplierNomorTelepon);
        btnTambahUbah = view.findViewById(R.id.btnSupplierTambahUbah);

        if(getArguments() != null && getArguments().getParcelable("tes") != null) {
            ACTION = "UBAH";
            Supplier supplier = getArguments().getParcelable("tes");
            idSupplier = supplier.getId();
            etNama.setText(supplier.getNama());
            etAlamat.setText(supplier.getAlamat());
            etNamaSales.setText(supplier.getNama_sales());
            etNomorTelepon.setText(supplier.getNomor_telepon_sales());
            btnTambahUbah.setText("Ubah");
        }

        btnTambahUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API service = RetrofitClientInstance.getRetrofitInstance().create(API.class);
                switch (ACTION) {
                    case "TAMBAH":
                        Call<APIResponse> create = service.createSupplier(
                                etNama.getText().toString(),
                                etAlamat.getText().toString(),
                                etNamaSales.getText().toString(),
                                etNomorTelepon.getText().toString(),
                                ((MainActivity)getActivity()).logged_in_user.getApiKey()
                        );
                        create.enqueue(new Callback<APIResponse>() {
                            @Override
                            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                                APIResponse<Pegawai> apiResponse = response.body();

                                Toast.makeText(getContext(),apiResponse.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<APIResponse> call, Throwable t) {
                                Toast.makeText(getActivity(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;

                    case "UBAH":
                        Call<APIResponse> update = service.updateSupplier(
                                idSupplier,
                                etNama.getText().toString(),
                                etAlamat.getText().toString(),
                                etNamaSales.getText().toString(),
                                etNomorTelepon.getText().toString(),
                                ((MainActivity)getActivity()).logged_in_user.getApiKey()
                        );
                        update.enqueue(new Callback<APIResponse>() {
                            @Override
                            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                                APIResponse<Pegawai> apiResponse = response.body();

                                Toast.makeText(getContext(),apiResponse.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<APIResponse> call, Throwable t) {
                                Toast.makeText(getActivity(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                }
            }
        });

        return view;
    }
}
