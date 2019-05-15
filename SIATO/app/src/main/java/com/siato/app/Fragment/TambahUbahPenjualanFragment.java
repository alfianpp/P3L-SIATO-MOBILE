package com.siato.app.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.siato.app.API;
import com.siato.app.APIResponse;
import com.siato.app.MainActivity;
import com.siato.app.POJO.Konsumen;
import com.siato.app.POJO.Partially.Cabang;
import com.siato.app.POJO.Partially.Pegawai;
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
    private Spinner btnSpinnerJenis;
    private Spinner btnSpinnerCabang;
    private Spinner btnSpinnerKonsumen;
    private String jenis;
    private Button btnTambahUbah;
    private List<Cabang> listCabang;
    private List<Konsumen> listKonsumen;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah_ubah_penjualan, container, false);

        btnSpinnerJenis = view.findViewById(R.id.spJenisPenjualan);
        btnSpinnerCabang = view.findViewById(R.id.spCabangPenjualan);
        btnSpinnerKonsumen = view.findViewById(R.id.spKonsumenPenjualan);
        btnTambahUbah = view.findViewById(R.id.btnTambahUbahPenjualan);

        if(getArguments() != null && getArguments().getParcelable("penjualan") != null) {
            ACTION = "UBAH";
            Penjualan penjualan = getArguments().getParcelable("penjualan");
            IDPenjualan = penjualan.getId();
            btnTambahUbah.setText("Ubah");
        }
        
        spinnerCabang();
        spinnerKonsumen();

        btnSpinnerJenis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String firstItem = String.valueOf(btnSpinnerJenis.getSelectedItem());
                if (firstItem.equals("Service")){
                    jenis = "SV";
                }else if (firstItem.equals("Sparepart")){
                    jenis = "SP";
                }else if (firstItem.equals("Service dan Sparepart")){
                    jenis = "SS";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        
        btnTambahUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
                switch (ACTION) {
                    case "TAMBAH":
                        Call<APIResponse> create = APIService.createPenjualan(
                                String.valueOf(listCabang.get(btnSpinnerCabang.getSelectedItemPosition()).getId()),
                                jenis,
                                String.valueOf(listKonsumen.get(btnSpinnerKonsumen.getSelectedItemPosition()).getID()),
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
                                String.valueOf(listCabang.get(btnSpinnerCabang.getSelectedItemPosition()).getId()),
                                jenis,
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

    private void spinnerKonsumen() {
        API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
        Call<APIResponse<List<Konsumen>>> call = APIService.getAllKonsumen(((MainActivity)getActivity()).logged_in_user.getApiKey());
        call.enqueue(new Callback<APIResponse<List<Konsumen>>>(){
            @Override
            public void onResponse(Call<APIResponse<List<Konsumen>>> call, Response<APIResponse<List<Konsumen>>> response) {
                APIResponse<List<Konsumen>> apiResponse = response.body();

                if (!apiResponse.getError()){
                    listKonsumen = apiResponse.getData();
                    List<String> listKonsumen = new ArrayList<>();

                    for (int i = 0; i < apiResponse.getData().size(); i++){
                        listKonsumen.add(apiResponse.getData().get(i).getNama());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,listKonsumen);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    btnSpinnerKonsumen.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<Konsumen>>> call, Throwable t) {
                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void spinnerCabang() {
        API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
        Call<APIResponse<List<Cabang>>> call = APIService.getAllCabang(((MainActivity)getActivity()).logged_in_user.getApiKey());
        call.enqueue(new Callback<APIResponse<List<Cabang>>>(){
            @Override
            public void onResponse(Call<APIResponse<List<Cabang>>> call, Response<APIResponse<List<Cabang>>> response) {
                APIResponse<List<Cabang>> apiResponse = response.body();

                if (!apiResponse.getError()){
                    listCabang = apiResponse.getData();
                    List<String> listCabang = new ArrayList<>();

                    for (int i = 0; i < apiResponse.getData().size(); i++){
                        listCabang.add(apiResponse.getData().get(i).getNama());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,listCabang);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    btnSpinnerCabang.setAdapter(adapter);
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
                .setTitle(ACTION.substring(0, 1) + ACTION.substring(1).toLowerCase() + " " + getResources().getString(R.string.data_penjualan));
    }
}
