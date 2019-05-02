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
import com.siato.app.ListAdapter.DetilPengadaanBarangListAdapter;
import com.siato.app.ListAdapter.PengadaanBarangListAdapter;
import com.siato.app.MainActivity;
import com.siato.app.POJO.DetilPengadaanBarang;
import com.siato.app.POJO.Partially.PengadaanBarang;
import com.siato.app.R;
import com.siato.app.RecyclerViewClickListener;
import com.siato.app.RecyclerViewTouchListener;
import com.siato.app.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KelolaDetilPengadaanFragment extends Fragment {
    private View view;
    private API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
    private DetilPengadaanBarangListAdapter adapter = null;
    private RecyclerView recyclerView;
    private EditText etSearch;
    private Button btnTambahDetilPengadaanBarang;
    private Integer Id_pengadaan = null;
    private PengadaanBarang pengadaanBarang;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.fragment_kelola_detil_pengadaan, container, false);
        etSearch = view.findViewById(R.id.etSearchDetilPengadaan);
        btnTambahDetilPengadaanBarang = view.findViewById(R.id.btnTambahDetilPengadaanBarang);
        Id_pengadaan = pengadaanBarang.getId();

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

        btnTambahDetilPengadaanBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFragment(8);
            }
        });

       return view;
    }

    private void refreshList() {
        Call<APIResponse<List<DetilPengadaanBarang>>> call = APIService.getAllDetilPengadaanBarang(Id_pengadaan,((MainActivity)getActivity()).logged_in_user.getApiKey());
        call.enqueue(new Callback<APIResponse<List<DetilPengadaanBarang>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<DetilPengadaanBarang>>> call, Response<APIResponse<List<DetilPengadaanBarang>>> response) {
                APIResponse<List<DetilPengadaanBarang>> apiResponse = response.body();

                if(!apiResponse.getError()) {
                    generateDataList(apiResponse.getData(), view);
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<DetilPengadaanBarang>>> call, Throwable t) {
                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(List<DetilPengadaanBarang> detilPengadaanBarangList, View view) {
        recyclerView = view.findViewById(R.id.rvListDetilPengadaanBarang);
        adapter = new DetilPengadaanBarangListAdapter(getContext(), detilPengadaanBarangList);
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
                final DetilPengadaanBarang selected = adapter.getItem(position);
                mBuilder.setTitle("Pilih Aksi")
                        .setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Fragment fragment = new TambahUbahDetilPengadaanFragment();
                                Bundle b = new Bundle();
                                b.putParcelable("detil_pengadaan_barang", selected);
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
                                mBuilder.setTitle("Hapus Detail")
                                        .setMessage("Apakah Anda ingin melanjutkan untuk menghapus detail pengadaan barang ini?")
                                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Call<APIResponse> call = APIService.deletePengadaanBarang(selected.getId(), ((MainActivity)getActivity()).logged_in_user.getApiKey());
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
