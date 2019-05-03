package com.siato.app.Fragment;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.siato.app.API;
import com.siato.app.APIResponse;
import com.siato.app.ListAdapter.KendaraanListAdapter;
import com.siato.app.MainActivity;
import com.siato.app.POJO.Kendaraan;
import com.siato.app.R;
import com.siato.app.RecyclerViewClickListener;
import com.siato.app.RecyclerViewTouchListener;
import com.siato.app.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaKendaraanFragment extends Fragment{
    public static final String TAG = KelolaKendaraanFragment.class.getSimpleName();
    private View view;
    private API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
    private KendaraanListAdapter adapter = null;
    private RecyclerView recyclerView;
    private EditText etSearch;
    private FloatingActionButton btnTambah;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_kelola, container, false);

        etSearch = view.findViewById(R.id.etSearch);
        btnTambah = view.findViewById(R.id.btnTambah);

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

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFragment(4);
            }
        });

        return view;
    }
    private void refreshList() {
        Call<APIResponse<List<Kendaraan>>> call = APIService.getAllKendaraan(((MainActivity)getActivity()).logged_in_user.getApiKey());
        call.enqueue(new Callback<APIResponse<List<Kendaraan>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<Kendaraan>>> call, Response<APIResponse<List<Kendaraan>>> response) {
                APIResponse<List<Kendaraan>> apiResponse = response.body();

                if(!apiResponse.getError()) {
                    generateDataList(apiResponse.getData());
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<Kendaraan>>> call, Throwable t) {
                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void generateDataList(List<Kendaraan> kendaraanList) {
        recyclerView = view.findViewById(R.id.rvList);
        adapter = new KendaraanListAdapter(getContext(), kendaraanList);
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
                final Kendaraan selected = adapter.getItem(position);
                mBuilder.setTitle("Pilih Aksi")
                        .setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Fragment fragment = new TambahUbahKendaraanFragment();
                                Bundle b = new Bundle();
                                b.putParcelable("kendaraan", selected);
                                fragment.setArguments(b);
                                ((MainActivity)getActivity()).showFragment(fragment, TambahUbahKendaraanFragment.TAG);
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
                                mBuilder.setTitle("Hapus Kendaraan")
                                        .setMessage("Apakah Anda ingin melanjutkan untuk menghapus kendaraan ini?")
                                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Call<APIResponse> call = APIService.deleteKendaraan(selected.getNomorPolisi(), ((MainActivity)getActivity()).logged_in_user.getApiKey());
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
                .setTitle(R.string.kelola_kendaraan);
    }
}