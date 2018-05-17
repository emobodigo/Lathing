package com.example.djoha.lathing.Utils;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.djoha.lathing.Bid.BidActivity;
import com.example.djoha.lathing.Model.LelangModel;
import com.example.djoha.lathing.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    List<LelangModel> lelangModelList;
    LelangModel lelangModel;

    Activity activity;
    Intent intent;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    final StorageReference storageRef = storage.getReference();

    private FirebaseAuth mAuth;
    final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public HomeAdapter(List<LelangModel> lelangModelList, Activity activity) {
        mAuth = FirebaseAuth.getInstance();
        this.lelangModelList = lelangModelList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_home_layout, parent, false);

        return new ViewHolder(v, activity);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        final LelangModel lelangModel = lelangModelList.get(position);
        holder.nama_barang.setText(lelangModel.getNama_barang());
        holder.username.setText(lelangModel.getNama_pelelang());
        holder.deskripsi.setText(lelangModel.getDeskripsi());
        holder.bid_awal.setText(lelangModel.getHarga_awal());
        holder.bid_tanggal.setText(lelangModel.getAwal_bid());
        StorageReference gallery = storageRef.child("" + lelangModel.getGambar());

        Glide.with(activity)
                .using(new FirebaseImageLoader())
                .load(gallery)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.gambar);

        if (lelangModel.getId_pelelang().equalsIgnoreCase(mAuth.getCurrentUser().getUid())) {
            holder.btn_bid.setVisibility(View.GONE);
        } else if (!lelangModel.getHarga_akhir().toString().equalsIgnoreCase("-")) {
            holder.btn_bid.setText("Acution is Over");
            holder.btn_bid.setBackgroundColor(activity.getResources().getColor(R.color.grey));
            holder.btn_bid.setEnabled(false);
        }

        holder.btn_bid.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                database.child("user").child(mAuth.getCurrentUser().getUid() + "").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String nama_pelelang = dataSnapshot.child("username").getValue().toString();
                        intent = new Intent(activity, BidActivity.class);
                        intent.putExtra("key", lelangModel.getKey());
                        intent.putExtra("nama_pelelang", nama_pelelang);
                        intent.putExtra("foto", lelangModel.getGambar());
                        activity.startActivity(intent);
                        Toast.makeText(activity, lelangModel.getKey() + " " + lelangModel.getNama_barang(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

    }

    @Override
    public int getItemCount() {
        return lelangModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nama_barang, username, deskripsi, bid_awal, bid_tanggal;
        ImageView gambar, foto_profil;
        Button btn_bid;
        Activity act;

        public ViewHolder(View v, Activity activity) {
            super(v);

            nama_barang = v.findViewById(R.id.itemname);
            bid_awal = v.findViewById(R.id.bidawal);
            gambar = v.findViewById(R.id.image1);
            username = v.findViewById(R.id.username_lelang);
            deskripsi = v.findViewById(R.id.description);
            bid_tanggal = v.findViewById(R.id.biddate);
            btn_bid = v.findViewById(R.id.button_bid);
            this.act = activity;


        }
    }
}
