package com.siato.app.Fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.siato.app.API;
import com.siato.app.APIResponse;
import com.siato.app.ListAdapter.DetailPengadaanBarangListAdapter;
import com.siato.app.MainActivity;
import com.siato.app.POJO.DetailPengadaanBarang;
import com.siato.app.POJO.PengadaanBarang;
import com.siato.app.R;
import com.siato.app.RecyclerViewClickListener;
import com.siato.app.RecyclerViewTouchListener;
import com.siato.app.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KelolaDetailPengadaanBarangFragment extends Fragment {
    public static final String TAG = KelolaDetailPengadaanBarangFragment.class.getSimpleName();
    private View view;
    private API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
    private DetailPengadaanBarangListAdapter adapter = null;
    private RecyclerView recyclerView;
    private FloatingActionButton btnTambah;

    private PengadaanBarang selectedPengadaanBarang = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(getArguments() != null && getArguments().getParcelable("pengadaan_barang") != null) {
            selectedPengadaanBarang = getArguments().getParcelable("pengadaan_barang");
        }

        view = inflater.inflate(R.layout.fragment_kelola, container, false);

        LinearLayout searchLayout = view.findViewById(R.id.searchLayout);
        btnTambah = view.findViewById(R.id.btnTambah);

        searchLayout.setVisibility(View.GONE);

        if(!selectedPengadaanBarang.getStatus().equals(1)) {
            btnTambah.hide();
        }

        refreshList();

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new TambahUbahDetailPengadaanBarangFragment();
                Bundle b = new Bundle();
                b.putBoolean("ubah_detail_pengadaan_barang", false);
                b.putInt("id_pengadaan_barang", selectedPengadaanBarang.getId());
                fragment.setArguments(b);
                ((MainActivity)getActivity()).showFragment(fragment, TambahUbahDetailPengadaanBarangFragment.TAG);
            }
        });

       return view;
    }

    private void refreshList() {
        Call<APIResponse<List<DetailPengadaanBarang>>> call = APIService.getAllDetailPengadaanBarang(selectedPengadaanBarang.getId(), ((MainActivity)getActivity()).logged_in_user.getApiKey());
        call.enqueue(new Callback<APIResponse<List<DetailPengadaanBarang>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<DetailPengadaanBarang>>> call, Response<APIResponse<List<DetailPengadaanBarang>>> response) {
                APIResponse<List<DetailPengadaanBarang>> apiResponse = response.body();

                if(!apiResponse.getError()) {
                    generateDataList(apiResponse.getData(), view);
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<DetailPengadaanBarang>>> call, Throwable t) {
                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(List<DetailPengadaanBarang> detailPengadaanBarangList, View view) {
        recyclerView = view.findViewById(R.id.rvList);
        adapter = new DetailPengadaanBarangListAdapter(getContext(), detailPengadaanBarangList);
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
                final DetailPengadaanBarang selected = adapter.getItem(position);
                openEditDeleteVerifyActionDialog(selected);
            }
        }));
    }

    private void openEditDeleteVerifyActionDialog(final DetailPengadaanBarang detailPengadaanBarang) {
        View view = getLayoutInflater().inflate(R.layout.dialog_action_edit_verify_delete,null);

        TextView dialog_title = view.findViewById(R.id.dialog_action_edit_delete_verify_title);
        LinearLayout action_edit = view.findViewById(R.id.dialog_action_edit);
        LinearLayout action_delete = view.findViewById(R.id.dialog_action_delete);
        LinearLayout action_verify = view.findViewById(R.id.dialog_action_verify);

        if((!selectedPengadaanBarang.getStatus().equals(3)) && (detailPengadaanBarang.getJumlahDatang() == null && detailPengadaanBarang.getHarga() == null)) {
            dialog_title.setText(R.string.choose_an_action);
        }
        else {
            dialog_title.setText(R.string.no_action_available);
        }

        if(!selectedPengadaanBarang.getStatus().equals(1)) {
            action_edit.setVisibility(View.GONE);
            action_delete.setVisibility(View.GONE);
        }

        if((!selectedPengadaanBarang.getStatus().equals(2)) || (detailPengadaanBarang.getJumlahDatang() != null && detailPengadaanBarang.getHarga() != null)) {
            action_verify.setVisibility(View.GONE);
        }

        final Dialog dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(view);

        action_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Fragment fragment = new TambahUbahDetailPengadaanBarangFragment();
                Bundle b = new Bundle();
                b.putBoolean("ubah_detail_pengadaan_barang", true);
                b.putParcelable("detail_pengadaan_barang", detailPengadaanBarang);
                fragment.setArguments(b);
                ((MainActivity) getActivity()).showFragment(fragment, TambahUbahDetailPengadaanBarangFragment.TAG);
            }
        });

        action_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Fragment fragment = new VerifikasiPengadaanBarangFragment();
                Bundle b = new Bundle();
                b.putInt("id_detail_pengadaan_barang", detailPengadaanBarang.getId());
                fragment.setArguments(b);
                ((MainActivity) getActivity()).showFragment(fragment, VerifikasiPengadaanBarangFragment.TAG);
            }
        });

        action_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                AlertDialog confirm = new AlertDialog.Builder(getActivity())
                        .setTitle("Hapus Detail Pengadaan Barang")
                        .setMessage("Apakah Anda ingin melanjutkan untuk menghapus detail pengadaan barang ini?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Call<APIResponse> call = APIService.deleteDetailPengadaanBarang(detailPengadaanBarang.getId(), ((MainActivity)getActivity()).logged_in_user.getApiKey());
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
                        .setNegativeButton("Tidak", null)
                        .show();
            }
        });

        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).getSupportActionBar()
                .setTitle("Detail Pengadaan Barang");
    }
}
