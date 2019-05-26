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

import com.siato.app.POJO.Spareparts;
import com.siato.app.R;

import java.util.ArrayList;
import java.util.List;

public class StokSparepartsListAdapter extends RecyclerView.Adapter<StokSparepartsListAdapter.StokViewHolder> implements Filterable {
    private Context context;
    private List<Spareparts> StokSparepartsList;
    private List<Spareparts> StokSparepartsListFiltered;

    public StokSparepartsListAdapter(Context context, List<Spareparts> StokSparepartsList) {
        this.context = context;
        this.StokSparepartsList = StokSparepartsList;
        this.StokSparepartsListFiltered = StokSparepartsList;
    }

    public class StokViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvKodeSpareparts;
        private final TextView tvNamaSpareparts;
        private final TextView tvMerkSpareparts;
        private final TextView tvStokSpareparts;
        private final TextView tvStokMinimalSpareparts;

        public StokViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKodeSpareparts = itemView.findViewById(R.id.tvStokSparepartsKode);
            tvNamaSpareparts = itemView.findViewById(R.id.tvStokSparepartsNama);
            tvMerkSpareparts = itemView.findViewById(R.id.tvStokSparepartsMerk);
            tvStokSpareparts = itemView.findViewById(R.id.tvStokSparepartsStok);
            tvStokMinimalSpareparts = itemView.findViewById(R.id.tvStokSparepartsStokMinimal);
        }
    }

    @NonNull
    @Override
    public StokViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.view_stok_spareparts, parent, false);
        return new StokSparepartsListAdapter.StokViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StokViewHolder holder, int position) {
        final Spareparts current = StokSparepartsListFiltered.get(position);

        holder.tvKodeSpareparts.setText(current.getKode());
        holder.tvNamaSpareparts.setText(current.getNama());
        holder.tvMerkSpareparts.setText(current.getMerk());
        holder.tvStokSpareparts.setText(String.valueOf(current.getStok()));
        holder.tvStokMinimalSpareparts.setText(String.valueOf(current.getStokMinimal()));
    }

    @Override
    public int getItemCount() {
        return StokSparepartsListFiltered.size();
    }

    public Spareparts getItem(int position) {
        return StokSparepartsListFiltered.get(position);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()) {
                    StokSparepartsListFiltered = StokSparepartsList;
                }
                else {
                    List<Spareparts> filteredList = new ArrayList<>();
                    for(Spareparts row : StokSparepartsList) {
                        if(row.getKode().toLowerCase().contains(charString.toLowerCase()) || row.getNama().toLowerCase().contains(charString.toLowerCase()) || row.getMerk().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    StokSparepartsListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = StokSparepartsListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                StokSparepartsListFiltered = (ArrayList<Spareparts>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
