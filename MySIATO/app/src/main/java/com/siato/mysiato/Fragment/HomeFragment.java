package com.siato.mysiato.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.siato.mysiato.API;
import com.siato.mysiato.APIResponse;
import com.siato.mysiato.ListAdapter.SparepartsListAdapter;
import com.siato.mysiato.POJO.Spareparts;
import com.siato.mysiato.R;
import com.siato.mysiato.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    public static final String TAG = HomeFragment.class.getSimpleName();
    private View view;
    private API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
    private SparepartsListAdapter adapter = null;
    private RecyclerView recyclerView;
    private EditText etSearch;
    private String sortBy;
    private Spinner sort;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_spareparts, container, false);

        etSearch = view.findViewById(R.id.etSearch);
        sort = view.findViewById(R.id.spinnerSort);

        refreshList();

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                refreshList();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String firstItem = String.valueOf(sort.getSelectedItem());
                if (firstItem.equals("urutkan stok dari terkecil")){
                    sortBy = "stok|asc";
                    refreshList();
                }else if(firstItem.equals("urutkan stok dari terbesar")){
                    sortBy = "stok|desc";
                    refreshList();
                }else if(firstItem.equals("urutkan harga dari terkecil")){
                    sortBy = "harga_jual|asc";
                    refreshList();
                }else if(firstItem.equals("urutkan harga dari terbesar")){
                    sortBy = "harga_jual|desc";
                    refreshList();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    private void refreshList() {
        Call<APIResponse<List<Spareparts>>> call = APIService.listRepos(
                etSearch.getText().toString(),
                sortBy
        );
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
        recyclerView = view.findViewById(R.id.rvList);
        adapter = new SparepartsListAdapter(getContext(), sparepartsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }
}
