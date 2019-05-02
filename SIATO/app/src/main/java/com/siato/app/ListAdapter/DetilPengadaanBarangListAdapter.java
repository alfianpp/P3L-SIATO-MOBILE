package com.siato.app.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.siato.app.POJO.DetilPengadaanBarang;
import com.siato.app.R;

import java.util.ArrayList;
import java.util.List;

public class DetilPengadaanBarangListAdapter extends RecyclerView.Adapter<DetilPengadaanBarangListAdapter.DetilPengadaanBarangViewHolder>
{
    private Context context;
    private List<DetilPengadaanBarang> detilPengadaanBarangList;
    private List<DetilPengadaanBarang> detilPengadaanBarangListFiltered;

    public DetilPengadaanBarangListAdapter(Context context, List<DetilPengadaanBarang> detilPengadaanBarangList) {
        this.context = context;
        this.detilPengadaanBarangList = detilPengadaanBarangList;
        this.detilPengadaanBarangListFiltered = detilPengadaanBarangList;
    }

    public class DetilPengadaanBarangViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvNamaSupplier;
        private final TextView tvNamaBarang;
        private final TextView tvJumlahPesan;
        private final TextView tvJumlahDatang;
        private final TextView tvHarga;

        public DetilPengadaanBarangViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaSupplier = itemView.findViewById(R.id.tvDetilPengadaanNamaSupplier);
            tvNamaBarang = itemView.findViewById(R.id.tvDetilPengadaanNamaBarang);
            tvJumlahPesan = itemView.findViewById(R.id.tvDetilPengadaanJumlahPesan);
            tvJumlahDatang = itemView.findViewById(R.id.tvDetilPengadaanJumlahDatang);
            tvHarga = itemView.findViewById(R.id.tvDetilPengadaanHarga);

        }
    }

    @NonNull
    @Override
    public DetilPengadaanBarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.view_pengadaan_barang_detil, parent, false);
        return new DetilPengadaanBarangViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DetilPengadaanBarangViewHolder holder, int position) {
        final DetilPengadaanBarang current = detilPengadaanBarangListFiltered.get(position);

        holder.tvNamaSupplier.setText(current.getPengadaanBarang().getSupplier().getNama());
        holder.tvNamaBarang.setText(current.getSpareparts().getNama());
        holder.tvJumlahPesan.setText(current.getJmlPesan());
        holder.tvJumlahDatang.setText(current.getJmlDatang());
        holder.tvHarga.setText(current.getSpareparts().getNama());
    }

    @Override
    public int getItemCount() {
        return detilPengadaanBarangListFiltered.size();
    }

    public DetilPengadaanBarang getItem(int position) {
        return detilPengadaanBarangListFiltered.get(position);
    }
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()) {
                    detilPengadaanBarangListFiltered = detilPengadaanBarangList;
                }
                else {
                    List<DetilPengadaanBarang> filteredList = new ArrayList<>();
                    for(DetilPengadaanBarang row : detilPengadaanBarangList) {
                        if(row.getSpareparts().getNama().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    detilPengadaanBarangListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = detilPengadaanBarangListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                detilPengadaanBarangListFiltered = (ArrayList<DetilPengadaanBarang>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
