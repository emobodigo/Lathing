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
import com.example.djoha.lathing.Model.BidModel;
import com.example.djoha.lathing.Model.LelangModel;
import com.example.djoha.lathing.Profil.FinishLelangActivity;
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

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    private List<LelangModel> lelangModelList;
    private Activity activity;
    private FirebaseAuth mAuth;
    final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    Intent intent;
    LelangModel lelangModel;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    final StorageReference storageRef = storage.getReference();


    public ProfileAdapter(List<LelangModel> lelangModelList, Activity activity) {
        this.lelangModelList = lelangModelList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_post_profile, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.ViewHolder holder, int position) {
        mAuth = FirebaseAuth.getInstance();
        lelangModel = lelangModelList.get(position);
        holder.nama_barang.setText(lelangModel.getNama_barang());
        holder.harga.setText(lelangModel.getHarga_akhir());

        if(!lelangModel.getHarga_akhir().equals("-")){
            holder.btn_finish.setText("See Your Result");
        }
        StorageReference gallery = storageRef.child(""+lelangModel.getGambar());
        Glide.with(activity)
                .using(new FirebaseImageLoader())
                .load(gallery)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.gambar);

        holder.btn_finish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                database.child("user").child(mAuth.getCurrentUser().getUid()+"").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String nama_pelelang = dataSnapshot.child("username").getValue().toString();
                        intent = new Intent(activity,FinishLelangActivity.class);
                        intent.putExtra("key",lelangModel.getKey());
                        intent.putExtra("nama_pelelang",nama_pelelang);
                        activity.startActivity(intent);
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

        TextView nama_barang, harga;
        ImageView gambar;
        Button btn_finish;

        public ViewHolder(View v) {
            super(v);

            nama_barang = v.findViewById(R.id.namabarang);
            harga = v.findViewById(R.id.hargatinggi);
            gambar = v.findViewById(R.id.gambarpost);
            btn_finish = v.findViewById(R.id.profile_btn_stop);

        }
    }
}
