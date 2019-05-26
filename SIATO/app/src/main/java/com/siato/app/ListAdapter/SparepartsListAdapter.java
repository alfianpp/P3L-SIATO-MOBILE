package com.siato.app.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.siato.app.POJO.Spareparts;
import com.siato.app.R;

public class SparepartsListAdapter extends RecyclerView.Adapter<SparepartsListAdapter.SparepartsViewHolder> implements Filterable {
    private Context context;
    private List<Spareparts> sparepartsList;
    private List<Spareparts> sparepartsListFiltered;

    public SparepartsListAdapter(Context context, List<Spareparts> sparepartsList) {
        this.context = context;
        this.sparepartsList = sparepartsList;
        this.sparepartsListFiltered = sparepartsList;
    }

    public class SparepartsViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvKodeSpareparts;
        private final TextView tvNamaSpareparts;
        private final TextView tvMerkSpareparts;
        private final TextView tvHargaBeliSpareparts;
        private final TextView tvHargaJualSpareparts;

        public SparepartsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKodeSpareparts = itemView.findViewById(R.id.tvSparepartsKode);
            tvNamaSpareparts = itemView.findViewById(R.id.tvSparepartsNama);
            tvMerkSpareparts = itemView.findViewById(R.id.tvSparepartsMerk);
            tvHargaBeliSpareparts = itemView.findViewById(R.id.tvSparepartsHargaBeli);
            tvHargaJualSpareparts = itemView.findViewById(R.id.tvSparepartsHargaJual);
        }
    }

    @NonNull
    @Override
    public SparepartsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.view_spareparts, parent, false);
        return new SparepartsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SparepartsViewHolder holder, int position) {
        final Spareparts current = sparepartsListFiltered.get(position);

        holder.tvKodeSpareparts.setText(current.getKode());
        holder.tvNamaSpareparts.setText(current.getNama());
        holder.tvMerkSpareparts.setText(current.getMerk());
        holder.tvHargaBeliSpareparts.setText(String.valueOf(current.getHargaBeli()));
        holder.tvHargaJualSpareparts.setText(String.valueOf(current.getHargaJual()));
    }

    @Override
    public int getItemCount() {
        return sparepartsListFiltered.size();
    }

    public Spareparts getItem(int position) {
        return sparepartsListFiltered.get(position);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()) {
                    sparepartsListFiltered = sparepartsList;
                }
                else {
                    List<Spareparts> filteredList = new ArrayList<>();
                    for(Spareparts row : sparepartsList) {
                        if(row.getKode().toLowerCase().contains(charString.toLowerCase()) || row.getNama().toLowerCase().contains(charString.toLowerCase()) || row.getMerk().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    sparepartsListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = sparepartsListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                sparepartsListFiltered = (ArrayList<Spareparts>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
