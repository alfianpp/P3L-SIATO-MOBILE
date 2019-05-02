package com.siato.app.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.siato.app.API;
import com.siato.app.APIResponse;
import com.siato.app.MainActivity;
import com.siato.app.POJO.DetilPengadaanBarang;
import com.siato.app.R;
import com.siato.app.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TambahUbahDetilPengadaanFragment extends Fragment {
    private String ACTION = "TAMBAH";
    private Integer IDDetilPengadaanBarang = null;
    private TextInputEditText etIdSparepart;
    private TextInputEditText etjumlah;
    private Integer id_pengadaan;
    private Integer jumlah_datang;
    private Button btnTambahUbah;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah_ubah_detil_pengadaan, container, false);

        etIdSparepart = view.findViewById(R.id.etIdSparepart);
        etjumlah = view.findViewById(R.id.etJumlah);
        btnTambahUbah = view.findViewById(R.id.btnTambahUbahDetilPengadaanBarang);

        if(getArguments() != null && getArguments().getParcelable("detil_pengadaan_barang") != null) {
            ACTION = "UBAH";
            ((MainActivity)getActivity()).setActionBarTitle("Ubah Transaksi");
            DetilPengadaanBarang detilPengadaanBarang = getArguments().getParcelable("detil_pengadaan_barang");
            IDDetilPengadaanBarang = detilPengadaanBarang.getId();
            etIdSparepart.setText(String.valueOf(detilPengadaanBarang.getSpareparts().getKode()));
            etjumlah.setText(String.valueOf(detilPengadaanBarang.getJmlPesan()));
            id_pengadaan = detilPengadaanBarang.getPengadaanBarang().getId();
            jumlah_datang = null;
            btnTambahUbah.setText("Ubah");
        }

        btnTambahUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
                switch (ACTION) {
                    case "TAMBAH":
                        Call<APIResponse> create = APIService.createDetilPengadaanBarang(
                                id_pengadaan,
                                etIdSparepart.getText().toString(),
                                etjumlah.getText().toString(),
                                jumlah_datang,
                                ((MainActivity) getActivity()).logged_in_user.getApiKey()
                        );
                        create.enqueue(new Callback<APIResponse>() {
                            @Override
                            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                                APIResponse apiResponse = response.body();

                                if(!apiResponse.getError()) {
                                    ((MainActivity)getActivity()).changeFragment(R.id.nav_transaksi_pengadaan_barang);
                                }

                                Toast.makeText(getContext(), apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<APIResponse> call, Throwable t) {
                                Toast.makeText(getActivity(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;

                    case "UBAH":
                        Call<APIResponse> update = APIService.updateDetilPengadaanBarang(
                                IDDetilPengadaanBarang,
                                id_pengadaan,
                                etIdSparepart.getText().toString(),
                                etjumlah.getText().toString(),
                                jumlah_datang,
                                ((MainActivity) getActivity()).logged_in_user.getApiKey()
                        );
                        update.enqueue(new Callback<APIResponse>() {
                            @Override
                            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                                APIResponse apiResponse = response.body();

                                if(!apiResponse.getError()) {
                                    ((MainActivity)getActivity()).changeFragment(R.id.nav_transaksi_pengadaan_barang);
                                }

                                Toast.makeText(getContext(), apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
