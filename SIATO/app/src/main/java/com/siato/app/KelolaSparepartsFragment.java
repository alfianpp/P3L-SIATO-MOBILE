package com.siato.app;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaSparepartsFragment extends Fragment {
    private View view;
    private API service = RetrofitClientInstance.getRetrofitInstance().create(API.class);
    private SparepartsListAdapter adapter = null;
    private RecyclerView recyclerView;
    private EditText etSearch;
    private Button btnTambahUbahSpareparts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_kelola_spareparts, container, false);

        etSearch = view.findViewById(R.id.etSearchSpareparts);
        btnTambahUbahSpareparts = view.findViewById(R.id.btnTambahUbahSpareparts);

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

        btnTambahUbahSpareparts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFragment(1);
            }
        });

        return view;
    }

    private void refreshList() {
        Call<APIResponse<List<Spareparts>>> call = service.getAllSpareparts(((MainActivity)getActivity()).logged_in_user.getApiKey());
        call.enqueue(new Callback<APIResponse<List<Spareparts>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<Spareparts>>> call, Response<APIResponse<List<Spareparts>>> response) {
                APIResponse<List<Spareparts>> apiResponse = response.body();

                if(!apiResponse.getError()) {
                    generateDataList(apiResponse.getData());
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<Spareparts>>> call, Throwable t) {
                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(List<Spareparts> sparepartsList) {
        recyclerView = view.findViewById(R.id.recyclerview_spareparts);
        adapter = new SparepartsListAdapter(getContext(), sparepartsList);
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
                final Spareparts selected = adapter.getItem(position);
                mBuilder.setTitle("Pilih Aksi")
                        .setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Fragment fragment = new TambahUbahSparepartsFragment();
                                Bundle b = new Bundle();
                                b.putParcelable("tes", selected);
                                fragment.setArguments(b);
                                String title = "Ubah Data Spareparts";
                                ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                                ((MainActivity)getActivity()).getSupportActionBar().setTitle(title);
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
                                mBuilder.setTitle("Hapus Spareparts")
                                        .setMessage("Apakah Anda ingin melanjutkan untuk menghapus spareparts ini?")
                                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Call<APIResponse> call = service.deleteSpareparts(selected.getKode(), ((MainActivity)getActivity()).logged_in_user.getApiKey());
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
