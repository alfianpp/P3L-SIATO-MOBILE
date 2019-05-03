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
import com.siato.app.POJO.Partially.Pegawai;
import com.siato.app.POJO.Penjualan;
import com.siato.app.R;
import com.siato.app.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahUbahPenjualanFragment extends Fragment {
    public static final String TAG = TambahUbahPenjualanFragment.class.getSimpleName();
    private String ACTION = "TAMBAH";
    private Integer IDPenjualan = null;
    private TextInputEditText etJenis;
    private TextInputEditText etCabang;
    private TextInputEditText etKonsumen;
    private Button btnTambahUbah;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah_ubah_penjualan, container, false);

        etJenis = view.findViewById(R.id.etPenjualanJenis);
        etCabang = view.findViewById(R.id.etPenjualanCabang);
        etKonsumen = view.findViewById(R.id.etPenjualanKonsumen);
        btnTambahUbah = view.findViewById(R.id.btnTambahUbahPenjualan);

        if(getArguments() != null && getArguments().getParcelable("penjualan") != null) {
            ACTION = "UBAH";
            Penjualan penjualan = getArguments().getParcelable("penjualan");
            etJenis.setText(penjualan.getJenis());
            etCabang.setText(String.valueOf(penjualan.getCabang().getId()));
            etKonsumen.setText(String.valueOf(penjualan.getKonsumen().getId()));
            IDPenjualan = penjualan.getId();
            btnTambahUbah.setText("Ubah");
        }
        btnTambahUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
                switch (ACTION) {
                    case "TAMBAH":
                        Call<APIResponse> create = APIService.createPenjualan(
                                etCabang.getText().toString(),
                                etJenis.getText().toString(),
                                etKonsumen.getText().toString(),
                                ((MainActivity)getActivity()).logged_in_user.getApiKey()
                        );
                        create.enqueue(new Callback<APIResponse>() {
                            @Override
                            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                                APIResponse apiResponse = response.body();

                                if(!apiResponse.getError()) {
                                    ((MainActivity)getActivity()).changeFragment(R.id.nav_transaksi_penjualan);
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
                        Call<APIResponse> update = APIService.updatePenjualan(
                                IDPenjualan,
                                etCabang.getText().toString(),
                                etJenis.getText().toString(),
                                ((MainActivity)getActivity()).logged_in_user.getApiKey()
                        );
                        update.enqueue(new Callback<APIResponse>() {
                            @Override
                            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                                APIResponse apiResponse = response.body();

                                if(!apiResponse.getError()) {
                                    ((MainActivity)getActivity()).changeFragment(R.id.nav_transaksi_penjualan);
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

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).getSupportActionBar()
                .setTitle(ACTION.substring(0, 1) + ACTION.substring(1).toLowerCase() + " " + getResources().getString(R.string.data_penjualan));
    }
}
