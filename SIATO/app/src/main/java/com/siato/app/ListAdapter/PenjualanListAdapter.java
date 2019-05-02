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

import com.siato.app.POJO.Penjualan;
import com.siato.app.R;

import java.util.ArrayList;
import java.util.List;

public class PenjualanListAdapter extends RecyclerView.Adapter<PenjualanListAdapter.PenjualanViewHolder> implements Filterable {
    private Context context;
    private List<Penjualan> penjualanList;
    private List<Penjualan> penjualanListFiltered;

    public PenjualanListAdapter(Context context,List<Penjualan> penjualanList) {
        this.context = context;
        this.penjualanList = penjualanList;
        this.penjualanListFiltered = penjualanList;
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
        final Penjualan current = penjualanListFiltered.get(position);

        holder.tvPenjualanJenis.setText(current.getJenis());
        holder.tvPenjualanNamaKonsumen.setText(current.getKonsumen().getNama());
    }

    @Override
    public int getItemCount() {
       return penjualanListFiltered.size();
    }
    public Penjualan getItem(int position) {
        return penjualanListFiltered.get(position);
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()) {
                    penjualanListFiltered = penjualanList;
                }
                else {
                    List<Penjualan> filteredList = new ArrayList<>();
                    for(Penjualan row : penjualanList) {
                        if(row.getKonsumen().getNama().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    penjualanListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = penjualanListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                penjualanListFiltered = (ArrayList<Penjualan>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
