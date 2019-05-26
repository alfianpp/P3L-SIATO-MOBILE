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

import com.siato.app.API;
import com.siato.app.APIResponse;
import com.siato.app.MainActivity;
import com.siato.app.POJO.DetailPenjualanJasaService;
import com.siato.app.POJO.Partially.JasaService;
import com.siato.app.R;
import com.siato.app.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahUbahDetailPenjualanJasaServiceFragment extends Fragment {
    public static final String TAG = TambahUbahDetailPenjualanJasaServiceFragment.class.getSimpleName();
    private String ACTION = "TAMBAH";
    private Integer IDDetailPenjualan = null;
    private Spinner spJasaService;
    private Button btnTambahUbah;

    private DetailPenjualanJasaService selectedDetailPenjualanJasaService = null;
    private List<JasaService> listJasaService;
    private Integer currentJasaServiceID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah_ubah_detail_penjualan_jasaservice, container, false);

        spJasaService = view.findViewById(R.id.spDetailPenjualanJasaService);
        btnTambahUbah = view.findViewById(R.id.btnTambahUbahDetailPenjualanJasaService);

        if(getArguments() != null) {
            if(getArguments().getBoolean("ubah_detail_penjualan_jasaservice")) {
                ACTION = "UBAH";
                DetailPenjualanJasaService detailPenjualanJasaService = getArguments().getParcelable("detail_penjualan_jasaservice");
                selectedDetailPenjualanJasaService = detailPenjualanJasaService;
                btnTambahUbah.setText("Ubah");
            }
            else {
                IDDetailPenjualan = getArguments().getInt("id_detail_penjualan");
            }
        }

        setJasaService();

        btnTambahUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
                switch (ACTION) {
                    case "TAMBAH":
                        Call<APIResponse> create = APIService.createDetailPenjualanJasaService(
                                IDDetailPenjualan,
                                String.valueOf(listJasaService.get(spJasaService.getSelectedItemPosition()).getId()),
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

    private void setJasaService() {
        API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
        Call<APIResponse<List<JasaService>>> call = APIService.getAllJasaService(((MainActivity)getActivity()).logged_in_user.getApiKey());
        call.enqueue(new Callback<APIResponse<List<JasaService>>>(){
            @Override
            public void onResponse(Call<APIResponse<List<JasaService>>> call, Response<APIResponse<List<JasaService>>> response) {
                APIResponse<List<JasaService>> apiResponse = response.body();

                if (!apiResponse.getError()){
                    listJasaService = apiResponse.getData();

                    List<String> listJasa = new ArrayList<>();

                    for(JasaService jasaService:apiResponse.getData()) {
                        listJasa.add(jasaService.getNama());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,listJasa);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spJasaService.setAdapter(adapter);

                    if(ACTION.equals("UBAH")) {
                        for(int i=0; i<apiResponse.getData().size(); i++) {
                            if(apiResponse.getData().get(i).getId().equals(currentJasaServiceID)) {
                                spJasaService.setSelection(i);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<JasaService>>> call, Throwable t) {
                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).getSupportActionBar()
                .setTitle(ACTION.substring(0, 1) + ACTION.substring(1).toLowerCase() + " " + "Jasa Service");
    }
}
