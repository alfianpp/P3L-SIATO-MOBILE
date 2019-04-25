package com.siato.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.siato.app.API;
import com.siato.app.APIResponse;
import com.siato.app.MainActivity;
import com.siato.app.POJO.Pegawai;
import com.siato.app.R;
import com.siato.app.RetrofitClientInstance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    private TextInputEditText etUsername;
    private TextInputEditText etPassword;
    private Button btnLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etUsername = view.findViewById(R.id.etLoginUsername);
        etPassword = view.findViewById(R.id.etLoginPassword);
        btnLogin = view.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                API service = RetrofitClientInstance.getRetrofitInstance().create(API.class);
                Call<APIResponse<Pegawai>> call = service.login(username, password);
                call.enqueue(new Callback<APIResponse<Pegawai>>() {
                    @Override
                    public void onResponse(Call<APIResponse<Pegawai>> call, Response<APIResponse<Pegawai>> response) {
                        APIResponse<Pegawai> apiResponse = response.body();

                        if(!apiResponse.getError()) {
                            ((MainActivity)getActivity()).logged_in_user = apiResponse.getData();
                            ((MainActivity)getActivity()).logged_inDrawer(true);
                            ((MainActivity)getActivity()).backToDashboard();
                        }
                        Toast.makeText(getContext(), apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<APIResponse<Pegawai>> call, Throwable t) {
                        Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }
}
