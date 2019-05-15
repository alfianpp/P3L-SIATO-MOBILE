package com.siato.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.siato.app.API;
import com.siato.app.APIResponse;
import com.siato.app.MainActivity;
import com.siato.app.POJO.DetailPenjualan;
import com.siato.app.POJO.Kendaraan;
import com.siato.app.POJO.Pegawai;
import com.siato.app.R;
import com.siato.app.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TambahUbahDetailPenjualanFragment extends Fragment {
    public static final String TAG = TambahUbahDetailPenjualanFragment.class.getSimpleName();
    private String ACTION = "TAMBAH";
    private Integer IDPenjualan;
    private Integer IDDetailPenjualan = null;
    private TextInputEditText etKendaraan;
    private TextInputEditText etMontir;
    private Spinner btnSpinnerKendaraan;
    private Spinner btnSpinnerMontir;
    private Button btnTambahUbah;
    private List<Kendaraan> listKendaraan;
    private List<Pegawai> listMontir;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah_ubah_detail_penjualan, container, false);

        btnSpinnerKendaraan = view.findViewById(R.id.spKendaraanPenjualanDetail);
        btnSpinnerMontir = view.findViewById(R.id.spMontirPenjualanDetail);
        btnTambahUbah = view.findViewById(R.id.btnTambahUbahDetailPenjualan);

        if(getArguments() != null) {
            if(getArguments().getBoolean("ubah_detail_penjualan")) {
                ACTION = "UBAH";
                DetailPenjualan detailPenjualan = getArguments().getParcelable("detail_penjualan");
                IDDetailPenjualan = detailPenjualan.getId();
                etKendaraan.setText(detailPenjualan.getKendaraan().getNomorPolisi());
                etMontir.setText(String.valueOf(detailPenjualan.getMontir().getId()));
                btnTambahUbah.setText("Ubah");
            }
            else {
                IDPenjualan = getArguments().getInt("id_penjualan");
            }
        }

        spinnerKendaraan();
        spinnerMontir();

        btnTambahUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
                switch (ACTION) {
                    case "TAMBAH":
                        Call<APIResponse> create = APIService.createDetailPenjualan(
                                IDPenjualan,
                                String.valueOf(listKendaraan.get(btnSpinnerKendaraan.getSelectedItemPosition()).getNomorPolisi()),
                                String.valueOf(listMontir.get(btnSpinnerMontir.getSelectedItemPosition()).getId()),
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
                                IDDetailPenjualan,
                                String.valueOf(listMontir.get(btnSpinnerMontir.getSelectedItemPosition()).getId()),
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

    private void spinnerMontir() {
        API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
        Call<APIResponse<List<Pegawai>>> call = APIService.getAllMontir(((MainActivity)getActivity()).logged_in_user.getApiKey());
        call.enqueue(new Callback<APIResponse<List<Pegawai>>>(){
            @Override
            public void onResponse(Call<APIResponse<List<Pegawai>>> call, Response<APIResponse<List<Pegawai>>> response) {
                APIResponse<List<Pegawai>> apiResponse = response.body();

                if (!apiResponse.getError()){
                    listMontir = apiResponse.getData();
                    List<String> listMontir = new ArrayList<>();

                    for (int i = 0; i < apiResponse.getData().size(); i++){
                        listMontir.add(apiResponse.getData().get(i).getNama());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,listMontir);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    btnSpinnerMontir.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<Pegawai>>> call, Throwable t) {
                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void spinnerKendaraan() {
        API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
        Call<APIResponse<List<Kendaraan>>> call = APIService.getAllKendaraan(((MainActivity)getActivity()).logged_in_user.getApiKey());
        call.enqueue(new Callback<APIResponse<List<Kendaraan>>>(){
            @Override
            public void onResponse(Call<APIResponse<List<Kendaraan>>> call, Response<APIResponse<List<Kendaraan>>> response) {
                APIResponse<List<Kendaraan>> apiResponse = response.body();

                if (!apiResponse.getError()){
                    listKendaraan = apiResponse.getData();
                    List<String> listKendaraan = new ArrayList<>();

                    for (int i = 0; i < apiResponse.getData().size(); i++){
                        listKendaraan.add(apiResponse.getData().get(i).getNomorPolisi());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,listKendaraan);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    btnSpinnerKendaraan.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<Kendaraan>>> call, Throwable t) {
                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).getSupportActionBar()
                .setTitle(ACTION.substring(0, 1) + ACTION.substring(1).toLowerCase() + " " + getResources().getString(R.string.transaksi_detail_pengadaan_barang));
    }
}
