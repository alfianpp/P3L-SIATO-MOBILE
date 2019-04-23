package com.siato.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PengadaanBarangListAdapter extends RecyclerView.Adapter<PengadaanBarangListAdapter.PengadaanBarangViewHolder>
{
    private Context context;
    private List<PengadaanBarang> pengadaanBarangList;

    public PengadaanBarangListAdapter(Context context, List<PengadaanBarang> pengadaanBarangList) {
        this.context = context;
        this.pengadaanBarangList = pengadaanBarangList;
    }

    public class PengadaanBarangViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvIdSupplier;
        private final TextView tvTotal;
        private final TextView tvStatus;
        private final TextView tvTgl;

        public PengadaanBarangViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdSupplier = itemView.findViewById(R.id.tvPengadaanBrgIdSupplier);
            tvTotal = itemView.findViewById(R.id.tvPengadaanBrgTotal);
            tvStatus = itemView.findViewById(R.id.tvPengadaanBrgStatus);
            tvTgl = itemView.findViewById(R.id.tvPengadaanBrgTgl);
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
        final PengadaanBarang current = pengadaanBarangList.get(position);

       holder.tvIdSupplier.setText(current.getId_supplier());
       holder.tvTotal.setText((int) current.getTotal().doubleValue());
       holder.tvStatus.setText(current.getStatus());
       holder.tvTgl.setText(current.getTgl_transaksi().toString());
    }

    @Override
    public int getItemCount() {
        return pengadaanBarangList.size();
    }
}
