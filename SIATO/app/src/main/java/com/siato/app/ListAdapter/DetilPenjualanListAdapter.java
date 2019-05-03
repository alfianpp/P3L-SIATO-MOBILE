package com.siato.app.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.siato.app.POJO.DetilPenjualan;
import com.siato.app.R;

import java.util.List;

public class DetilPenjualanListAdapter extends RecyclerView.Adapter<DetilPenjualanListAdapter.DetilPenjualanViewHolder>{
    private Context context;
    private List<DetilPenjualan> detilPenjualanList;
    private List<DetilPenjualan> detilPenjualanListFiltered;

    public DetilPenjualanListAdapter(Context context, List<DetilPenjualan> detilPenjualanList) {
        this.context = context;
        this.detilPenjualanList = detilPenjualanList;
        this.detilPenjualanListFiltered = detilPenjualanList;
    }

    public class DetilPenjualanViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvNamaKonsumen;
        private final TextView tvNoTelepon;
        private final TextView tvTanggal;

        public DetilPenjualanViewHolder(View itemView) {
            super(itemView);

            tvNamaKonsumen=itemView.findViewById(R.id.tvDetilPenjualanKonsumen);
            tvNoTelepon=itemView.findViewById(R.id.tvDetilPenjualanNoTelepon);
            tvTanggal=itemView.findViewById(R.id.tvDetilPenjualanTanggal);

        }
    }


    @NonNull
    @Override
    public DetilPenjualanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.view_penjualan_detil, parent, false);
        return new DetilPenjualanViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DetilPenjualanViewHolder holder, int position) {
        final DetilPenjualan current = detilPenjualanListFiltered.get(position);

        holder.tvNamaKonsumen.setText(current.getPenjualan().getKonsumen().getNama());
        holder.tvNoTelepon.setText(current.getPenjualan().getKonsumen().getNoTlp());
        holder.tvTanggal.setText(current.getPenjualan().getKonsumen().getId());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
