package com.siato.app.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.siato.app.POJO.PengadaanBarang;
import com.siato.app.POJO.Supplier;
import com.siato.app.R;
import com.siato.app.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahUbahPengadaanBarangFragment extends Fragment {
    public static final String TAG = TambahUbahPengadaanBarangFragment.class.getSimpleName();
    private String ACTION = "TAMBAH";
    private Integer IDPengadaanBarang = null;
    private Button btnTambahUbah;
    private Spinner spinner;
    private List<Supplier> listSupplier;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah_ubah_pengadaan_barang, container, false);

        btnTambahUbah = view.findViewById(R.id.btnTambahUbahPengadaanBarang);
        spinner = view.findViewById(R.id.btnSpinner);


        if(getArguments() != null && getArguments().getParcelable("pengadaan_barang") != null) {
            ACTION = "UBAH";
            PengadaanBarang pengadaanBarang = getArguments().getParcelable("pengadaan_barang");
            IDPengadaanBarang = pengadaanBarang.getId();
            btnTambahUbah.setText("Ubah");
        }

        initSupplier();

        btnTambahUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
                switch (ACTION) {
                    case "TAMBAH":
                    Call<APIResponse> create = APIService.createPengadaanBarang(
                            String.valueOf(listSupplier.get(spinner.getSelectedItemPosition()).getId()),
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
                        Call<APIResponse> update = APIService.updatePengadaanBarang(
                                IDPengadaanBarang,
                                String.valueOf(listSupplier.get(spinner.getSelectedItemPosition()).getId()),
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

    public void initSupplier(){
        API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
        Call<APIResponse<List<Supplier>>> call = APIService.getAllSupplier(((MainActivity)getActivity()).logged_in_user.getApiKey());
        call.enqueue(new Callback<APIResponse<List<Supplier>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<Supplier>>> call, Response<APIResponse<List<Supplier>>> response) {
                APIResponse<List<Supplier>> apiResponse = response.body();

                if (!apiResponse.getError()){
                    listSupplier = apiResponse.getData();
                    List<String> listSupplier = new ArrayList<>();

                    for (int i = 0; i < apiResponse.getData().size(); i++){
                        listSupplier.add(apiResponse.getData().get(i).getNama());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,listSupplier);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<Supplier>>> call, Throwable t) {
                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).getSupportActionBar()
                .setTitle(ACTION.substring(0, 1) + ACTION.substring(1).toLowerCase() + " " + getResources().getString(R.string.data_pengadaan_barang));
    }
}
