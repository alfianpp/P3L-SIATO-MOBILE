package com.siato.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.siato.app.API;
import com.siato.app.APIResponse;
import com.siato.app.MainActivity;
import com.siato.app.R;
import com.siato.app.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VerifikasiPengadaanBarangFragment extends Fragment {
    public static final String TAG = VerifikasiPengadaanBarangFragment.class.getSimpleName();
    private String ACTION = "TAMBAH";
    private Integer IDDetailPengadaanBarang;
    private TextInputEditText etJumlahDatang;
    private TextInputEditText etHarga;
    private Button btnVerifikasi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_verifikasi_pengadaan_barang, container, false);

        etJumlahDatang = view.findViewById(R.id.etVerifikasiPengadaanBarang_JumlahDatang);
        etHarga = view.findViewById(R.id.etVerifikasiPengadaanBarang_Harga);
        btnVerifikasi = view.findViewById(R.id.btnVerifikasiPengadaanBarang);

        if(getArguments() != null) {
            IDDetailPengadaanBarang = getArguments().getInt("id_detail_pengadaan_barang");
        }

        btnVerifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
                Call<APIResponse> call = APIService.verifikasiDetailPengadaanBarang(
                        IDDetailPengadaanBarang,
                        etJumlahDatang.getText().toString(),
                        etHarga.getText().toString(),
                        ((MainActivity) getActivity()).logged_in_user.getApiKey()
                );
                call.enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        APIResponse apiResponse = response.body();

                        Toast.makeText(getContext(), apiResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        if(!apiResponse.getError()) {
                            getActivity().getSupportFragmentManager().popBackStackImmediate();
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).getSupportActionBar()
                .setTitle(ACTION.substring(0, 1) + ACTION.substring(1).toLowerCase() + " " + "Verifikasi Barang Masuk");
    }
}
