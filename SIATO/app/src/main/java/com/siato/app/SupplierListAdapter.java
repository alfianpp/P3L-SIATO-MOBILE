package com.siato.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SupplierListAdapter extends RecyclerView.Adapter<SupplierListAdapter.SupplierViewHolder> {
    private Context context;
    private List<Supplier> supplierList;
    private List<Supplier> supplierListFiltered;

    public SupplierListAdapter(Context context, List<Supplier> supplierList) {
        this.context = context;
        this.supplierList = supplierList;
        this.supplierListFiltered = supplierList;
    }

    public class SupplierViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvNamaSupplier;
        private final TextView tvAlamatSupplier;
        private final TextView tvNamaSalesSupplier;
        private final TextView tvNomorTeleponSupplier;

        public SupplierViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaSupplier = itemView.findViewById(R.id.tvSupplierNama);
            tvAlamatSupplier = itemView.findViewById(R.id.tvSupplierAlamat);
            tvNamaSalesSupplier = itemView.findViewById(R.id.tvSupplierNamaSales);
            tvNomorTeleponSupplier = itemView.findViewById(R.id.tvSupplierNomorTelepon);
        }
    }

    @NonNull
    @Override
    public SupplierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.view_supplier, parent, false);
        return new SupplierViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SupplierViewHolder holder, int position) {
        final Supplier current = supplierList.get(position);

        holder.tvNamaSupplier.setText(current.getNama());
        holder.tvAlamatSupplier.setText(current.getAlamat());
        holder.tvNamaSalesSupplier.setText(current.getNama_sales());
        holder.tvNomorTeleponSupplier.setText(current.getNomor_telepon_sales());
    }

    @Override
    public int getItemCount() {
        return supplierList.size();
    }

    public Supplier getItem(int position) {
        return supplierListFiltered.get(position);
    }


    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()) {
                    supplierListFiltered = supplierList;
                }
                else {
                    List<Supplier> filteredList = new ArrayList<>();
                    for(Supplier row : supplierList) {
                        if(row.getNama().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    supplierListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = supplierListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                supplierListFiltered = (ArrayList<Supplier>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
