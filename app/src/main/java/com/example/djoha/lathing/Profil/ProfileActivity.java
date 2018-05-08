package com.example.djoha.lathing.Profil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.djoha.lathing.Model.Lelang;
import com.example.djoha.lathing.Model.User;
import com.example.djoha.lathing.R;
import com.example.djoha.lathing.Utils.BottomNavHelper;
import com.example.djoha.lathing.Utils.MethodFirebase;
import com.example.djoha.lathing.Utils.UniversalImageLoader;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    private static final int ACTIVITY_NUM = 2;
    private Context mContext = ProfileActivity.this;
    private MethodFirebase methodFirebase;
    RecyclerView rvprofile;
    ImageView cimg;
    TextView nama,kontak,post,username,alamat;
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nama = (TextView)findViewById(R.id.tvnama);
        kontak = (TextView)findViewById(R.id.tvkontak);
        post = (TextView) findViewById(R.id.tvpost);
        username = (TextView) findViewById(R.id.username);
        cimg = (ImageView)findViewById(R.id.fotoprofil);
        alamat = (TextView)findViewById(R.id.alamat);

        rvprofile = (RecyclerView)findViewById(R.id.recyclerpost);
        rvprofile.setHasFixedSize(true);
        rvprofile.setLayoutManager(new LinearLayoutManager(this));
        rvprofile.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        methodFirebase = new MethodFirebase(mContext);

        Query query = firebaseDatabase.getReference("lelang");
        FirebaseRecyclerOptions<Lelang> options = new FirebaseRecyclerOptions.Builder<Lelang>().setQuery(query,Lelang.class).build();
        adapter = new FirebaseRecyclerAdapter<Lelang, BarangViewsHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull BarangViewsHolder holder, int position, @NonNull Lelang model) {
                holder.setNamabrg(model.getNama_barang());
                holder.setHargas(model.getHarga_akhir());
                holder.setIMG(model.getGambar());
            }

            @NonNull
            @Override
            public BarangViewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View  view = LayoutInflater.from(mContext).inflate(R.layout.list_post_profile,null);
                return new  BarangViewsHolder(view);
            }
        };
        rvprofile.setAdapter(adapter);

        NavigationView();
        SetupToolbar();
        setupFirebaseAuth();


    }

    private void SetupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_top_profile);
        setSupportActionBar(toolbar);

        ImageView img = (ImageView)findViewById(R.id.settingbutton);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SettingActivity.class);
                startActivity(intent);

            }
        });
    }

    private void NavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomnav);
        BottomNavHelper.SetupNavBar(bottomNavigationViewEx);
        BottomNavHelper.enableNavigation(ProfileActivity.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    private void setupFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
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
        databaseReference.addValueEventListener(new ValueEventListener() {
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
        user = new User();
        UniversalImageLoader.setImage(user.getFoto(),cimg,null,"");
        username.setText(user.getUsername());
        nama.setText(user.getNama());
        kontak.setText(user.getKontak());
        alamat.setText(user.getAlamat());
        post.setText(String.valueOf(user.getPost()));
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
        adapter.stopListening();
    }

    public static class BarangViewsHolder extends RecyclerView.ViewHolder{
        View mView;
        public BarangViewsHolder(View itemView){
            super(itemView);
            mView = itemView;
        }
        public void setNamabrg(String title){
            TextView namabarangs = (TextView)mView.findViewById(R.id.namabarang);
            namabarangs.setText(title);
        }
        public void setHargas(String hargas){
            TextView harga1 = (TextView)mView.findViewById(R.id.hargatinggi);
            harga1.setText(hargas);
        }
        public void setIMG(String image){
            ImageView img = (ImageView)mView.findViewById(R.id.gambarpost);
            Picasso.get().load(image).into(img);
        }
    }


}
