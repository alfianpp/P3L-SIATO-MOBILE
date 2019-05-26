package com.siato.app.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.siato.app.POJO.Penjualan;
import com.siato.app.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PenjualanListAdapter extends RecyclerView.Adapter<PenjualanListAdapter.PenjualanViewHolder> {
    private Context context;
    private List<Penjualan> penjualanList;
    private List<Penjualan> penjualanListFiltered;

    public PenjualanListAdapter(Context context,List<Penjualan> penjualanList) {
        this.context = context;
        this.penjualanList = penjualanList;
        this.penjualanListFiltered = penjualanList;
    }

    public class PenjualanViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvNomorTransaksiPenjualan;
        private final TextView tvNamaKonsumenPenjualan;
        private final TextView tvNomorTeleponKonsumenPenjualan;
        private final TextView tvTanggalTransaksiPenjualan;
        private final TextView tvStatusPenjualan;

        public PenjualanViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomorTransaksiPenjualan = itemView.findViewById(R.id.tvPenjualanNomorTransaksi);
            tvNamaKonsumenPenjualan = itemView.findViewById(R.id.tvPenjualanNamaKonsumen);
            tvNomorTeleponKonsumenPenjualan = itemView.findViewById(R.id.tvPenjualanNomorTeleponKonsumen);
            tvTanggalTransaksiPenjualan = itemView.findViewById(R.id.tvPenjualanTanggalTransaksi);
            tvStatusPenjualan = itemView.findViewById(R.id.tvPenjualanStatus);
        }
    }

    @NonNull
    @Override
    public PenjualanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.view_penjualan, parent, false);
        return new PenjualanListAdapter.PenjualanViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PenjualanViewHolder holder, int position) {
        final Penjualan current = penjualanListFiltered.get(position);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse(current.getTglTransaksi());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateFormat.applyPattern("ddMMyy");
        String nomorTransaksi = current.getJenis() + "-" + dateFormat.format(date) + "-" + current.getId();

        String statusTransaksi = null;
        switch(current.getStatus()) {
            case 1:
                statusTransaksi = "Terbuka";
                break;

            case 2:
                statusTransaksi = "Menunggu pembayaran";
                break;

            case 3:
                statusTransaksi = "Selesai";
                break;
        }

        holder.tvNomorTransaksiPenjualan.setText(nomorTransaksi);
        holder.tvNamaKonsumenPenjualan.setText(current.getKonsumen().getNama());
        holder.tvNomorTeleponKonsumenPenjualan.setText(current.getKonsumen().getNomorTelepon());
        holder.tvTanggalTransaksiPenjualan.setText(current.getTglTransaksi());
        holder.tvStatusPenjualan.setText(statusTransaksi);
    }

    @Override
    public int getItemCount() {
       return penjualanListFiltered.size();
    }

    public Penjualan getItem(int position) {
        return penjualanListFiltered.get(position);
    }
}
