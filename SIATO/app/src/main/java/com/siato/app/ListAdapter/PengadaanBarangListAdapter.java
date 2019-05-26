package com.siato.app.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.siato.app.POJO.PengadaanBarang;
import com.siato.app.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PengadaanBarangListAdapter extends RecyclerView.Adapter<PengadaanBarangListAdapter.PengadaanBarangViewHolder> {
    private Context context;
    private List<PengadaanBarang> pengadaanBarangList;
    private List<PengadaanBarang> pengadaanBarangListFiltered;

    public PengadaanBarangListAdapter(Context context, List<PengadaanBarang> pengadaanBarangList) {
        this.context = context;
        this.pengadaanBarangList = pengadaanBarangList;
        this.pengadaanBarangListFiltered = pengadaanBarangList;
    }

    public class PengadaanBarangViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvNamaSupplier;
        private final TextView tvTotal;
        private final TextView tvTanggalTransaksi;
        private final TextView tvStatus;

        public PengadaanBarangViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaSupplier = itemView.findViewById(R.id.tvPengadaanNamaSupplier);
            tvTotal = itemView.findViewById(R.id.tvPengadaanBarangTotal);
            tvTanggalTransaksi = itemView.findViewById(R.id.tvPengadaanTanggalTransaksi);
            tvStatus = itemView.findViewById(R.id.tvPengadaanBarangStatus);
        }
    }

    @NonNull
    @Override
    public PengadaanBarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.view_pengadaan_barang, parent, false);
        return new PengadaanBarangViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PengadaanBarangViewHolder holder, int position) {
        final PengadaanBarang current = pengadaanBarangListFiltered.get(position);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        NumberFormat numberFormat = new DecimalFormat("#,###", symbols);

        String statusTransaksi = null;
        switch(current.getStatus()) {
            case 1:
                statusTransaksi = "Terbuka";
                break;

            case 2:
                statusTransaksi = "Menunggu verifikasi";
                break;

            case 3:
                statusTransaksi = "Selesai";
                break;
        }

        holder.tvNamaSupplier.setText(current.getSupplier().getNama());
        holder.tvTotal.setText("Rp" + numberFormat.format((current.getTotal() != null) ? current.getTotal() : 0));
        holder.tvTanggalTransaksi.setText(current.getTglTransaksi());
        holder.tvStatus.setText(statusTransaksi);
    }

    @Override
    public int getItemCount() {
        return pengadaanBarangListFiltered.size();
    }

    public PengadaanBarang getItem(int position) {
        return pengadaanBarangListFiltered.get(position);
    }
}
