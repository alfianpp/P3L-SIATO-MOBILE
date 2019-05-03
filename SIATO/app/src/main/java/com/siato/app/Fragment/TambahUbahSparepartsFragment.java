package com.siato.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.siato.app.API;
import com.siato.app.APIResponse;
import com.siato.app.MainActivity;
import com.siato.app.POJO.Spareparts;
import com.siato.app.R;
import com.siato.app.RetrofitClientInstance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahUbahSparepartsFragment extends Fragment {
    public static final String TAG = TambahUbahSparepartsFragment.class.getSimpleName();
    private String ACTION = "TAMBAH";
    private TextInputEditText etKode;
    private TextInputEditText etNama;
    private TextInputEditText etMerk;
    private TextInputEditText etTipe;
    private String kodePeletakan;
    private Spinner spPeletakanLetak;
    private Spinner spPeletakanRuang;
    private EditText etPeletakanNomorUrut;
    private TextInputEditText etHargaJual;
    private TextInputEditText etHargaBeli;
    private TextInputEditText etStok;
    private TextInputEditText etStokMinimal;
    private Button btnTambahUbah;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah_ubah_spareparts, container, false);

        etKode = view.findViewById(R.id.etSparepartsKode);
        etNama = view.findViewById(R.id.etSparepartsNama);
        etMerk = view.findViewById(R.id.etSparepartsMerk);
        etTipe = view.findViewById(R.id.etSparepartsTipe);
        spPeletakanLetak = view.findViewById(R.id.spSparepartsPeletakanLetak);
        spPeletakanRuang = view.findViewById(R.id.spSparepartsPeletakanRuang);
        etPeletakanNomorUrut = view.findViewById(R.id.etSparepartsPeletakanNomorUrut);
        etHargaJual = view.findViewById(R.id.etSparepartsHargaJual);
        etHargaBeli = view.findViewById(R.id.etSparepartsHargaBeli);
        etStok = view.findViewById(R.id.etSparepartsStok);
        etStokMinimal = view.findViewById(R.id.etSparepartsStokMinimal);
        btnTambahUbah = view.findViewById(R.id.btnTambahUbahSpareparts);

        if(getArguments() != null && getArguments().getParcelable("spareparts") != null) {
            ACTION = "UBAH";
            Spareparts spareparts = getArguments().getParcelable("spareparts");
            etKode.setEnabled(false);
            etKode.setText(spareparts.getKode());
            etNama.setText(spareparts.getNama());
            etMerk.setText(spareparts.getMerk());
            etTipe.setText(spareparts.getTipe());
            kodePeletakan = spareparts.getKodePeletakan();
            etHargaJual.setText(String.valueOf(spareparts.getHargaJual()).replace(".0", ""));
            etHargaBeli.setText(String.valueOf(spareparts.getHargaBeli()).replace(".0", ""));
            etStok.setEnabled(false);
            etStok.setText(String.valueOf(spareparts.getStok()));
            etStokMinimal.setText(String.valueOf(spareparts.getStokMinimal()));
            btnTambahUbah.setText("Ubah");
        }

        final List<String> letakID = new ArrayList<>();
        letakID.add("DPN");
        letakID.add("TGH");
        letakID.add("BLK");

        List<String> letakValue = new ArrayList<>();
        letakValue.add("Depan");
        letakValue.add("Tengah");
        letakValue.add("Belakang");

        ArrayAdapter<String> letakAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, letakValue);
        letakAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPeletakanLetak.setAdapter(letakAdapter);

        final List<String> ruangID = new ArrayList<>();
        ruangID.add("KACA");
        ruangID.add("DUS");
        ruangID.add("BAN");
        ruangID.add("KAYU");

        List<String> ruangValue = new ArrayList<>();
        ruangValue.add("Kaca");
        ruangValue.add("Dus");
        ruangValue.add("Ban");
        ruangValue.add("Kayu");

        ArrayAdapter<String> ruangAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, ruangValue);
        ruangAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPeletakanRuang.setAdapter(ruangAdapter);

        if(ACTION.equals("UBAH")) {
            String[] peletakan = kodePeletakan.split("-", 0);
            spPeletakanLetak.setSelection(letakID.indexOf(peletakan[0]));
            spPeletakanRuang.setSelection(ruangID.indexOf(peletakan[1]));
            etPeletakanNomorUrut.setText(peletakan[2]);
        }

        btnTambahUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API APIService = RetrofitClientInstance.getRetrofitInstance().create(API.class);
                switch (ACTION){
                    case "TAMBAH":
                        Call<APIResponse> create = APIService.createSpareparts(
                                etKode.getText().toString(),
                                etNama.getText().toString(),
                                etMerk.getText().toString(),
                                etTipe.getText().toString(),
                                letakID.get(spPeletakanLetak.getSelectedItemPosition()) + "-" + ruangID.get(spPeletakanRuang.getSelectedItemPosition()) + "-" + etPeletakanNomorUrut.getText().toString(),
                                etHargaJual.getText().toString(),
                                etHargaBeli.getText().toString(),
                                etStok.getText().toString(),
                                etStokMinimal.getText().toString(),
                                ((MainActivity)getActivity()).logged_in_user.getApiKey()
                        );
                        create.enqueue(new Callback<APIResponse>() {
                            @Override
                            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                                APIResponse apiResponse = response.body();

                                if(!apiResponse.getError()) {
                                    ((MainActivity)getActivity()).changeFragment(R.id.nav_data_spareparts);
                                }

                                Toast.makeText(getContext(), apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<APIResponse> call, Throwable t) {
                                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;

                    case "UBAH":
                        Call<APIResponse> update = APIService.updateSpareparts(
                                etKode.getText().toString(),
                                etNama.getText().toString(),
                                etMerk.getText().toString(),
                                etTipe.getText().toString(),
                                letakID.get(spPeletakanLetak.getSelectedItemPosition()) + "-" + ruangID.get(spPeletakanRuang.getSelectedItemPosition()) + "-" + etPeletakanNomorUrut.getText().toString(),
                                etHargaJual.getText().toString(),
                                etHargaBeli.getText().toString(),
                                etStokMinimal.getText().toString(),
                                ((MainActivity)getActivity()).logged_in_user.getApiKey()
                        );
                        update.enqueue(new Callback<APIResponse>() {
                            @Override
                            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                                APIResponse apiResponse = response.body();

                                if(!apiResponse.getError()) {
                                    ((MainActivity)getActivity()).changeFragment(R.id.nav_data_spareparts);
                                }

                                Toast.makeText(getContext(), apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<APIResponse> call, Throwable t) {
                                Toast.makeText(getContext(), "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).getSupportActionBar()
                .setTitle(ACTION.substring(0, 1) + ACTION.substring(1).toLowerCase() + " " + getResources().getString(R.string.data_spareparts));
    }
}
