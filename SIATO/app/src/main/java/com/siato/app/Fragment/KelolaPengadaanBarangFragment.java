package com.siato.app.Fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.siato.app.ListAdapter.PengadaanBarangListAdapter;
import com.siato.app.MainActivity;
import com.siato.app.POJO.PengadaanBarang;
import com.siato.app.R;
import com.siato.app.RecyclerViewClickListener;
import com.siato.app.RecyclerViewTouchListener;
import com.siato.app.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaPengadaanBarangFragment extends Fragment {
    public static final String TAG = KelolaPengadaanBarangFragment.class.getSimpleName();
    private View view;
    private API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
    private PengadaanBarangListAdapter adapter = null;
    private RecyclerView recyclerView;
    private FloatingActionButton btnTambah;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_kelola, container, false);

        LinearLayout searchLayout = view.findViewById(R.id.searchLayout);
        btnTambah = view.findViewById(R.id.btnTambah);

        searchLayout.setVisibility(View.GONE);

        refreshList();

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFragment(5);
            }
        });

        return view;
    }

    private void refreshList() {
        Call<APIResponse<List<PengadaanBarang>>> call = APIService.getAllPengadaanBarang(((MainActivity)getActivity()).logged_in_user.getApiKey());
        call.enqueue(new Callback<APIResponse<List<PengadaanBarang>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<PengadaanBarang>>> call, Response<APIResponse<List<PengadaanBarang>>> response) {
                APIResponse<List<PengadaanBarang>> apiResponse = response.body();

                if(!apiResponse.getError()) {
                    generateDataList(apiResponse.getData(), view);
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<PengadaanBarang>>> call, Throwable t) {
                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(List<PengadaanBarang> pengadaanBarangList, View view) {
        recyclerView = view.findViewById(R.id.rvList);
        adapter = new PengadaanBarangListAdapter(getContext(), pengadaanBarangList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getContext(), recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Fragment fragment = new KelolaDetailPengadaanBarangFragment();
                Bundle b = new Bundle();
                b.putParcelable("pengadaan_barang", adapter.getItem(position));
                fragment.setArguments(b);
                ((MainActivity)getActivity()).showFragment(fragment, KelolaDetailPengadaanBarangFragment.TAG);
            }

            @Override
            public void onLongClick(final View view, int position) {
                final PengadaanBarang selected = adapter.getItem(position);
                openEditDeleteVerifyActionDialog(selected);
            }
        }));
    }

    private void openEditDeleteVerifyActionDialog(final PengadaanBarang pengadaanBarang) {
        View view = getLayoutInflater().inflate(R.layout.dialog_action_edit_verify_delete,null);

        TextView dialog_title = view.findViewById(R.id.dialog_action_edit_delete_verify_title);
        LinearLayout action_edit = view.findViewById(R.id.dialog_action_edit);
        LinearLayout action_delete = view.findViewById(R.id.dialog_action_delete);
        LinearLayout action_verify = view.findViewById(R.id.dialog_action_verify);

        if(!pengadaanBarang.getStatus().equals(3)) {
            dialog_title.setText(R.string.choose_an_action);
        }
        else {
            dialog_title.setText(R.string.no_action_available);
            action_delete.setVisibility(View.GONE);
        }

        if(!pengadaanBarang.getStatus().equals(1)) {
            action_edit.setVisibility(View.GONE);
        }

        action_verify.setVisibility(View.GONE);

        final Dialog dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(view);

        action_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Fragment fragment = new TambahUbahPengadaanBarangFragment();
                Bundle b = new Bundle();
                b.putParcelable("pengadaan_barang", pengadaanBarang);
                fragment.setArguments(b);
                ((MainActivity)getActivity()).showFragment(fragment, TambahUbahPengadaanBarangFragment.TAG);
            }
        });

        action_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                AlertDialog confirm = new AlertDialog.Builder(getActivity())
                        .setTitle("Hapus Pengadaan Barang")
                        .setMessage("Apakah Anda ingin melanjutkan untuk menghapus pengadaan barang ini?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Call<APIResponse> call = APIService.deletePengadaanBarang(pengadaanBarang.getId(), ((MainActivity)getActivity()).logged_in_user.getApiKey());
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
                .setTitle("Pengadaan Barang");
    }
}
