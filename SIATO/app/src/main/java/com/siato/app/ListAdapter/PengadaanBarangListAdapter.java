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

import com.siato.app.POJO.PengadaanBarang;
import com.siato.app.R;

import java.util.ArrayList;
import java.util.List;

public class PengadaanBarangListAdapter extends RecyclerView.Adapter<PengadaanBarangListAdapter.PengadaanBarangViewHolder> implements Filterable
{
    private Context context;
    private List<PengadaanBarang> pengadaanBarangList;
    private List<PengadaanBarang> pengadaanBarangListFiltered;

    public PengadaanBarangListAdapter(Context context, List<PengadaanBarang> pengadaanBarangList) {
        this.context = context;
        this.pengadaanBarangList = pengadaanBarangList;
        this.pengadaanBarangListFiltered = pengadaanBarangList;
    }

    public class PengadaanBarangViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvNamaSupplier;
        private final TextView tvTanggalTransaksi;

        public PengadaanBarangViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaSupplier = itemView.findViewById(R.id.tvPengadaanNamaSupplier);
            tvTanggalTransaksi = itemView.findViewById(R.id.tvPengadaanTanggalTransaksi);
        }
    }

    @NonNull
    @Override
    public PengadaanBarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.view_pengadaan_barang, parent, false);
        return new PengadaanBarangViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PengadaanBarangViewHolder holder, int position) {
        final PengadaanBarang current = pengadaanBarangListFiltered.get(position);

        holder.tvNamaSupplier.setText(current.getSupplier().getNama());
        holder.tvTanggalTransaksi.setText(current.getTglTransaksi());
    }

    @Override
    public int getItemCount() {
        return pengadaanBarangListFiltered.size();
    }

    public PengadaanBarang getItem(int position) {
        return pengadaanBarangListFiltered.get(position);
    }
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()) {
                    pengadaanBarangListFiltered = pengadaanBarangList;
                }
                else {
                    List<PengadaanBarang> filteredList = new ArrayList<>();
                    for(PengadaanBarang row : pengadaanBarangList) {
                        if(row.getSupplier().getNama().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    pengadaanBarangListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = pengadaanBarangListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                pengadaanBarangListFiltered = (ArrayList<PengadaanBarang>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
