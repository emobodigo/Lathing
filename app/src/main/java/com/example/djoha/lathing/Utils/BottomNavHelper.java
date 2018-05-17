package com.example.djoha.lathing.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;


import com.example.djoha.lathing.Home.MainActivity;
import com.example.djoha.lathing.R;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class BottomNavHelper {

    public static void SetupNavBar(BottomNavigationViewEx bottomNavigationViewEx){
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }

    public static void enableNavigation(final Context context, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_coin:
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                        ((Activity)context).finish();
                        break;
                    case R.id.ic_feed:
//                        Intent intent1 = new Intent(context, FeedActivity.class);
//                        context.startActivity(intent1);
//                        ((Activity)context).finish();
                        break;
                    case R.id.ic_profil:
//                        Intent intent2 = new Intent(context, ProfileActivity.class);
//                        context.startActivity(intent2);
//                        ((Activity)context).finish();
                        break;
                }
                return false;
            }
        });
    }
}
