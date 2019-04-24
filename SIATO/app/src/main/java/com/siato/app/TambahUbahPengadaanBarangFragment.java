package com.siato.app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.siato.app.R.id.DropDownIdSupplier;


public class TambahUbahPengadaanBarangFragment extends Fragment {
    private ArrayList<Supplier> supplierArrayList;
    private ArrayList<Integer> Names = new ArrayList<Integer>();
    private Spinner SpinIdSupplier;
    //private TextInputEditText idSupplier;
    private Double etTotal;
    private Integer etStatus;
    private Date etTgl;
    private Button btnTambahUbah;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah_ubah_pengadaan_barang, container, false);

        SpinIdSupplier = view.findViewById(DropDownIdSupplier);
        btnTambahUbah = view.findViewById(R.id.btnTambahUbahPengadaanBarang);
        etTotal = Double.valueOf("");
        etTgl = Date.valueOf("");
        etStatus = 1;


        API service =  RetrofitClientInstance.getRetrofitInstance().create(API.class);
        Call<APIResponse<List<Supplier>>> call = service.getAllSupplier(((MainActivity)getActivity()).logged_in_user.getApiKey());
        call.enqueue(new Callback<APIResponse<List<Supplier>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<Supplier>>> call, Response<APIResponse<List<Supplier>>> response) {
                List<Supplier> result = response.body().getData();
                SpinData(result);
            }

            @Override
            public void onFailure(Call<APIResponse<List<Supplier>>> call, Throwable t) {
                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        /*
        btnTambahUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API service = RetrofitClientInstance.getRetrofitInstance().create(API.class);
                Call<APIResponse> call = service.createSupplier(
                        SpinIdSupplier.toString(),
                        etTotal.getText().toString(),
                        etStatus.getText().toString(),
                        etTgl.getText().toString(),
                        ((MainActivity)getActivity()).logged_in_user.getApiKey()
                );
                call.enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        APIResponse<Pegawai> apiResponse = response.body();

                        Toast.makeText(getContext(),apiResponse.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        */
        return view;
    }

    private void SpinData(List<Supplier> response) {
       try {
           JSONObject object = new JSONObject(String.valueOf(response));
           if (object.optString("status").equals("true")){
               supplierArrayList = new ArrayList<>();
               JSONArray dataArray = object.getJSONArray("data");

               for(int i = 0; i < dataArray.length(); i++){

                   Supplier supplier = new Supplier();
                   JSONObject dataObj = dataArray.getJSONObject(i);

                   supplier.setId(dataObj.getInt("id"));
                   supplier.setNama(dataObj.getString("nama"));
                   supplier.setAlamat(dataObj.getString("alamat"));
                   supplier.setNama_sales(dataObj.getString("nama_sales"));
                   supplier.setNomor_telepon_sales(dataObj.getString("nomor_telepon_sales"));

                   supplierArrayList.add(supplier);
               }
               for (int i = 0; i < supplierArrayList.size(); i++){
                   Names.add(supplierArrayList.get(i).getId().intValue());
               }
               ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>((MainActivity)getActivity(),android.R.layout.simple_spinner_item, Names);
               spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
               SpinIdSupplier.setAdapter(spinnerArrayAdapter);
           }
       } catch (JSONException e) {
           e.printStackTrace();
       }
    }
}
