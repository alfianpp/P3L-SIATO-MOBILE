package com.siato.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.siato.app.POJO.Kendaraan;

import java.util.List;

public class KendaraanListAdapter extends RecyclerView.Adapter<KendaraanListAdapter.KendaraanViewHolder>{
    private Context context;
    private List<Kendaraan> kendaraanList;

    public KendaraanListAdapter(Context context, List<Kendaraan> kendaraanList) {
        this.context = context;
        this.kendaraanList = kendaraanList;
    }

    public class KendaraanViewHolder extends RecyclerView.ViewHolder {
//        private final TextView tvKendaraanNoPolisi;
//        private final TextView tvKendaraanMerk;

        public KendaraanViewHolder(@NonNull View itemView) {
            super(itemView);
//            tvKendaraanNoPolisi = itemView.findViewById(R.id.tvKendaraanNoPolisi);
//            tvKendaraanMerk = itemView.findViewById(R.id.tvKendaraanMerk);
        }
    }

    @NonNull
    @Override
    public KendaraanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.view_kendaraan, parent, false);
        return new KendaraanListAdapter.KendaraanViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull KendaraanViewHolder holder, int position) {
        final Kendaraan current = kendaraanList.get(position);

//        holder.tvKendaraanNoPolisi.setText(current.getNoPolisi());
//        holder.tvKendaraanMerk.setText(current.getMerk());
    }

    @Override
    public int getItemCount() {
        return kendaraanList.size();
    }

    public Kendaraan getItem(int position) {
        return kendaraanList.get(position);
    }
}
