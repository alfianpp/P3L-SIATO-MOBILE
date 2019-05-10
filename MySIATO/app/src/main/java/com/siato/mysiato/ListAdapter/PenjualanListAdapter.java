package com.siato.mysiato.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.siato.mysiato.POJO.Penjualan;
import com.siato.mysiato.R;

import java.util.List;

public class PenjualanListAdapter extends RecyclerView.Adapter<PenjualanListAdapter.PenjualanViewHolder> {
    private Context context;
    private List<Penjualan> penjualanList;

    public PenjualanListAdapter(Context context, List<Penjualan> penjualanList) {
        this.context = context;
        this.penjualanList = penjualanList;
    }

    public class PenjualanViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvPenjualanJenis;
        private final TextView tvPenjualanNamaKonsumen;

        public PenjualanViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPenjualanJenis = itemView.findViewById(R.id.tvPenjualanKode);
            tvPenjualanNamaKonsumen = itemView.findViewById(R.id.tvPenjualanNamaKonsumen);
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
        final Penjualan current = penjualanList.get(position);

        holder.tvPenjualanJenis.setText(current.getJenis());
        holder.tvPenjualanNamaKonsumen.setText(current.getKonsumen().getNama());
    }

    @Override
    public int getItemCount() {
       return penjualanList.size();
    }

    public Penjualan getItem(int position) {
        return penjualanList.get(position);
    }
}
