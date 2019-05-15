package com.siato.mysiato.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.siato.mysiato.API;
import com.siato.mysiato.APIResponse;
import com.siato.mysiato.ListAdapter.PenjualanListAdapter;
import com.siato.mysiato.POJO.Penjualan;
import com.siato.mysiato.R;
import com.siato.mysiato.RetrofitClientInstance;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_kelola, container, false);

        LinearLayout searchLayout = view.findViewById(R.id.searchLayout);

        searchLayout.setVisibility(View.GONE);

        refreshList();

        return view;
    }

    private void refreshList() {
        Call<APIResponse<List<Penjualan>>> call = APIService.index(String.valueOf(getArguments().getString("nomor_polisi")));
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
    }
}
