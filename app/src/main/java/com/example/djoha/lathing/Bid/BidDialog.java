package com.example.djoha.lathing.Bid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.djoha.lathing.R;

public class BidDialog extends AppCompatDialogFragment {

    EditText et_bid;
    BidDialogListener bidDialogListener;
    String bidPrice;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_bid_dialog, null);

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

        et_bid = view.findViewById(R.id.bid_dialog_et_bidPrice);

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

    public interface BidDialogListener{
        void applyTexts(String bidPrice);
    }
}
