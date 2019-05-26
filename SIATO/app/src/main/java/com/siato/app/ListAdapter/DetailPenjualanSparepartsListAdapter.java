package com.siato.app.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.siato.app.POJO.DetailPenjualanSpareparts;
import com.siato.app.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class DetailPenjualanSparepartsListAdapter extends RecyclerView.Adapter<DetailPenjualanSparepartsListAdapter.DetilPenjualanViewHolder>{
    private Context context;
    private List<DetailPenjualanSpareparts> detailPenjualanSpareparts;

    public DetailPenjualanSparepartsListAdapter(Context context, List<DetailPenjualanSpareparts> detailPenjualanSpareparts) {
        this.context = context;
        this.detailPenjualanSpareparts = detailPenjualanSpareparts;
    }

    public class DetilPenjualanViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvNamaSpareparts;
        private final TextView tvMerkSpareparts;
        private final TextView tvHargaSpareparts;
        private final TextView tvJumlahBeli;

        public DetilPenjualanViewHolder(View itemView) {
            super(itemView);

            tvNamaSpareparts = itemView.findViewById(R.id.tvDetailPenjualanSpareparts);
            tvMerkSpareparts = itemView.findViewById(R.id.tvDetailPenjualanSparepartsMerk);
            tvHargaSpareparts = itemView.findViewById(R.id.tvDetailPenjualanSparepartsHarga);
            tvJumlahBeli = itemView.findViewById(R.id.tvDetailPenjualanSparepartsJumlah);
        }
    }

    @NonNull
    @Override
    public DetilPenjualanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.view_detail_penjualan_spareparts, parent, false);
        return new DetilPenjualanViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DetilPenjualanViewHolder holder, int position) {
        final DetailPenjualanSpareparts current = detailPenjualanSpareparts.get(position);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        NumberFormat numberFormat = new DecimalFormat("#,###", symbols);

        holder.tvNamaSpareparts.setText(current.getSpareparts().getNama());
        holder.tvMerkSpareparts.setText(current.getSpareparts().getMerk());
        holder.tvHargaSpareparts.setText("Rp" + numberFormat.format(current.getHarga()));
        holder.tvJumlahBeli.setText(String.valueOf(current.getJumlah()));
    }

    @Override
    public int getItemCount() {
        return detailPenjualanSpareparts.size();
    }

    public DetailPenjualanSpareparts getItem(int position) {
        return detailPenjualanSpareparts.get(position);
    }
}
