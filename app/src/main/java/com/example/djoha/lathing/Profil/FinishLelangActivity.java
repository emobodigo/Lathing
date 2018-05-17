package com.example.djoha.lathing.Profil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.djoha.lathing.Utils.BidHistoryAdapter;
import com.example.djoha.lathing.Model.BidModel;
import com.example.djoha.lathing.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FinishLelangActivity extends AppCompatActivity {

    final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private RecyclerView recyclerView;
    private BidHistoryAdapter adapter;

    TextView tv_namaBarang, tv_highestBid, tv_expDate;
    Button btn_finishAuction;
    ImageView backbutton;

    String key_lelang, nama_pelelang;
    int hp;
    ArrayList<BidModel> bidModelList = new ArrayList<>();

    public FinishLelangActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_lelang);

        tv_namaBarang = findViewById(R.id.finish_tv_namaItem);
        tv_highestBid = findViewById(R.id.finish_tv_highprice);
        tv_expDate = findViewById(R.id.finish_tv_expDate);
        btn_finishAuction = findViewById(R.id.finish_btn_finish);
        recyclerView = findViewById(R.id.finishh_rv_bidhistory);
        backbutton = findViewById(R.id.backbutton_finish);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                key_lelang = null;
            } else {
                key_lelang = extras.getString("key");
                nama_pelelang = extras.getString("nama_pelelang");
            }
        }

        //ngisi info post
        DatabaseReference lelang = database.child("lelang").child(key_lelang);
        lelang.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tv_namaBarang.setText(dataSnapshot.child("nama_barang").getValue().toString());
                tv_expDate.setText(dataSnapshot.child("akhir_bid").getValue().toString());

                if (!dataSnapshot.child("harga_akhir").getValue().toString().equalsIgnoreCase("-")) {
                    btn_finishAuction.setEnabled(false);
                    btn_finishAuction.setBackgroundColor(getResources().getColor(R.color.grey));
                    btn_finishAuction.setText("Auction is Over");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //ngisi bid history
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

                    LinearLayoutManager layoutManager = new LinearLayoutManager(FinishLelangActivity.this);
                    layoutManager.setReverseLayout(true);
                    layoutManager.setStackFromEnd(true);
                    recyclerView.setLayoutManager(layoutManager);
                    adapter = new BidHistoryAdapter(bidModelList, FinishLelangActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    try {
                        hp = bidModelList.get(bidModelList.size() - 1).getJumlah_bid();
                    } catch (ArrayIndexOutOfBoundsException e) {
                        hp = 0;
                    }

                    tv_highestBid.setText(hp + "");

                } catch (NullPointerException e) {
                    Toast.makeText(FinishLelangActivity.this, "Data bid masih kosong", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btn_finishAuction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(FinishLelangActivity.this);

                //dialog confirmation
                builder1.setMessage("Are you sure ?")
                        .setCancelable(false)
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Map<String, Object> link = new HashMap<String, Object>();
                                link.put("/lelang/" + key_lelang + "/harga_akhir", String.valueOf(hp));

//                                Log.d("dialog", id_pelelang + " " + bidPrice + " " + nama_pelelang + " " + currentTime);
                                database.updateChildren(link);
                            }
                        });

                builder1.create().show();

            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

}
