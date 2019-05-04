package com.siato.app.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.siato.app.POJO.DetailPenjualanJasaService;
import com.siato.app.POJO.DetailPenjualanSpareparts;
import com.siato.app.R;

import java.util.List;

public class DetailPenjualanJasaServiceListAdapter extends RecyclerView.Adapter<DetailPenjualanJasaServiceListAdapter.DetilPenjualanViewHolder>{
    private Context context;
    private List<DetailPenjualanJasaService> detailPenjualanJasaServiceList;

    public DetailPenjualanJasaServiceListAdapter(Context context, List<DetailPenjualanJasaService> detailPenjualanJasaServiceList) {
        this.context = context;
        this.detailPenjualanJasaServiceList = detailPenjualanJasaServiceList;
    }

    public class DetilPenjualanViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvJasaService;

        public DetilPenjualanViewHolder(View itemView) {
            super(itemView);

            tvJasaService = itemView.findViewById(R.id.tvDetailPenjualanJasaService);
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

        holder.tvJasaService.setText(current.getJasaService().getNama());
    }

    @Override
    public int getItemCount() {
        return detailPenjualanJasaServiceList.size();
    }

    public DetailPenjualanJasaService getItem(int position) {
        return detailPenjualanJasaServiceList.get(position);
    }
}
