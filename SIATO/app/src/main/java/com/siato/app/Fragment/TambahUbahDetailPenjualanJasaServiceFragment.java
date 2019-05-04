package com.siato.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.siato.app.API;
import com.siato.app.APIResponse;
import com.siato.app.MainActivity;
import com.siato.app.POJO.DetailPenjualanJasaService;
import com.siato.app.POJO.DetailPenjualanSpareparts;
import com.siato.app.R;
import com.siato.app.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahUbahDetailPenjualanJasaServiceFragment extends Fragment {
    public static final String TAG = TambahUbahDetailPenjualanJasaServiceFragment.class.getSimpleName();
    private String ACTION = "TAMBAH";
    private Integer IDDetailPenjualan;
    private Integer IDDetailPenjualanSpareparts;
    private TextInputEditText etJasaService;
    private Button btnTambahUbah;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah_ubah_detail_penjualan_jasaservice, container, false);

        etJasaService = view.findViewById(R.id.etDetailPenjualanJasaService);
        btnTambahUbah = view.findViewById(R.id.btnTambahUbahDetailPenjualanJasaService);

        if(getArguments() != null) {
            if(getArguments().getBoolean("ubah_detail_penjualan_jasaservice")) {
                ACTION = "UBAH";
                DetailPenjualanJasaService detailPenjualanJasaService = getArguments().getParcelable("detail_penjualan_jasaservice");
                IDDetailPenjualanSpareparts = detailPenjualanJasaService.getId();
                etJasaService.setText(detailPenjualanJasaService.getJasaService().getNama());
                btnTambahUbah.setText("Ubah");
            }
            else {
                IDDetailPenjualan = getArguments().getInt("id_detail_penjualan");
            }
        }

        btnTambahUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
                switch (ACTION) {
                    case "TAMBAH":
                        Call<APIResponse> create = APIService.createDetailPenjualanJasaService(
                                IDDetailPenjualan,
                                etJasaService.getText().toString(),
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
