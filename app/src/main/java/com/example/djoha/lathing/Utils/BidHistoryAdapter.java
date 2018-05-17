package com.example.djoha.lathing.Utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.djoha.lathing.Model.BidModel;
import com.example.djoha.lathing.R;

import java.util.List;

public class BidHistoryAdapter extends RecyclerView.Adapter<BidHistoryAdapter.ViewHolder> {

    private List<BidModel> bidModelList;
    private Activity activity;

    public BidHistoryAdapter(List<BidModel> bidModelList, Activity activity) {
        this.bidModelList = bidModelList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public BidHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_bid_history, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BidHistoryAdapter.ViewHolder holder, int position) {
        BidModel bidModel = bidModelList.get(position);
        holder.bidHistory_tv_nama.setText(bidModel.getId_penawar());
        holder.bidHistory_tv_tanggal.setText(bidModel.getWaktu_bid());
        holder.bidHistory_tv_nominal.setText(bidModel.getJumlah_bid()+"");
    }

    @Override
    public int getItemCount() {
        return bidModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView bidHistory_tv_tanggal, bidHistory_tv_nama, bidHistory_tv_nominal;

        public ViewHolder(View v) {
            super(v);

            bidHistory_tv_nama = v.findViewById(R.id.bidHistory_tv_nama);
            bidHistory_tv_nominal = v.findViewById(R.id.bidHistory_tv_nominal);
            bidHistory_tv_tanggal = v.findViewById(R.id.bidHistory_tv_tanggal);

        }
    }
}
