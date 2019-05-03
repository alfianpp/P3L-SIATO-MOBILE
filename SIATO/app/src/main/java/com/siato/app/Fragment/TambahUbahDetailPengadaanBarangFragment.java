package com.siato.app.Fragment;

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
import com.siato.app.POJO.DetailPengadaanBarang;
import com.siato.app.R;
import com.siato.app.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TambahUbahDetailPengadaanBarangFragment extends Fragment {
    public static final String TAG = TambahUbahDetailPengadaanBarangFragment.class.getSimpleName();
    private String ACTION = "TAMBAH";
    private Integer IDPengadaanBarang;
    private Integer IDDetailPengadaanBarang = null;
    private TextInputEditText etIdSparepart;
    private TextInputEditText etjumlah;
    private Button btnTambahUbah;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah_ubah_detail_pengadaan_barang, container, false);

        etIdSparepart = view.findViewById(R.id.etIdSparepart);
        etjumlah = view.findViewById(R.id.etJumlah);
        btnTambahUbah = view.findViewById(R.id.btnTambahUbahDetilPengadaanBarang);

        if(getArguments() != null) {
            if(getArguments().getBoolean("ubah_detail_pengadaan_barang")) {
                ACTION = "UBAH";
                DetailPengadaanBarang detailPengadaanBarang = getArguments().getParcelable("detail_pengadaan_barang");
                IDDetailPengadaanBarang = detailPengadaanBarang.getId();
                etIdSparepart.setText(detailPengadaanBarang.getSpareparts().getKode());
                etjumlah.setText(String.valueOf(detailPengadaanBarang.getJumlahPesan()));
                btnTambahUbah.setText("Ubah");
            }
            else {
                IDPengadaanBarang = getArguments().getInt("id_pengadaan_barang");
            }
        }

        btnTambahUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
                switch (ACTION) {
                    case "TAMBAH":
                        Call<APIResponse> create = APIService.createDetailPengadaanBarang(
                                IDPengadaanBarang,
                                etIdSparepart.getText().toString(),
                                etjumlah.getText().toString(),
                                ((MainActivity) getActivity()).logged_in_user.getApiKey()
                        );
                        create.enqueue(new Callback<APIResponse>() {
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
                        break;

                    case "UBAH":
                        Call<APIResponse> update = APIService.updateDetailPengadaanBarang(
                                IDDetailPengadaanBarang,
                                etjumlah.getText().toString(),
                                ((MainActivity) getActivity()).logged_in_user.getApiKey()
                        );
                        update.enqueue(new Callback<APIResponse>() {
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
                        break;
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).getSupportActionBar()
                .setTitle(ACTION.substring(0, 1) + ACTION.substring(1).toLowerCase() + " " + getResources().getString(R.string.transaksi_detail_pengadaan_barang));
    }
}
