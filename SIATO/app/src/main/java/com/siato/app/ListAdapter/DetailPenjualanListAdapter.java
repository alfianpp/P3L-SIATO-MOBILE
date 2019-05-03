package com.siato.app.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.siato.app.POJO.DetailPenjualan;
import com.siato.app.R;

import java.util.List;

public class DetailPenjualanListAdapter extends RecyclerView.Adapter<DetailPenjualanListAdapter.DetilPenjualanViewHolder>{
    private Context context;
    private List<DetailPenjualan> detailPenjualanList;

    public DetailPenjualanListAdapter(Context context, List<DetailPenjualan> detailPenjualanList) {
        this.context = context;
        this.detailPenjualanList = detailPenjualanList;
    }

    public class DetilPenjualanViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvKendaraan;
        private final TextView tvMontir;

        public DetilPenjualanViewHolder(View itemView) {
            super(itemView);

            tvKendaraan = itemView.findViewById(R.id.tvDetailPenjualanKendaraan);
            tvMontir = itemView.findViewById(R.id.tvDetailPenjualanMontir);
        }
    }

    @NonNull
    @Override
    public DetilPenjualanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.view_detail_penjualan, parent, false);
        return new DetilPenjualanViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DetilPenjualanViewHolder holder, int position) {
        final DetailPenjualan current = detailPenjualanList.get(position);

        holder.tvKendaraan.setText(current.getKendaraan().getNomorPolisi());
        holder.tvMontir.setText(current.getMontir().getNama());
    }

    @Override
    public int getItemCount() {
        return detailPenjualanList.size();
    }

    public DetailPenjualan getItem(int position) {
        return detailPenjualanList.get(position);
    }
}
