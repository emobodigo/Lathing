package com.example.djoha.lathing.Bid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.djoha.lathing.Model.BidModel;
import com.example.djoha.lathing.R;
import com.example.djoha.lathing.Utils.BidHistoryAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BidActivity extends AppCompatActivity implements BidDialog.BidDialogListener {

    private RecyclerView recyclerView;
    private BidHistoryAdapter adapter;
    final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    //    LelangModel lelangModel;
    BidDialog bidDialog;
    List<BidModel> bidModelList = new ArrayList<>();
    List<BidModel> bidModelListReverse = new ArrayList<>();

    //dummy
    String id_pelelang = "1";
    String nama_pelelang = "kelvin";
    String key_lelang;

    String gambar_lelang;

    String id_lelang;
    int hp;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    final StorageReference storageRef = storage.getReference();

    ScrollView scrollView;
    Button bid_btn_bid;
    TextView tv_username, tv_namaBarang, tv_deskripsi, tv_exp, tv_highprice;
    ImageView iv_itemPhoto, backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid);

        tv_username = findViewById(R.id.bid_tv_username);
        tv_namaBarang = findViewById(R.id.bid_tv_namaItem);
        tv_deskripsi = findViewById(R.id.bid_tv_descItem);
        tv_exp = findViewById(R.id.bid_tv_expDate);
        iv_itemPhoto = findViewById(R.id.bid_iv_itemPhoto);
        tv_highprice = findViewById(R.id.bid_tv_highprice);
        backbutton = findViewById(R.id.backbutton);


        recyclerView = findViewById(R.id.bidHistory_rv_history);
        LinearLayoutManager layoutManager = new LinearLayoutManager(BidActivity.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                key_lelang = null;
            } else {
                key_lelang = extras.getString("key");
                nama_pelelang = extras.getString("nama_pelelang");
                gambar_lelang = extras.getString("foto");
            }
        }

        StorageReference gallery = storageRef.child(""+gambar_lelang);
        Glide.with(getApplicationContext())
                .using(new FirebaseImageLoader())
                .load(gallery)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv_itemPhoto);

        DatabaseReference ref = database.child("lelang");

        //ngambil data lelang
        DatabaseReference lelang = ref.child(key_lelang);
        lelang.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tv_username.setText(dataSnapshot.child("nama_pelelang").getValue().toString());
                tv_namaBarang.setText(dataSnapshot.child("nama_barang").getValue().toString());
                tv_deskripsi.setText(dataSnapshot.child("deskripsi").getValue().toString());
                tv_exp.setText(dataSnapshot.child("akhir_bid").getValue().toString());
                id_lelang = dataSnapshot.getKey();

                if (!dataSnapshot.child("harga_akhir").getValue().toString().equalsIgnoreCase("-")) {
                    bid_btn_bid.setEnabled(false);
                    bid_btn_bid.setBackgroundColor(getResources().getColor(R.color.grey));
                    bid_btn_bid.setText("Auction is Over");
                }
//                Log.d("id_lelang", id_lelang+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //ngambil data history bid
        DatabaseReference bid = lelang.child("bid");
        bid.orderByChild("jumlah_bid").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                bidModelList.clear();
                try {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String id_bid = ds.getKey();
                        int jumlah_bid = Integer.parseInt(ds.child("jumlah_bid").getValue().toString());
                        String waktu_bid = ds.child("waktu_bid").getValue().toString();
                        String nama_penawar = ds.child("nama_penawar").getValue().toString();
                        bidModelList.add(new BidModel(id_bid, nama_penawar, waktu_bid, jumlah_bid));
                    }


                    adapter = new BidHistoryAdapter(bidModelList, BidActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    try {
                        hp = bidModelList.get(bidModelList.size() - 1).getJumlah_bid();
                    } catch (ArrayIndexOutOfBoundsException e) {
                        hp = 0;
                    }

                    tv_highprice.setText(hp + "");
                    if (bidDialog != null) {
                        bidDialog.update_price(hp + "");
                    }

                } catch (NullPointerException e) {
                    Toast.makeText(BidActivity.this, "Data bid masih kosong", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //btn bid dipencet
        bid_btn_bid = findViewById(R.id.bid_btn_bid);
        bid_btn_bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBidDialog();
            }
        });

    }

    public void openBidDialog() {
        bidDialog = new BidDialog(id_lelang, id_pelelang, nama_pelelang, hp);
        bidDialog.show(getSupportFragmentManager(), "Bid Dialog");
    }

    @Override
    public void applyTexts(String bidPrice) {
        String key = database.push().getKey();
        Date today = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String currentTime = formatter.format(today);

        Map<String, Object> link = new HashMap<String, Object>();
        link.put("/lelang/" + id_lelang + "/bid/" + key + "/id_penawar", id_pelelang);
        link.put("/lelang/" + id_lelang + "/bid/" + key + "/jumlah_bid", Integer.parseInt(bidPrice));
        link.put("/lelang/" + id_lelang + "/bid/" + key + "/nama_penawar", nama_pelelang);
        link.put("/lelang/" + id_lelang + "/bid/" + key + "/waktu_bid", currentTime);

        Log.d("dialog", id_pelelang + " " + bidPrice + " " + nama_pelelang + " " + currentTime);
        database.updateChildren(link);
    }
}
