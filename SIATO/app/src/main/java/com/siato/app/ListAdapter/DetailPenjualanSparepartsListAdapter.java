package com.siato.app.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.siato.app.POJO.DetailPenjualan;
import com.siato.app.POJO.DetailPenjualanSpareparts;
import com.siato.app.R;

import java.util.List;

public class DetailPenjualanSparepartsListAdapter extends RecyclerView.Adapter<DetailPenjualanSparepartsListAdapter.DetilPenjualanViewHolder>{
    private Context context;
    private List<DetailPenjualanSpareparts> detailPenjualanSpareparts;

    public DetailPenjualanSparepartsListAdapter(Context context, List<DetailPenjualanSpareparts> detailPenjualanSpareparts) {
        this.context = context;
        this.detailPenjualanSpareparts = detailPenjualanSpareparts;
    }

    public class DetilPenjualanViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvSpareparts;
        private final TextView tvJumlah;

        public DetilPenjualanViewHolder(View itemView) {
            super(itemView);

            tvSpareparts = itemView.findViewById(R.id.tvDetailPenjualanSpareparts);
            tvJumlah = itemView.findViewById(R.id.tvDetailPenjualanJumlahSpareparts);
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

        holder.tvSpareparts.setText(current.getSpareparts().getKode());
        holder.tvJumlah.setText(String.valueOf(current.getJumlah()));
    }

    @Override
    public int getItemCount() {
        return detailPenjualanSpareparts.size();
    }

    public DetailPenjualanSpareparts getItem(int position) {
        return detailPenjualanSpareparts.get(position);
    }
}
