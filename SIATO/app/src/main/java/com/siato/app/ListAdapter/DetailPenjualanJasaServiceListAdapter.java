package com.siato.app.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.siato.app.POJO.DetailPenjualanJasaService;
import com.siato.app.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class DetailPenjualanJasaServiceListAdapter extends RecyclerView.Adapter<DetailPenjualanJasaServiceListAdapter.DetilPenjualanViewHolder>{
    private Context context;
    private List<DetailPenjualanJasaService> detailPenjualanJasaServiceList;

    public DetailPenjualanJasaServiceListAdapter(Context context, List<DetailPenjualanJasaService> detailPenjualanJasaServiceList) {
        this.context = context;
        this.detailPenjualanJasaServiceList = detailPenjualanJasaServiceList;
    }

    public class DetilPenjualanViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvNamaJasaService;
        private final TextView tvHargaJasaService;

        public DetilPenjualanViewHolder(View itemView) {
            super(itemView);

            tvNamaJasaService = itemView.findViewById(R.id.tvDetailPenjualanJasaService);
            tvHargaJasaService = itemView.findViewById(R.id.tvDetailPenjualanJasaServiceHarga);
        }
    }

    @NonNull
    @Override
    public DetilPenjualanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.view_detail_penjualan_jasaservice, parent, false);
        return new DetilPenjualanViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DetilPenjualanViewHolder holder, int position) {
        final DetailPenjualanJasaService current = detailPenjualanJasaServiceList.get(position);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        NumberFormat numberFormat = new DecimalFormat("#,###", symbols);

        holder.tvNamaJasaService.setText(current.getJasaService().getNama());
        holder.tvHargaJasaService.setText("Rp" + numberFormat.format(current.getHarga()));
    }

    @Override
    public int getItemCount() {
        return detailPenjualanJasaServiceList.size();
    }

    public DetailPenjualanJasaService getItem(int position) {
        return detailPenjualanJasaServiceList.get(position);
    }
}
