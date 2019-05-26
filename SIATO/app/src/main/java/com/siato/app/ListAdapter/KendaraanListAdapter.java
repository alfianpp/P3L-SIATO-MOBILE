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

import com.siato.app.POJO.Kendaraan;
import com.siato.app.R;

import java.util.ArrayList;
import java.util.List;

public class KendaraanListAdapter extends RecyclerView.Adapter<KendaraanListAdapter.KendaraanViewHolder> implements Filterable {
    private Context context;
    private List<Kendaraan> kendaraanList;
    private List<Kendaraan> kendaraanListFiltered;

    public KendaraanListAdapter(Context context, List<Kendaraan> kendaraanList) {
        this.context = context;
        this.kendaraanList = kendaraanList;
        this.kendaraanListFiltered = kendaraanList;
    }

    public class KendaraanViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvNomorPolisiKendaraan;
        private final TextView tvMerkTipeKendaraan;
        private final TextView tvNamaPemilikKendaraan;

        public KendaraanViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomorPolisiKendaraan = itemView.findViewById(R.id.tvKendaraanNomorPolisi);
            tvMerkTipeKendaraan = itemView.findViewById(R.id.tvKendaraanMerkTipe);
            tvNamaPemilikKendaraan = itemView.findViewById(R.id.tvKendaraanNamaPemilik);
        }
    }

    @NonNull
    @Override
    public KendaraanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.view_kendaraan, parent, false);
        return new KendaraanListAdapter.KendaraanViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull KendaraanViewHolder holder, int position) {
        final Kendaraan current = kendaraanListFiltered.get(position);

        holder.tvNomorPolisiKendaraan.setText(current.getNomorPolisi());
        holder.tvMerkTipeKendaraan.setText(current.getMerk() + " " + current.getTipe());
        holder.tvNamaPemilikKendaraan.setText(current.getPemilik().getNama());
    }

    @Override
    public int getItemCount() {
        return kendaraanListFiltered.size();
    }

    public Kendaraan getItem(int position) {
        return kendaraanListFiltered.get(position);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()) {
                    kendaraanListFiltered = kendaraanList;
                }
                else {
                    List<Kendaraan> filteredList = new ArrayList<>();
                    for(Kendaraan row : kendaraanList) {
                        if(row.getNomorPolisi().toLowerCase().contains(charString.toLowerCase()) || row.getMerk().toLowerCase().contains(charString.toLowerCase()) || row.getTipe().toLowerCase().contains(charString.toLowerCase()) || row.getPemilik().getNama().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    kendaraanListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = kendaraanListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                kendaraanListFiltered = (ArrayList<Kendaraan>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}