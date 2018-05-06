package com.example.djoha.lathing.Profil;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.djoha.lathing.R;
import com.example.djoha.lathing.Utils.SectionStatePagerAdapter;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {

    private SectionStatePagerAdapter sectionStatePagerAdapter;
    private ViewPager viewPager;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        relativeLayout = (RelativeLayout)findViewById(R.id.rel1);
        SetupSettingList();
        setupFragment();

        ImageView img = (ImageView)findViewById(R.id.backbutton);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void setupFragment(){
        sectionStatePagerAdapter = new SectionStatePagerAdapter(getSupportFragmentManager());
        sectionStatePagerAdapter.addFragment(new EditProfileFragment(), getString(R.string.editprofile));
        sectionStatePagerAdapter.addFragment(new SignOutFragment(),getString(R.string.signout));

    }

    private void setViewPager(int fragmentNumber){
        relativeLayout.setVisibility(View.GONE);
        viewPager.setAdapter(sectionStatePagerAdapter);
        viewPager.setCurrentItem(fragmentNumber);
    }

    private void SetupSettingList(){
        ListView listView = (ListView)findViewById(R.id.lvsetting);
        ArrayList<String> option = new ArrayList<>();
        option.add(getString(R.string.editprofile));
        option.add(getString(R.string.signout));

        ArrayAdapter arrayAdapter = new ArrayAdapter(SettingActivity.this, android.R.layout.simple_list_item_1, option);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setViewPager(position);
            }
        });
    }
}
