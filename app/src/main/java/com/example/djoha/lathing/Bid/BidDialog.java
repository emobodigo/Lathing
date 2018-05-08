package com.example.djoha.lathing.Bid;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.djoha.lathing.Model.BidModel;
import com.example.djoha.lathing.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BidDialog extends AppCompatDialogFragment {

    EditText et_bid;
    TextView tv_highestPrice;
    BidDialogListener bidDialogListener;

    String bidPrice;
    String id_lelang, id_penawar, nama_penawar;
    int hp;

    public BidDialog() {

    }

    @SuppressLint("ValidFragment")
    public BidDialog(String id_lelang, String id_penawar, String nama_penawar, int hp) {
        this.id_lelang = id_lelang;
        this.id_penawar = id_penawar;
        this.nama_penawar = nama_penawar;
        this.hp = hp;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_bid_dialog, null);

        et_bid = view.findViewById(R.id.bid_dialog_et_bidPrice);
        tv_highestPrice = view.findViewById(R.id.bid_dialog_tv_highestPrice);
        tv_highestPrice.setText(hp+"");

        //dialog bid
        builder.setView(view)
                .setTitle("Input Your Bid")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bidPrice = et_bid.getText().toString();

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());

                        //dialog confirmation
                        builder1.setMessage("Are you sure you want to bid Rp."+ bidPrice +" ?")
                                .setCancelable(false)
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        bidDialogListener.applyTexts(bidPrice);
                                    }
                                });

                        builder1.create().show();
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            bidDialogListener = (BidDialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "implement BidDialogListener dulu");
        }
    }

    public void update_price(String jumlah_bid){
        Log.d("update coy", jumlah_bid);
        tv_highestPrice.setText(jumlah_bid);
    }

    public interface BidDialogListener{
        void applyTexts(String bidPrice);
    }
}
