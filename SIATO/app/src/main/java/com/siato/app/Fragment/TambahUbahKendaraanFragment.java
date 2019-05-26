package com.siato.app.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.siato.app.API;
import com.siato.app.APIResponse;
import com.siato.app.MainActivity;
import com.siato.app.POJO.Kendaraan;
import com.siato.app.POJO.Konsumen;
import com.siato.app.R;
import com.siato.app.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TambahUbahKendaraanFragment extends Fragment {
    public static final String TAG = TambahUbahKendaraanFragment.class.getSimpleName();
    private String ACTION = "TAMBAH";
    private TextInputEditText etNomorPolisi;
    private AutoCompleteTextView etMerk;
    private AutoCompleteTextView etTipe;
    private AutoCompleteTextView etPemilik;
    private Button btnTambahUbah;

    private List<Konsumen> listKonsumen;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah_ubah_kendaraan, container, false);

        etNomorPolisi = view.findViewById(R.id.etKendaraanNomorPolisi);
        etMerk = view.findViewById(R.id.etKendaraanMerk);
        etTipe = view.findViewById(R.id.etKendaraanTipe);
        etPemilik = view.findViewById(R.id.etKendaraanPemilik);
        btnTambahUbah = view.findViewById(R.id.btnTambahUbahKendaraan);

        etPemilik.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0) {
                    API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
                    Call<APIResponse<List<Konsumen>>> call = APIService.searchKonsumen(s.toString());
                    call.enqueue(new Callback<APIResponse<List<Konsumen>>>() {
                        @Override
                        public void onResponse(Call<APIResponse<List<Konsumen>>> call, Response<APIResponse<List<Konsumen>>> response) {
                            APIResponse<List<Konsumen>> apiResponse = response.body();

                            if(!apiResponse.getError()) {
                                listKonsumen = apiResponse.getData();

                                List<String> listPemilik = new ArrayList<>();

                                for (Konsumen konsumen:apiResponse.getData()) {
                                    listPemilik.add(konsumen.getNama());
                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listPemilik);
                                etPemilik.setAdapter(adapter);
                            }
                        }

                        @Override
                        public void onFailure(Call<APIResponse<List<Konsumen>>> call, Throwable t) {
                            Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if(getArguments() != null && getArguments().getParcelable("kendaraan") != null) {
            ACTION = "UBAH";
            Kendaraan kendaraan = getArguments().getParcelable("kendaraan");
            etNomorPolisi.setText(kendaraan.getNomorPolisi());
            etNomorPolisi.setEnabled(false);
            etMerk.setText(kendaraan.getMerk());
            etTipe.setText(kendaraan.getTipe());
            etPemilik.setText(kendaraan.getPemilik().getNama());
            btnTambahUbah.setText("Ubah");
        }

        setAvailableMerkAndTipe();

        btnTambahUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
                switch (ACTION) {
                    case "TAMBAH":
                        Call<APIResponse> create = APIService.createKendaraan(
                                etNomorPolisi.getText().toString(),
                                etMerk.getText().toString(),
                                etTipe.getText().toString(),
                                String.valueOf((!TextUtils.isEmpty(etPemilik.getText())) ? ((getSelectedKonsumen() != null) ? getSelectedKonsumen().getId() : null) : null),
                                ((MainActivity)getActivity()).logged_in_user.getApiKey()
                        );
                        create.enqueue(new Callback<APIResponse>() {
                            @Override
                            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                                APIResponse apiResponse = response.body();

                                if(!apiResponse.getError()) {
                                    ((MainActivity)getActivity()).changeFragment(R.id.nav_data_kendaraan);
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
                        Call<APIResponse> update = APIService.updateKendaraan(
                                etNomorPolisi.getText().toString(),
                                etMerk.getText().toString(),
                                etTipe.getText().toString(),
                                String.valueOf((!TextUtils.isEmpty(etPemilik.getText())) ? ((getSelectedKonsumen() != null) ? getSelectedKonsumen().getId() : null) : null),
                                ((MainActivity)getActivity()).logged_in_user.getApiKey()
                        );
                        update.enqueue(new Callback<APIResponse>() {
                            @Override
                            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                                APIResponse apiResponse = response.body();

                                if(!apiResponse.getError()) {
                                    ((MainActivity)getActivity()).changeFragment(R.id.nav_data_kendaraan);
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

    private void setAvailableMerkAndTipe() {
        API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
        Call<APIResponse<List<String>>> availableMerk = APIService.listAvailableMerkKendaraan();
        availableMerk.enqueue(new Callback<APIResponse<List<String>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<String>>> call, Response<APIResponse<List<String>>> response) {
                APIResponse<List<String>> apiResponse = response.body();

                if(!apiResponse.getError()) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, apiResponse.getData());
                    etMerk.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<String>>> call, Throwable t) {
                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Call<APIResponse<List<String>>> availableTipe = APIService.listAvailableTipeKendaraan();
        availableTipe.enqueue(new Callback<APIResponse<List<String>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<String>>> call, Response<APIResponse<List<String>>> response) {
                APIResponse<List<String>> apiResponse = response.body();

                if(!apiResponse.getError()) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, apiResponse.getData());
                    etTipe.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<String>>> call, Throwable t) {
                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Konsumen getSelectedKonsumen() {
        for (Konsumen konsumen:listKonsumen) {
            if(konsumen.getNama().equals(etPemilik.getText().toString())) {
                return konsumen;
            }
        }

        return null;
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).getSupportActionBar()
                .setTitle(ACTION.substring(0, 1) + ACTION.substring(1).toLowerCase() + " " + getResources().getString(R.string.data_kendaraan));
    }
}