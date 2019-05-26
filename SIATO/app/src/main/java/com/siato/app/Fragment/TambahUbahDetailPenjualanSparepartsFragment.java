package com.siato.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.siato.app.API;
import com.siato.app.APIResponse;
import com.siato.app.MainActivity;
import com.siato.app.POJO.DetailPenjualanSpareparts;
import com.siato.app.POJO.Spareparts;
import com.siato.app.R;
import com.siato.app.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahUbahDetailPenjualanSparepartsFragment extends Fragment {
    public static final String TAG = TambahUbahDetailPenjualanSparepartsFragment.class.getSimpleName();
    private String ACTION = "TAMBAH";
    private Integer IDDetailPenjualan = null;
    private Spinner spSpareparts;
    private TextInputEditText etJumlah;
    private Button btnTambahUbah;

    private DetailPenjualanSpareparts selectedDetailPenjualanSpareparts = null;
    private List<Spareparts> listSparepart;
    private String currentSparepartsKode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah_ubah_detail_penjualan_spareparts, container, false);

        spSpareparts = view.findViewById(R.id.spDetailPenjualanSpareparts);
        etJumlah = view.findViewById(R.id.etDetailPenjualanSparepartsJumlah);
        btnTambahUbah = view.findViewById(R.id.btnTambahUbahDetailPenjualanSpareparts);

        if(getArguments() != null) {
            if(getArguments().getBoolean("ubah_detail_penjualan_spareparts")) {
                ACTION = "UBAH";
                DetailPenjualanSpareparts detailPenjualanSpareparts = getArguments().getParcelable("detail_penjualan_spareparts");
                selectedDetailPenjualanSpareparts = detailPenjualanSpareparts;
                currentSparepartsKode = detailPenjualanSpareparts.getSpareparts().getKode();
                spSpareparts.setEnabled(false);
                etJumlah.setText(String.valueOf(detailPenjualanSpareparts.getJumlah()));
                btnTambahUbah.setText("Ubah");
            }
            else {
                IDDetailPenjualan = getArguments().getInt("id_detail_penjualan");
            }
        }

        setSpareparts();

        btnTambahUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
                switch (ACTION) {
                    case "TAMBAH":
                        Call<APIResponse> create = APIService.createDetailPenjualanSpareparts(
                                IDDetailPenjualan,
                                String.valueOf(listSparepart.get(spSpareparts.getSelectedItemPosition()).getKode()),
                                etJumlah.getText().toString(),
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
                        Call<APIResponse> update = APIService.updateDetailPenjualanSpareparts(
                                selectedDetailPenjualanSpareparts.getId(),
                                etJumlah.getText().toString(),
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

    private void setSpareparts() {
        API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
        Call<APIResponse<List<Spareparts>>> call = APIService.getAllSpareparts(((MainActivity)getActivity()).logged_in_user.getApiKey());
        call.enqueue(new Callback<APIResponse<List<Spareparts>>>(){
            @Override
            public void onResponse(Call<APIResponse<List<Spareparts>>> call, Response<APIResponse<List<Spareparts>>> response) {
                APIResponse<List<Spareparts>> apiResponse = response.body();

                if (!apiResponse.getError()){
                    listSparepart = apiResponse.getData();

                    List<String> listSparepart = new ArrayList<>();

                    for(Spareparts spareparts:apiResponse.getData()) {
                        listSparepart.add(spareparts.getNama());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,listSparepart);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spSpareparts.setAdapter(adapter);

                    if(ACTION.equals("UBAH")) {
                        for(int i=0; i<apiResponse.getData().size(); i++) {
                            if(apiResponse.getData().get(i).getKode().equals(currentSparepartsKode)) {
                                spSpareparts.setSelection(i);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<Spareparts>>> call, Throwable t) {
                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).getSupportActionBar()
                .setTitle(ACTION.substring(0, 1) + ACTION.substring(1).toLowerCase() + " " + "Spareparts");
    }
}
