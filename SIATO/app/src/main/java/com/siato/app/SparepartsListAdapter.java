package com.siato.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SparepartsListAdapter extends RecyclerView.Adapter<SparepartsListAdapter.SparepartsViewHolder> {
    private Context context;
    private List<Spareparts> sparepartsList;

    public SparepartsListAdapter(Context context, List<Spareparts> sparepartsList) {
        this.context = context;
        this.sparepartsList = sparepartsList;
    }

    public class SparepartsViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvKodeSpareparts;
        private final TextView tvNamaSpareparts;

        public SparepartsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKodeSpareparts = itemView.findViewById(R.id.tvSparepartsKode);
            tvNamaSpareparts = itemView.findViewById(R.id.tvSparepartsNama);
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
        final Spareparts current = sparepartsList.get(position);

        holder.tvKodeSpareparts.setText(current.getKode());
        holder.tvNamaSpareparts.setText(current.getNama());
    }

    @Override
    public int getItemCount() {
        return sparepartsList.size();
    }
}
