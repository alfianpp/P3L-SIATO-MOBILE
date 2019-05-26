package com.siato.app.Fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.siato.app.API;
import com.siato.app.APIResponse;
import com.siato.app.ListAdapter.KonsumenListAdapter;
import com.siato.app.MainActivity;
import com.siato.app.POJO.Konsumen;
import com.siato.app.R;
import com.siato.app.RecyclerViewClickListener;
import com.siato.app.RecyclerViewTouchListener;
import com.siato.app.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaKonsumenFragment extends Fragment {
    public static final String TAG = KelolaKonsumenFragment.class.getSimpleName();
    private View view;
    private API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
    private KonsumenListAdapter adapter = null;
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
                ((MainActivity)getActivity()).changeFragment(3);
            }
        });

        return view;
    }

    private void refreshList() {
        Call<APIResponse<List<Konsumen>>> call = APIService.getAllKonsumen(((MainActivity)getActivity()).logged_in_user.getApiKey());
        call.enqueue(new Callback<APIResponse<List<Konsumen>>>() {
            @Override
            public void onResponse(Call<APIResponse<List<Konsumen>>> call, Response<APIResponse<List<Konsumen>>> response) {
                APIResponse<List<Konsumen>> apiResponse = response.body();

                if(!apiResponse.getError()) {
                    generateDataList(apiResponse.getData());
                }
            }

            @Override
            public void onFailure(Call<APIResponse<List<Konsumen>>> call, Throwable t) {
                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(List<Konsumen> konsumenList) {
        recyclerView = view.findViewById(R.id.rvList);
        adapter = new KonsumenListAdapter(getContext(), konsumenList);
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
                final Konsumen selected = adapter.getItem(position);
                openEditDeleteVerifyActionDialog(selected);
            }
        }));
    }

    private void openEditDeleteVerifyActionDialog(final Konsumen konsumen) {
        View view = getLayoutInflater().inflate(R.layout.dialog_action_edit_verify_delete,null);

        TextView dialog_title = view.findViewById(R.id.dialog_action_edit_delete_verify_title);
        LinearLayout action_edit = view.findViewById(R.id.dialog_action_edit);
        LinearLayout action_delete = view.findViewById(R.id.dialog_action_delete);
        LinearLayout action_verify = view.findViewById(R.id.dialog_action_verify);

        dialog_title.setText(konsumen.getNama());
        action_verify.setVisibility(View.GONE);

        final Dialog dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(view);

        action_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Fragment fragment = new TambahUbahKonsumenFragment();
                Bundle b = new Bundle();
                b.putParcelable("konsumen", konsumen);
                fragment.setArguments(b);
                ((MainActivity)getActivity()).showFragment(fragment, TambahUbahKonsumenFragment.TAG);
            }
        });

        action_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                AlertDialog confirm = new AlertDialog.Builder(getActivity())
                        .setTitle("Hapus Konsumen")
                        .setMessage("Apakah Anda ingin melanjutkan untuk menghapus konsumen ini?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Call<APIResponse> call = APIService.deleteKonsumen(konsumen.getId(), ((MainActivity)getActivity()).logged_in_user.getApiKey());
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
                .setTitle(R.string.kelola_konsumen);
    }
}
