package com.siato.app.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.siato.app.API;
import com.siato.app.APIResponse;
import com.siato.app.MainActivity;
import com.siato.app.POJO.Konsumen;
import com.siato.app.POJO.Partially.Cabang;
import com.siato.app.POJO.Penjualan;
import com.siato.app.R;
import com.siato.app.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahUbahPenjualanFragment extends Fragment {
    public static final String TAG = TambahUbahPenjualanFragment.class.getSimpleName();
    private String ACTION = "TAMBAH";
    private Integer IDPenjualan = null;
    private Spinner spJenis;
    private Spinner spCabang;
    private Spinner spKonsumen;
    private Button btnTambahUbah;

    private String currentJenisID;
    private List<Cabang> listCabang;
    private Integer currentCabangID;
    private List<Konsumen> listKonsumen;
    private Integer currentKonsumenID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah_ubah_penjualan, container, false);

        spJenis = view.findViewById(R.id.spPenjualanJenis);
        spCabang = view.findViewById(R.id.spPenjualanCabang);
        spKonsumen = view.findViewById(R.id.spPenjualanKonsumen);
        btnTambahUbah = view.findViewById(R.id.btnTambahUbahPenjualan);

        final List<String> jenisID = new ArrayList<>();
        jenisID.add("SV");
        jenisID.add("SP");
        jenisID.add("SS");

        final List<String> jenisValue = new ArrayList<>();
        jenisValue.add("Service");
        jenisValue.add("Spareparts");
        jenisValue.add("Service & Spareparts");

        if(getArguments() != null && getArguments().getParcelable("penjualan") != null) {
            ACTION = "UBAH";
            Penjualan penjualan = getArguments().getParcelable("penjualan");
            IDPenjualan = penjualan.getId();
            currentJenisID = penjualan.getJenis();
            if(currentJenisID.equals("SP")) {
                spJenis.setEnabled(false);
            }
            else if(currentJenisID.equals("SV") || currentJenisID.equals("SS")) {
                jenisID.remove("SP");
                jenisValue.remove("Spareparts");
            }
            currentCabangID = penjualan.getCabang().getId();
            currentKonsumenID = penjualan.getKonsumen().getId();
            spKonsumen.setEnabled(false);
            btnTambahUbah.setText("Ubah");
        }

        ArrayAdapter<String> jenisAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, jenisValue);
        jenisAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJenis.setAdapter(jenisAdapter);

        if(ACTION.equals("UBAH")) {
            for(int i=0; i<jenisID.size(); i++) {
                if(jenisID.get(i).equals(currentJenisID)) {
                    spJenis.setSelection(i);
                }
            }
        }
        
        setCabang();
        setKonsumen();
        
        btnTambahUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
                switch (ACTION) {
                    case "TAMBAH":
                        Call<APIResponse> create = APIService.createPenjualan(
                                String.valueOf(listCabang.get(spCabang.getSelectedItemPosition()).getId()),
                                jenisID.get(spJenis.getSelectedItemPosition()),
                                String.valueOf(listKonsumen.get(spKonsumen.getSelectedItemPosition()).getId()),
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
                                String.valueOf(listCabang.get(spCabang.getSelectedItemPosition()).getId()),
                                jenisID.get(spJenis.getSelectedItemPosition()),
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

    private void setKonsumen() {
        API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
        Call<APIResponse<List<Konsumen>>> call = APIService.getAllKonsumen(((MainActivity)getActivity()).logged_in_user.getApiKey());
        call.enqueue(new Callback<APIResponse<List<Konsumen>>>(){
            @Override
            public void onResponse(Call<APIResponse<List<Konsumen>>> call, Response<APIResponse<List<Konsumen>>> response) {
                APIResponse<List<Konsumen>> apiResponse = response.body();

                if (!apiResponse.getError()){
                    listKonsumen = apiResponse.getData();

                    List<String> listKonsumen = new ArrayList<>();

                    for(Konsumen konsumen:apiResponse.getData()) {
                        listKonsumen.add(konsumen.getNama());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,listKonsumen);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spKonsumen.setAdapter(adapter);

                    if(ACTION.equals("UBAH")) {
                        for(int i=0; i<apiResponse.getData().size(); i++) {
                            if(apiResponse.getData().get(i).getId().equals(currentKonsumenID)) {
                                spKonsumen.setSelection(i);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<Konsumen>>> call, Throwable t) {
                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setCabang() {
        API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
        Call<APIResponse<List<Cabang>>> call = APIService.getAllCabang(((MainActivity)getActivity()).logged_in_user.getApiKey());
        call.enqueue(new Callback<APIResponse<List<Cabang>>>(){
            @Override
            public void onResponse(Call<APIResponse<List<Cabang>>> call, Response<APIResponse<List<Cabang>>> response) {
                APIResponse<List<Cabang>> apiResponse = response.body();

                if (!apiResponse.getError()){
                    listCabang = apiResponse.getData();

                    List<String> listCabang = new ArrayList<>();

                    for(Cabang cabang:apiResponse.getData()) {
                        listCabang.add(cabang.getNama());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,listCabang);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spCabang.setAdapter(adapter);

                    if(ACTION.equals("UBAH")) {
                        for(int i=0; i<apiResponse.getData().size(); i++) {
                            if(apiResponse.getData().get(i).getId().equals(currentCabangID)) {
                                spCabang.setSelection(i);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<Cabang>>> call, Throwable t) {
                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).getSupportActionBar()
                .setTitle(ACTION.substring(0, 1) + ACTION.substring(1).toLowerCase() + " " + "Penjualan");
    }
}
