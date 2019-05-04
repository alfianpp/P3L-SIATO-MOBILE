package com.siato.app.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.siato.app.API;
import com.siato.app.APIResponse;
import com.siato.app.ListAdapter.DetailPengadaanBarangListAdapter;
import com.siato.app.ListAdapter.DetailPenjualanSparepartsListAdapter;
import com.siato.app.MainActivity;
import com.siato.app.POJO.DetailPengadaanBarang;
import com.siato.app.POJO.DetailPenjualanSpareparts;
import com.siato.app.R;
import com.siato.app.RecyclerViewClickListener;
import com.siato.app.RecyclerViewTouchListener;
import com.siato.app.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KelolaDetailPenjualanSparepartsFragment extends Fragment {
    public static final String TAG = KelolaDetailPenjualanSparepartsFragment.class.getSimpleName();
    private View view;
    private API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
    private DetailPenjualanSparepartsListAdapter adapter = null;
    private RecyclerView recyclerView;
    private FloatingActionButton btnTambah;
    private Integer IDDetailPenjualan = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_kelola, container, false);

        LinearLayout searchLayout = view.findViewById(R.id.searchLayout);
        btnTambah = view.findViewById(R.id.btnTambah);

        searchLayout.setVisibility(View.GONE);

        if(getArguments() != null) {
            IDDetailPenjualan = getArguments().getInt("id_detail_penjualan");
        }

        refreshList();

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new TambahUbahDetailPenjualanSparepartsFragment();
                Bundle b = new Bundle();
                b.putBoolean("ubah_detail_penjualan_spareparts", false);
                b.putInt("id_detail_penjualan", IDDetailPenjualan);
                fragment.setArguments(b);
                ((MainActivity) getActivity()).showFragment(fragment, TambahUbahDetailPenjualanSparepartsFragment.TAG);
            }
        });

       return view;
    }

    private void refreshList() {
        Call<APIResponse<List<DetailPenjualanSpareparts>>> call = APIService.getAllDetailPenjualanSpareparts(IDDetailPenjualan, ((MainActivity)getActivity()).logged_in_user.getApiKey());
        call.enqueue(new Callback<APIResponse<List<DetailPenjualanSpareparts>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<DetailPenjualanSpareparts>>> call, Response<APIResponse<List<DetailPenjualanSpareparts>>> response) {
                APIResponse<List<DetailPenjualanSpareparts>> apiResponse = response.body();

                if(!apiResponse.getError()) {
                    generateDataList(apiResponse.getData(), view);
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<DetailPenjualanSpareparts>>> call, Throwable t) {
                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(List<DetailPenjualanSpareparts> detailPengadaanBarangList, View view) {
        recyclerView = view.findViewById(R.id.rvList);
        adapter = new DetailPenjualanSparepartsListAdapter(getContext(), detailPengadaanBarangList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getContext(), recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(final View view, int position) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                final DetailPenjualanSpareparts selected = adapter.getItem(position);
                mBuilder.setTitle("Pilih Aksi")
                        .setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Fragment fragment = new TambahUbahDetailPenjualanSparepartsFragment();
                                Bundle b = new Bundle();
                                b.putBoolean("ubah_detail_penjualan_spareparts", true);
                                b.putParcelable("detail_penjualan_spareparts", selected);
                                fragment.setArguments(b);
                                ((MainActivity) getActivity()).showFragment(fragment, TambahUbahDetailPenjualanSparepartsFragment.TAG);
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
                                        .setMessage("Apakah Anda ingin melanjutkan untuk menghapus penjualan spareparts ini?")
                                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Call<APIResponse> call = APIService.deleteDetailPenjualanSpareparts(selected.getId(), ((MainActivity)getActivity()).logged_in_user.getApiKey());
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

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).getSupportActionBar()
                .setTitle(R.string.transaksi_detail_pengadaan_barang);
    }
}
