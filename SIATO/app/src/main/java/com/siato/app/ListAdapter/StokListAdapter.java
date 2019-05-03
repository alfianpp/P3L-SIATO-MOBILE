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

public class StokListAdapter extends RecyclerView.Adapter<StokListAdapter.StokViewHolder> implements Filterable {
    private Context context;
    private List<Spareparts> StokList;
    private List<Spareparts> StokListFiltered;

    public StokListAdapter(Context context, List<Spareparts> StokList) {
        this.context = context;
        this.StokList = StokList;
        this.StokListFiltered = StokList;
    }

    public class StokViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvNamaStok;
        private final TextView tvJmlStok;

        public StokViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaStok = itemView.findViewById(R.id.tvStokNama);
            tvJmlStok = itemView.findViewById(R.id.tvJumlahStok);
        }
    }

    @NonNull
    @Override
    public StokViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.view_stok, parent, false);
        return new StokListAdapter.StokViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StokViewHolder holder, int position) {
        final Spareparts current = StokListFiltered.get(position);

        holder.tvNamaStok.setText(current.getNama());
        holder.tvJmlStok.setText(current.getStok());
    }

    @Override
    public int getItemCount() {
        return StokListFiltered.size();
    }

    public Spareparts getItem(int position) {
        return StokListFiltered.get(position);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()) {
                    StokListFiltered = StokList;
                }
                else {
                    List<Spareparts> filteredList = new ArrayList<>();
                    for(Spareparts row : StokList) {
                        if(row.getNama().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    StokListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = StokListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                StokListFiltered = (ArrayList<Spareparts>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
