package com.example.djoha.lathing.Home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.djoha.lathing.Bid.BidActivity;
import com.example.djoha.lathing.Model.LelangModel;
import com.example.djoha.lathing.R;
import com.example.djoha.lathing.Utils.HomeAdapter;
import com.example.djoha.lathing.Utils.MethodFirebase;
import com.example.djoha.lathing.Utils.ProfileAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private Context mContext = getActivity();
    private MethodFirebase methodFirebase;
    RecyclerView rvhome;
    ImageView cimg, postimg;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    String nama_pelelang;
    Intent x;

    HomeAdapter homeAdapter;
    LelangModel lelangModel;
    List<LelangModel> listLelang = new ArrayList<>();
    final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_layout, container, false);
         rvhome = (RecyclerView)view.findViewById(R.id.recyclerhome);
         rvhome.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rvhome.setLayoutManager(layoutManager);

         postimg = view.findViewById(R.id.bidbutton);
         postimg.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
              x = new Intent(getActivity(),TambahLelangActivity.class);
                 database.child("user").child(mAuth.getCurrentUser().getUid()+"").addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(DataSnapshot dataSnapshot) {
                         nama_pelelang = dataSnapshot.child("username").getValue().toString();
                         x.putExtra("nama_pelelang",nama_pelelang);
                         x.putExtra("id_pelelang",mAuth.getCurrentUser().getUid()+"");
                         startActivity(x);
                     }

                     @Override
                     public void onCancelled(DatabaseError databaseError) {

                     }
                 });


             }
         });

         methodFirebase = new MethodFirebase(mContext);


         setupFirebaseAuth();
        //Database query untuk dapet lelang
        final DatabaseReference lelang = database.child("lelang");
        lelang.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listLelang.clear();

                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    Log.d("asooy", "onDataChange: "+ds.getKey());

                        String key = ds.getKey();
                        String nama_barang = ds.child("nama_barang").getValue().toString();
                        String akhir_bid = ds.child("akhir_bid").getValue().toString();
                        String awal_bid = ds.child("awal_bid").getValue().toString();
                        String id_pelelang = ds.child("id_pelelang").getValue().toString();
                        String gambar = ds.child("gambar").getValue().toString();
                        String deskripsi = ds.child("deskripsi").getValue().toString();
                        String harga_awal = ds.child("harga_awal").getValue().toString();
                        String harga_akhir = ds.child("harga_akhir").getValue().toString();
                        String nama_pelelang = ds.child("nama_pelelang").getValue().toString();
                        listLelang.add(new LelangModel(key, nama_barang,  akhir_bid,  awal_bid,  id_pelelang,  gambar,  deskripsi,  harga_awal,  harga_akhir, nama_pelelang));

                }

                homeAdapter = new HomeAdapter(listLelang, getActivity());
                rvhome.setAdapter(homeAdapter);
                homeAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

    private void setupFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
//        Log.d("authnotif", "setupFirebaseAuth: "+currentFirebaseUser.toString());
        DatabaseReference mref = database.child("user").child(currentFirebaseUser.getUid());
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user!=null){
                    //user masuk
                } else {
                    //user keluar
                }
            }
        };

        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //setWidget(methodFirebase.deploydata(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
