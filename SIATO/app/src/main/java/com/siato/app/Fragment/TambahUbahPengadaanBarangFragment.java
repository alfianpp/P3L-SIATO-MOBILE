package com.siato.app.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.siato.app.API;
import com.siato.app.APIResponse;
import com.siato.app.MainActivity;
import com.siato.app.POJO.PengadaanBarang;
import com.siato.app.R;
import com.siato.app.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahUbahPengadaanBarangFragment extends Fragment {
    private String ACTION = "TAMBAH";
    private Integer IDPengadaanBarang = null;
    private TextInputEditText etIdS;
    private Button btnTambahUbah;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah_ubah_pengadaan_barang, container, false);

        etIdS = view.findViewById(R.id.etIdSupplier);
        btnTambahUbah = view.findViewById(R.id.btnTambahUbahPengadaanBarang);

        if(getArguments() != null && getArguments().getParcelable("pengadaan_barang") != null) {
            ACTION = "UBAH";
            ((MainActivity)getActivity()).setActionBarTitle("Ubah Transaksi");
            PengadaanBarang pengadaanBarang = getArguments().getParcelable("pengadaan_barang");
            IDPengadaanBarang = pengadaanBarang.getId();
            etIdS.setText(String.valueOf(pengadaanBarang.getId()));
            btnTambahUbah.setText("Ubah");
        }

        btnTambahUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
                switch (ACTION) {
                    case "TAMBAH":
                    Call<APIResponse> create = APIService.createPengadaanBarang(
                            etIdS.getText().toString(),
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
                                etIdS.getText().toString(),
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

//    private void SpinData(List<Supplier> response) {
//       try {
//           JSONObject object = new JSONObject(String.valueOf(response));
//           if (object.optString("status").equals("true")){
//               supplierArrayList = new ArrayList<>();
//               JSONArray dataArray = object.getJSONArray("data");
//
//               for(int i = 0; i < dataArray.length(); i++){
//
//                   Supplier supplier = new Supplier();
//                   JSONObject dataObj = dataArray.getJSONObject(i);
//
//                   supplier.setId(dataObj.getInt("id"));
//                   supplier.setNama(dataObj.getString("nama"));
//                   supplier.setAlamat(dataObj.getString("alamat"));
//                   supplier.setNama_sales(dataObj.getString("nama_sales"));
//                   supplier.setNomor_telepon_sales(dataObj.getString("nomor_telepon_sales"));
//
//                   supplierArrayList.add(supplier);
//               }
//               for (int i = 0; i < supplierArrayList.size(); i++){
//                   Names.add(supplierArrayList.get(i).getId().intValue());
//               }
//               ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>((MainActivity)getActivity(),android.R.layout.simple_spinner_item, Names);
//               spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//               SpinIdSupplier.setAdapter(spinnerArrayAdapter);
//           }
//       } catch (JSONException e) {
//           e.printStackTrace();
//       }
//    }
}
