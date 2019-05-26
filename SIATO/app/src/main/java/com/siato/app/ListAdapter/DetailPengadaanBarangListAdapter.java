package com.siato.app.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.siato.app.POJO.DetailPengadaanBarang;
import com.siato.app.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailPengadaanBarangListAdapter extends RecyclerView.Adapter<DetailPengadaanBarangListAdapter.DetilPengadaanBarangViewHolder> {
    private Context context;
    private List<DetailPengadaanBarang> detailPengadaanBarangList;
    private List<DetailPengadaanBarang> detailPengadaanBarangListFiltered;

    public DetailPengadaanBarangListAdapter(Context context, List<DetailPengadaanBarang> detailPengadaanBarangList) {
        this.context = context;
        this.detailPengadaanBarangList = detailPengadaanBarangList;
        this.detailPengadaanBarangListFiltered = detailPengadaanBarangList;
    }

    public class DetilPengadaanBarangViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvKodeSpareparts;
        private final TextView tvNamaSpareparts;
        private final TextView tvMerkSpareparts;
        private final TextView tvJumlahPesan;
        private final LinearLayout layoutVerifikasi;
        private final TextView tvJumlahDatang;
        private final TextView tvHarga;

        public DetilPengadaanBarangViewHolder(@NonNull View itemView) {
            super(itemView);

            tvKodeSpareparts = itemView.findViewById(R.id.tvDetailPengadaanBarangKodeSpareparts);
            tvNamaSpareparts = itemView.findViewById(R.id.tvDetailPengadaanBarangNamaSpareparts);
            tvMerkSpareparts = itemView.findViewById(R.id.tvDetailPengadaanBarangMerkSpareparts);
            tvJumlahPesan = itemView.findViewById(R.id.tvDetailPengadaanBarangJumlahPesan);
            layoutVerifikasi = itemView.findViewById(R.id.layoutDetailPengadaanBarangVerifikasi);
            tvJumlahDatang = itemView.findViewById(R.id.tvDetailPengadaanBarangJumlahDatang);
            tvHarga = itemView.findViewById(R.id.tvDetailPengadaanBarangHargaSatuan);
        }
    }

    @NonNull
    @Override
    public DetilPengadaanBarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.view_detail_pengadaan_barang, parent, false);
        return new DetilPengadaanBarangViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DetilPengadaanBarangViewHolder holder, int position) {
        final DetailPengadaanBarang current = detailPengadaanBarangListFiltered.get(position);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        NumberFormat numberFormat = new DecimalFormat("#,###", symbols);

        holder.tvKodeSpareparts.setText(current.getSpareparts().getKode());
        holder.tvNamaSpareparts.setText(current.getSpareparts().getNama());
        holder.tvMerkSpareparts.setText(current.getSpareparts().getMerk());
        holder.tvJumlahPesan.setText(String.valueOf(current.getJumlahPesan()));

        if(current.getJumlahDatang() != null && current.getHarga() != null) {
            holder.tvJumlahDatang.setText(String.valueOf(current.getJumlahDatang()));
            holder.tvHarga.setText("Rp" + numberFormat.format(current.getHarga()));
        }
        else {
            holder.layoutVerifikasi.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return detailPengadaanBarangListFiltered.size();
    }

    public DetailPengadaanBarang getItem(int position) {
        return detailPengadaanBarangListFiltered.get(position);
    }
}
