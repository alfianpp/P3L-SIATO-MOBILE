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

import com.siato.app.POJO.Konsumen;
import com.siato.app.R;

import java.util.ArrayList;
import java.util.List;

public class KonsumenListAdapter extends RecyclerView.Adapter<KonsumenListAdapter.KonsumenViewHolder> implements Filterable {
    private Context context;
    private List<Konsumen> konsumenList;
    private List<Konsumen> konsumenListFiltered;

    public KonsumenListAdapter(Context context, List<Konsumen> konsumenList) {
        this.context = context;
        this.konsumenList = konsumenList;
        this.konsumenListFiltered = konsumenList;
    }

    public class KonsumenViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvNamaKonsumen;
        private final TextView tvNomorTeleponKonsumen;
        private final TextView tvAlamatKonsumen;

        public KonsumenViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaKonsumen = itemView.findViewById(R.id.tvKonsumenNama);
            tvNomorTeleponKonsumen = itemView.findViewById(R.id.tvKonsumenNomorTelepon);
            tvAlamatKonsumen = itemView.findViewById(R.id.tvKonsumenAlamat);
        }
    }

    @NonNull
    @Override
    public KonsumenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.view_konsumen, parent, false);
        return new KonsumenViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull KonsumenViewHolder holder, int position) {
        final Konsumen current = konsumenListFiltered.get(position);

        holder.tvNamaKonsumen.setText(current.getNama());
        holder.tvNomorTeleponKonsumen.setText(current.getNomorTelepon());
        holder.tvAlamatKonsumen.setText(current.getAlamat());
    }

    @Override
    public int getItemCount() {
        return konsumenListFiltered.size();
    }

    public Konsumen getItem(int position) {
        return konsumenListFiltered.get(position);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()) {
                    konsumenListFiltered = konsumenList;
                }
                else {
                    List<Konsumen> filteredList = new ArrayList<>();
                    for(Konsumen row : konsumenList) {
                        if(row.getNama().toLowerCase().contains(charString.toLowerCase()) || row.getAlamat().toLowerCase().contains(charString.toLowerCase()) || row.getNomorTelepon().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    konsumenListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = konsumenListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                konsumenListFiltered = (ArrayList<Konsumen>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
