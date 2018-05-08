package com.example.djoha.lathing.Home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.djoha.lathing.Model.LelangModel;
import com.example.djoha.lathing.Model.User;
import com.example.djoha.lathing.Profil.SettingActivity;
import com.example.djoha.lathing.R;
import com.example.djoha.lathing.Utils.MethodFirebase;
import com.example.djoha.lathing.Utils.ProfileAdapter;
import com.example.djoha.lathing.Utils.UniversalImageLoader;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {

    private static final int ACTIVITY_NUM = 2;
    private Context mContext = getActivity();
    private MethodFirebase methodFirebase;
    RecyclerView rvprofile;
    ImageView cimg;
    TextView nama,kontak,post,username,alamat;
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    ProfileAdapter profileAdapter;
    LelangModel lelangModel;
    List<LelangModel> listLelang = new ArrayList<>();
    final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.profile_fragment_layout, container, false);
        nama = (TextView)view.findViewById(R.id.tvnama);
        kontak = (TextView)view.findViewById(R.id.tvkontak);
        post = (TextView) view.findViewById(R.id.tvpost);
        username = (TextView) view.findViewById(R.id.username);
        cimg = (ImageView)view.findViewById(R.id.fotoprofil);
        alamat = (TextView)view.findViewById(R.id.alamat);

        rvprofile = (RecyclerView)view.findViewById(R.id.recyclerpost);
        rvprofile.setHasFixedSize(true);
        rvprofile.setLayoutManager(new LinearLayoutManager(mContext));

        methodFirebase = new MethodFirebase(mContext);

        setupFirebaseAuth();
        SetupToolbar(view);

        //Database query untuk dapet lelang
        final DatabaseReference lelang = database.child("lelang");
        lelang.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listLelang.clear();

                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    Log.d("asooy", "onDataChange: "+ds.getKey());
                    if(mAuth.getCurrentUser().getUid().equals(ds.child("id_pelelang").getValue().toString())){
                        String nama_barang = ds.child("nama_barang").getValue().toString();
                        String akhir_bid = ds.child("akhir_bid").getValue().toString();
                        String awal_bid = ds.child("awal_bid").getValue().toString();
                        String id_pelelang = ds.child("id_pelelang").getValue().toString();
                        String gambar = ds.child("gambar").getValue().toString();
                        String deskripsi = ds.child("deskripsi").getValue().toString();
                        String harga_awal = ds.child("harga_awal").getValue().toString();
                        String harga_akhir = ds.child("harga_akhir").getValue().toString();
                        String nama_pelelang = ds.child("nama_pelelang").getValue().toString();

                        listLelang.add(new LelangModel(nama_barang,  akhir_bid,  awal_bid,  id_pelelang,  gambar,  deskripsi,  harga_awal,  harga_akhir, nama_pelelang));
                    }
                }

                profileAdapter = new ProfileAdapter(listLelang, getActivity());
                rvprofile.setAdapter(profileAdapter);
                profileAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






        return view;
    }

    private void SetupToolbar(View view){
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar_top_profile);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        ImageView img = (ImageView)view.findViewById(R.id.settingbutton);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);

            }
        });
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
                setWidget(methodFirebase.deploydata(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setWidget(User user){
        UniversalImageLoader.setImage(user.getFoto(),cimg,null,"");
        username.setText(String.valueOf(user.getUsername()));
        nama.setText(String.valueOf(user.getNama()));
        kontak.setText(String.valueOf(user.getKontak()));
        alamat.setText(String.valueOf(user.getAlamat()));
        post.setText(String.valueOf(user.getPost()));
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
