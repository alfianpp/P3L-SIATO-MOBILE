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
import com.siato.app.ListAdapter.PenjualanListAdapter;
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

public class KelolaPenjualanFragment extends Fragment {
    public static final String TAG = KelolaPenjualanFragment.class.getSimpleName();
    private View view;
    private API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
    private PenjualanListAdapter adapter = null;
    private RecyclerView recyclerView;
    private FloatingActionButton btnTambah;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_kelola, container, false);

        LinearLayout searchLayout = view.findViewById(R.id.searchLayout);
        btnTambah = view.findViewById(R.id.btnTambah);

        searchLayout.setVisibility(View.GONE);

        refreshList();

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFragment(6);
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
        recyclerView = view.findViewById(R.id.rvList);
        adapter = new PenjualanListAdapter(getContext(), penjualanList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getContext(), recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(final View view, int position) {
                final Penjualan selected = adapter.getItem(position);

                if(selected.getJenis().equals("SP")) {
                    Call<APIResponse<List<DetailPenjualan>>> call = APIService.getAllDetailPenjualan(selected.getId(), ((MainActivity)getActivity()).logged_in_user.getApiKey());
                    call.enqueue(new Callback<APIResponse<List<DetailPenjualan>>>() {
                        @Override
                        public void onResponse(Call<APIResponse<List<DetailPenjualan>>> call, Response<APIResponse<List<DetailPenjualan>>> response) {
                            APIResponse<List<DetailPenjualan>> apiResponse = response.body();

                            if(!apiResponse.getError()) {
                                Fragment fragment = new KelolaDetailPenjualanSparepartsFragment();
                                Bundle b = new Bundle();
                                b.putParcelable("penjualan", selected);
                                b.putInt("id_detail_penjualan", apiResponse.getData().get(0).getId());
                                fragment.setArguments(b);
                                ((MainActivity)getActivity()).showFragment(fragment, KelolaDetailPenjualanSparepartsFragment.TAG);
                            }
                        }

                        @Override
                        public void onFailure(Call<APIResponse<List<DetailPenjualan>>> call, Throwable t) {
                            Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else if(selected.getJenis().equals("SV") || selected.getJenis().equals("SS")) {
                    Fragment fragment = new KelolaDetailPenjualanFragment();
                    Bundle b = new Bundle();
                    b.putParcelable("penjualan", adapter.getItem(position));
                    fragment.setArguments(b);
                    ((MainActivity)getActivity()).showFragment(fragment, KelolaDetailPenjualanFragment.TAG);
                }
            }

            @Override
            public void onLongClick(final View view, int position) {
                final Penjualan selected = adapter.getItem(position);
                openEditDeleteVerifyActionDialog(selected);
            }
        }));
    }

    private void openEditDeleteVerifyActionDialog(final Penjualan penjualan) {
        View view = getLayoutInflater().inflate(R.layout.dialog_action_edit_verify_delete,null);

        TextView dialog_title = view.findViewById(R.id.dialog_action_edit_delete_verify_title);
        LinearLayout action_edit = view.findViewById(R.id.dialog_action_edit);
        LinearLayout action_delete = view.findViewById(R.id.dialog_action_delete);
        LinearLayout action_verify = view.findViewById(R.id.dialog_action_verify);

        if(penjualan.getStatus().equals(1)) {
            dialog_title.setText(R.string.choose_an_action);
        }
        else {
            dialog_title.setText(R.string.no_action_available);
            action_edit.setVisibility(View.GONE);
            action_verify.setVisibility(View.GONE);
            action_delete.setVisibility(View.GONE);
        }

        final Dialog dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(view);

        action_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Fragment fragment = new TambahUbahPenjualanFragment();
                Bundle b = new Bundle();
                b.putParcelable("penjualan", penjualan);
                fragment.setArguments(b);
                ((MainActivity)getActivity()).showFragment(fragment, TambahUbahPenjualanFragment.TAG);
            }
        });

        action_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Call<APIResponse> call = APIService.verifikasiPenjualan(penjualan.getId(), "2", ((MainActivity) getActivity()).logged_in_user.getApiKey());
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
        });

        action_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                AlertDialog confirm = new AlertDialog.Builder(getActivity())
                        .setTitle("Hapus Penjualan")
                        .setMessage("Apakah Anda ingin melanjutkan untuk menghapus penjualan ini?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Call<APIResponse> call = APIService.deletePenjualan(penjualan.getId(), ((MainActivity)getActivity()).logged_in_user.getApiKey());
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
                .setTitle("Penjualan");
    }
}
