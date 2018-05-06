package com.example.djoha.lathing.Bid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.djoha.lathing.Model.BidModel;
import com.example.djoha.lathing.Model.LelangModel;
import com.example.djoha.lathing.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BidActivity extends AppCompatActivity implements BidDialog.BidDialogListener {

    private RecyclerView recyclerView;
    private BidHistoryAdapter adapter;
    private DatabaseReference mDatabase, mDatabaseChild;

    LelangModel lelangModel;


    ScrollView scrollView;
    Button bid_btn_bid;
    TextView tv_username, tv_namaBarang, tv_deskripsi, tv_exp;
    ImageView iv_userPhoto, iv_itemPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid);

        tv_username = findViewById(R.id.bid_tv_username);
        tv_namaBarang = findViewById(R.id.bid_tv_namaItem);
        tv_deskripsi = findViewById(R.id.bid_tv_descItem);
        tv_exp = findViewById(R.id.bid_tv_expDate);
        iv_userPhoto = findViewById(R.id.bid_iv_userPhoto);
        iv_itemPhoto = findViewById(R.id.bid_iv_itemPhoto);

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("lelang");

        DatabaseReference lelang = ref.child("1");
        lelang.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        bid_btn_bid = findViewById(R.id.bid_btn_bid);
        bid_btn_bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBidDialog();
            }
        });

        recyclerView = findViewById(R.id.bidHistory_rv_history);
        adapter = new BidHistoryAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(BidActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    public void openBidDialog(){
        BidDialog bidDialog = new BidDialog();
        bidDialog.show(getSupportFragmentManager(), "Bid Dialog");
    }

    @Override
    public void applyTexts(String bidPrice) {
        //Diisi sama method untuk pengisian ke Firebase
    }
}
