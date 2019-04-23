package com.siato.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaSupplierFragment extends Fragment {
    private SupplierListAdapter adapter = null;
    private RecyclerView recyclerView;
    private Button btnTambahUbahSupplier;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_kelola_supplier, container, false);

        btnTambahUbahSupplier = view.findViewById(R.id.btnTambahUbahSupplier);

        API service =  RetrofitClientInstance.getRetrofitInstance().create(API.class);
        Call<APIResponse<List<Supplier>>> call = service.getAllSupplier(((MainActivity)getActivity()).logged_in_user.getApiKey());
        call.enqueue(new Callback<APIResponse<List<Supplier>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<Supplier>>> call, Response<APIResponse<List<Supplier>>> response) {
                List<Supplier> result = response.body().getData();
                generateDataList(result, view);
            }

            @Override
            public void onFailure(Call<APIResponse<List<Supplier>>> call, Throwable t) {
                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btnTambahUbahSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFragment(2);
            }
        });

        return view;
    }

    private void generateDataList(List<Supplier> supplierList, View view) {
        recyclerView = view.findViewById(R.id.recyclerview_supplier);
        adapter = new SupplierListAdapter(getContext(), supplierList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}

