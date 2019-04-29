package com.siato.app.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.siato.app.API;
import com.siato.app.APIResponse;
import com.siato.app.ListAdapter.KendaraanListAdapter;
import com.siato.app.ListAdapter.PenjualanListAdapter;
import com.siato.app.MainActivity;
import com.siato.app.POJO.Kendaraan;
import com.siato.app.POJO.Penjualan;
import com.siato.app.R;
import com.siato.app.RecyclerViewClickListener;
import com.siato.app.RecyclerViewTouchListener;
import com.siato.app.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaPenjualanFragment extends Fragment {
    private View view;
    private API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
    private PenjualanListAdapter adapter = null;
    private RecyclerView recyclerView;
    private EditText etSearch;
    private Button btnTambahPenjualan;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     view = inflater.inflate(R.layout.fragment_kelola_penjualan, container, false);
        etSearch = view.findViewById(R.id.etSearchPenjualan);
        btnTambahPenjualan = view.findViewById(R.id.btnTambahPenjualan);
        refreshList();

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnTambahPenjualan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFragment(4);
            }
        });
        return view;
    }

    private void refreshList() {
        Call<APIResponse<List<Penjualan>>> call = APIService.getAllPenjualan(((MainActivity)getActivity()).logged_in_user.getApiKey());
        call.enqueue(new Callback<APIResponse<List<Penjualan>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<Penjualan>>> call, Response<APIResponse<List<Penjualan>>> response) {
                APIResponse<List<Penjualan>> apiResponse = response.body();

                if(!apiResponse.getError()) {
                    generateDataList(apiResponse.getData());
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<Penjualan>>> call, Throwable t) {
                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(List<Penjualan> penjualanList) {
        recyclerView = view.findViewById(R.id.rvListPenjualan);
        adapter = new PenjualanListAdapter(getContext(), penjualanList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getContext(), recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(final View view, int position) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                final Penjualan selected = adapter.getItem(position);
                mBuilder.setTitle("Pilih Aksi")
                        .setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Fragment fragment = new TamabahUbahPenjualanFragment();
                                Bundle b = new Bundle();
                                b.putParcelable("penjualan", selected);
                                fragment.setArguments(b);
                                ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                            }
                        })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setNeutralButton("Hapus", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                                mBuilder.setTitle("Hapus Penjualan")
                                        .setMessage("Apakah Anda ingin melanjutkan untuk menghapus penjualan ini?")
                                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Call<APIResponse> call = APIService.deletePenjualan(selected.getId(), ((MainActivity)getActivity()).logged_in_user.getApiKey());
                                                call.enqueue(new Callback<APIResponse>() {
                                                    @Override
                                                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                                                        APIResponse apiResponse = response.body();

                                                        if(!apiResponse.getError()) {
                                                            refreshList();
                                                        }

                                                        Toast.makeText(getContext(), apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }

                                                    @Override
                                                    public void onFailure(Call<APIResponse> call, Throwable t) {
                                                        Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        })
                                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        }).create().show();
                            }
                        }).create().show();
            }
        }));
    }
}
