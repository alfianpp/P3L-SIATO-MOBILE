package com.siato.mysiato.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.siato.mysiato.API;
import com.siato.mysiato.APIResponse;
import com.siato.mysiato.MainActivity;
import com.siato.mysiato.R;
import com.siato.mysiato.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    public static final String TAG = LoginFragment.class.getSimpleName();
    private TextInputEditText etNoPolisi;
    private TextInputEditText etNoTelepon;
    private Button login;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etNoPolisi = view.findViewById(R.id.etLogin_NomorPolisi);
        etNoTelepon = view.findViewById(R.id.etLogin_NomorTelepon);
        login = view.findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nomorPolisi = etNoPolisi.getText().toString();
                final String nomorTelepon = etNoTelepon.getText().toString();

                API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
                Call<APIResponse> call = APIService.login(
                        nomorPolisi,nomorTelepon
                );
                call.enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        APIResponse apiResponse = response.body();
                        Fragment fragment = new KelolaPenjualanFragment();
                        Bundle b = new Bundle();
                        b.putString("nomor_polisi",etNoPolisi.getText().toString());
                        b.putString("nomor_telepon",etNoTelepon.getText().toString());
                        fragment.setArguments(b);
                        ((MainActivity)getActivity()).showFragment(fragment, KelolaPenjualanFragment.TAG);

                        Toast.makeText(getContext(), apiResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        if(!apiResponse.getError()) {

                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }
}
