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
    private Integer IDPenjualan = null;
    private Integer IDDetailPenjualan = null;
    private Integer IDKonsumen = null;
    private Spinner spKendaraan;
    private Spinner spMontir;
    private Button btnTambahUbah;

    private List<Kendaraan> listKendaraan;
    private String currentKendaraanNomorPolisi;
    private List<Pegawai> listMontir;
    private Integer currentMontirID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah_ubah_detail_penjualan, container, false);

        spKendaraan = view.findViewById(R.id.spDetailPenjualanKendaraan);
        spMontir = view.findViewById(R.id.spDetailPenjualanMontir);
        btnTambahUbah = view.findViewById(R.id.btnTambahUbahDetailPenjualan);

        if(getArguments() != null) {
            if(getArguments().getBoolean("ubah_detail_penjualan")) {
                ACTION = "UBAH";
                DetailPenjualan detailPenjualan = getArguments().getParcelable("detail_penjualan");
                IDDetailPenjualan = detailPenjualan.getId();
                currentKendaraanNomorPolisi = detailPenjualan.getKendaraan().getNomorPolisi();
                spKendaraan.setEnabled(false);
                currentMontirID = detailPenjualan.getMontir().getId();
                btnTambahUbah.setText("Ubah");
            }
            else {
                IDPenjualan = getArguments().getInt("id_penjualan");
            }
            IDKonsumen = getArguments().getInt("id_konsumen");
        }

        setKendaraan();
        setMontir();

        btnTambahUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
                switch (ACTION) {
                    case "TAMBAH":
                        Call<APIResponse> create = APIService.createDetailPenjualan(
                                IDPenjualan,
                                String.valueOf(listKendaraan.get(spKendaraan.getSelectedItemPosition()).getNomorPolisi()),
                                String.valueOf(listMontir.get(spMontir.getSelectedItemPosition()).getId()),
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
                        Call<APIResponse> update = APIService.updateDetailPenjualan(
                                IDDetailPenjualan,
                                String.valueOf(listMontir.get(spMontir.getSelectedItemPosition()).getId()),
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

    private void setKendaraan() {
        API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
        Call<APIResponse<List<Kendaraan>>> call = APIService.getKendaraanByKonsumen(IDKonsumen, ((MainActivity)getActivity()).logged_in_user.getApiKey());
        call.enqueue(new Callback<APIResponse<List<Kendaraan>>>(){
            @Override
            public void onResponse(Call<APIResponse<List<Kendaraan>>> call, Response<APIResponse<List<Kendaraan>>> response) {
                APIResponse<List<Kendaraan>> apiResponse = response.body();

                if (!apiResponse.getError()){
                    listKendaraan = apiResponse.getData();

                    List<String> listKendaraan = new ArrayList<>();

                    for(Kendaraan kendaraan:apiResponse.getData()) {
                        listKendaraan.add(kendaraan.getNomorPolisi());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,listKendaraan);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spKendaraan.setAdapter(adapter);

                    if(ACTION.equals("UBAH")) {
                        for(int i=0; i<apiResponse.getData().size(); i++) {
                            if(apiResponse.getData().get(i).getNomorPolisi().equals(currentKendaraanNomorPolisi)) {
                                spKendaraan.setSelection(i);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<Kendaraan>>> call, Throwable t) {
                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setMontir() {
        API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
        Call<APIResponse<List<Pegawai>>> call = APIService.getAllMontir(((MainActivity)getActivity()).logged_in_user.getApiKey());
        call.enqueue(new Callback<APIResponse<List<Pegawai>>>(){
            @Override
            public void onResponse(Call<APIResponse<List<Pegawai>>> call, Response<APIResponse<List<Pegawai>>> response) {
                APIResponse<List<Pegawai>> apiResponse = response.body();

                if (!apiResponse.getError()){
                    listMontir = apiResponse.getData();

                    List<String> listMontir = new ArrayList<>();

                    for(Pegawai montir:apiResponse.getData()) {
                        listMontir.add(montir.getNama());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,listMontir);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spMontir.setAdapter(adapter);

                    if(ACTION.equals("UBAH")) {
                        for(int i=0; i<apiResponse.getData().size(); i++) {
                            if(apiResponse.getData().get(i).getId().equals(currentMontirID)) {
                                spMontir.setSelection(i);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<Pegawai>>> call, Throwable t) {
                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).getSupportActionBar()
                .setTitle(ACTION.substring(0, 1) + ACTION.substring(1).toLowerCase() + " " + "Kendaraan");
    }
}
