package com.siato.app.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.siato.app.POJO.DetailPengadaanBarang;
import com.siato.app.R;

import java.util.ArrayList;
import java.util.List;

public class DetailPengadaanBarangListAdapter extends RecyclerView.Adapter<DetailPengadaanBarangListAdapter.DetilPengadaanBarangViewHolder>
{
    private Context context;
    private List<DetailPengadaanBarang> detailPengadaanBarangList;
    private List<DetailPengadaanBarang> detailPengadaanBarangListFiltered;

    public DetailPengadaanBarangListAdapter(Context context, List<DetailPengadaanBarang> detailPengadaanBarangList) {
        this.context = context;
        this.detailPengadaanBarangList = detailPengadaanBarangList;
        this.detailPengadaanBarangListFiltered = detailPengadaanBarangList;
    }

    public class DetilPengadaanBarangViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvNamaBarang;
        private final TextView tvJumlahPesan;
        private final TextView tvJumlahDatang;
        private final TextView tvHarga;

        public DetilPengadaanBarangViewHolder(@NonNull View itemView) {
            super(itemView);
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
        View itemView = layoutInflater.inflate(R.layout.view_detail_pengadaan_barang, parent, false);
        return new DetilPengadaanBarangViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DetilPengadaanBarangViewHolder holder, int position) {
        final DetailPengadaanBarang current = detailPengadaanBarangListFiltered.get(position);

        holder.tvNamaBarang.setText(current.getSpareparts().getNama());
        holder.tvJumlahPesan.setText(String.valueOf(current.getJumlahPesan()));
        holder.tvJumlahDatang.setText(String.valueOf(current.getJumlahDatang()));
        holder.tvHarga.setText(String.valueOf(current.getHarga()));
    }

    @Override
    public int getItemCount() {
        return detailPengadaanBarangListFiltered.size();
    }

    public DetailPengadaanBarang getItem(int position) {
        return detailPengadaanBarangListFiltered.get(position);
    }
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()) {
                    detailPengadaanBarangListFiltered = detailPengadaanBarangList;
                }
                else {
                    List<DetailPengadaanBarang> filteredList = new ArrayList<>();
                    for(DetailPengadaanBarang row : detailPengadaanBarangList) {
                        if(row.getSpareparts().getNama().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    detailPengadaanBarangListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = detailPengadaanBarangListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                detailPengadaanBarangListFiltered = (ArrayList<DetailPengadaanBarang>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
