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
import com.siato.app.POJO.Spareparts;
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
        private final TextView tvIDKonsumen;
        private final TextView tvNamaKonsumen;

        public KonsumenViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIDKonsumen = itemView.findViewById(R.id.tvKonsumenID);
            tvNamaKonsumen = itemView.findViewById(R.id.tvKonsumenNama);
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

        holder.tvIDKonsumen.setText(current.getID().toString());
        holder.tvNamaKonsumen.setText(current.getNama());
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
                        if(row.getNama().toLowerCase().contains(charString.toLowerCase())) {
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
