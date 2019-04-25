package com.siato.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.siato.app.API;
import com.siato.app.APIResponse;
import com.siato.app.MainActivity;
import com.siato.app.POJO.Konsumen;
import com.siato.app.POJO.Spareparts;
import com.siato.app.Pegawai;
import com.siato.app.R;
import com.siato.app.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahUbahKonsumenFragment extends Fragment {
    private String ACTION = "TAMBAH";
    private Integer IDKonsumen = null;
    private TextInputEditText etNama;
    private TextInputEditText etNomorTelepon;
    private TextInputEditText etAlamat;
    private Button btnTambahUbah;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah_ubah_konsumen, container, false);

        etNama = view.findViewById(R.id.etKonsumenNama);
        etNomorTelepon = view.findViewById(R.id.etKonsumenNomorTelepon);
        etAlamat = view.findViewById(R.id.etKonsumenAlamat);
        btnTambahUbah = view.findViewById(R.id.btnTambahUbahKonsumen);

        if(getArguments() != null && getArguments().getParcelable("konsumen") != null) {
            ACTION = "UBAH";
            ((MainActivity)getActivity()).setActionBarTitle("Ubah Konsumen");
            Konsumen konsumen = getArguments().getParcelable("konsumen");
            IDKonsumen = konsumen.getID();
            etNama.setText(konsumen.getNama());
            etNomorTelepon.setText(konsumen.getNomorTelepon());
            etAlamat.setText(konsumen.getAlamat());
            btnTambahUbah.setText("Ubah");
        }

        btnTambahUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
                switch (ACTION){
                    case "TAMBAH":
                        Call<APIResponse> create = APIService.createKonsumen(
                                etNama.getText().toString(),
                                etNomorTelepon.getText().toString(),
                                etAlamat.getText().toString(),
                                ((MainActivity)getActivity()).logged_in_user.getApiKey()
                        );
                        create.enqueue(new Callback<APIResponse>() {
                            @Override
                            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                                APIResponse<Pegawai> apiResponse = response.body();

                                if(!apiResponse.getError()) {
                                    ((MainActivity)getActivity()).changeFragment(R.id.nav_data_konsumen);
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
                        Call<APIResponse> update = APIService.updateKonsumen(
                                IDKonsumen,
                                etNama.getText().toString(),
                                etNomorTelepon.getText().toString(),
                                etAlamat.getText().toString(),
                                ((MainActivity)getActivity()).logged_in_user.getApiKey()
                        );
                        update.enqueue(new Callback<APIResponse>() {
                            @Override
                            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                                APIResponse<Pegawai> apiResponse = response.body();

                                if(!apiResponse.getError()) {
                                    ((MainActivity)getActivity()).changeFragment(R.id.nav_data_konsumen);
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
}
