package com.siato.app.Fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.siato.app.API;
import com.siato.app.APIResponse;
import com.siato.app.ListAdapter.DetailPenjualanListAdapter;
import com.siato.app.MainActivity;
import com.siato.app.POJO.DetailPenjualan;
import com.siato.app.POJO.Penjualan;
import com.siato.app.R;
import com.siato.app.RecyclerViewClickListener;
import com.siato.app.RecyclerViewTouchListener;
import com.siato.app.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KelolaDetailPenjualanFragment extends Fragment {
    public static final String TAG = KelolaDetailPenjualanFragment.class.getSimpleName();
    private View view;
    private API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
    private DetailPenjualanListAdapter adapter = null;
    private RecyclerView recyclerView;
    private FloatingActionButton btnTambah;

    private Penjualan selectedPenjualan = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(getArguments() != null && getArguments().getParcelable("penjualan") != null) {
            selectedPenjualan = getArguments().getParcelable("penjualan");
        }

        view = inflater.inflate(R.layout.fragment_kelola, container, false);

        LinearLayout searchLayout = view.findViewById(R.id.searchLayout);
        btnTambah = view.findViewById(R.id.btnTambah);

        searchLayout.setVisibility(View.GONE);

        if(!selectedPenjualan.getStatus().equals(1)) {
            btnTambah.hide();
        }

        refreshList();

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new TambahUbahDetailPenjualanFragment();
                Bundle b = new Bundle();
                b.putBoolean("ubah_detail_penjualan", false);
                b.putInt("id_penjualan", selectedPenjualan.getId());
                b.putInt("id_konsumen", selectedPenjualan.getKonsumen().getId());
                fragment.setArguments(b);
                ((MainActivity)getActivity()).showFragment(fragment, TambahUbahDetailPenjualanFragment.TAG);
            }
        });

       return view;
    }

    private void refreshList() {
        Call<APIResponse<List<DetailPenjualan>>> call = APIService.getAllDetailPenjualan(selectedPenjualan.getId(), ((MainActivity)getActivity()).logged_in_user.getApiKey());
        call.enqueue(new Callback<APIResponse<List<DetailPenjualan>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<DetailPenjualan>>> call, Response<APIResponse<List<DetailPenjualan>>> response) {
                APIResponse<List<DetailPenjualan>> apiResponse = response.body();

                if(!apiResponse.getError()) {
                    generateDataList(apiResponse.getData(), view);
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<DetailPenjualan>>> call, Throwable t) {
                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(List<DetailPenjualan> detailPenjualan, View view) {
        recyclerView = view.findViewById(R.id.rvList);
        adapter = new DetailPenjualanListAdapter(getContext(), detailPenjualan);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getContext(), recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                final DetailPenjualan selected = adapter.getItem(position);
                openViewDetailPenjualanActionDialog(selected);
            }

            @Override
            public void onLongClick(final View view, int position) {
                final DetailPenjualan selected = adapter.getItem(position);
                openEditDeleteVerifyActionDialog(selected);
            }
        }));
    }

    private void openEditDeleteVerifyActionDialog(final DetailPenjualan detailPenjualan) {
        View view = getLayoutInflater().inflate(R.layout.dialog_action_edit_verify_delete,null);

        TextView dialog_title = view.findViewById(R.id.dialog_action_edit_delete_verify_title);
        LinearLayout action_edit = view.findViewById(R.id.dialog_action_edit);
        LinearLayout action_delete = view.findViewById(R.id.dialog_action_delete);
        LinearLayout action_verify = view.findViewById(R.id.dialog_action_verify);

        if(selectedPenjualan.getStatus().equals(1)) {
            dialog_title.setText(R.string.choose_an_action);
        }
        else {
            dialog_title.setText(R.string.no_action_available);
            action_edit.setVisibility(View.GONE);
            action_delete.setVisibility(View.GONE);
        }

        action_verify.setVisibility(View.GONE);

        final Dialog dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(view);

        action_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Fragment fragment = new TambahUbahDetailPenjualanFragment();
                Bundle b = new Bundle();
                b.putBoolean("ubah_detail_penjualan", true);
                b.putParcelable("detail_penjualan", detailPenjualan);
                b.putInt("id_konsumen", selectedPenjualan.getKonsumen().getId());
                fragment.setArguments(b);
                ((MainActivity) getActivity()).showFragment(fragment, TambahUbahDetailPenjualanFragment.TAG);
            }
        });

        action_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                AlertDialog confirm = new AlertDialog.Builder(getActivity())
                        .setTitle("Hapus Detail Penjualan")
                        .setMessage("Apakah Anda ingin melanjutkan untuk menghapus detail penjualan ini?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Call<APIResponse> call = APIService.deleteDetailPenjualan(detailPenjualan.getId(), ((MainActivity)getActivity()).logged_in_user.getApiKey());
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

    private void openViewDetailPenjualanActionDialog(final DetailPenjualan detailPenjualan) {
        View view = getLayoutInflater().inflate(R.layout.dialog_action_view_detail_penjualan,null);

        TextView dialog_title = view.findViewById(R.id.dialog_action_view_detail_penjualan_title);
        LinearLayout action_view_spareparts = view.findViewById(R.id.dialog_action_view_spareparts);
        LinearLayout action_view_jasaservice = view.findViewById(R.id.dialog_action_view_jasaservice);

        dialog_title.setText("Lihat Detail Penjualan");

        if(selectedPenjualan.getJenis().equals("SV")) {
            action_view_spareparts.setVisibility(View.GONE);
        }

        final Dialog dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(view);

        action_view_spareparts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Fragment fragment = new KelolaDetailPenjualanSparepartsFragment();
                Bundle b = new Bundle();
                b.putParcelable("penjualan", selectedPenjualan);
                b.putInt("id_detail_penjualan", detailPenjualan.getId());
                fragment.setArguments(b);
                ((MainActivity)getActivity()).showFragment(fragment, KelolaDetailPenjualanSparepartsFragment.TAG);
            }
        });

        action_view_jasaservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Fragment fragment = new KelolaDetailPenjualanJasaServiceFragment();
                Bundle b = new Bundle();
                b.putParcelable("penjualan", selectedPenjualan);
                b.putInt("id_detail_penjualan", detailPenjualan.getId());
                fragment.setArguments(b);
                ((MainActivity)getActivity()).showFragment(fragment, KelolaDetailPenjualanJasaServiceFragment.TAG);
            }
        });

        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).getSupportActionBar()
                .setTitle("Daftar Kendaraan");
    }
}
