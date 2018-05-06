package com.example.djoha.lathing.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SectionStatePagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final HashMap<Fragment, Integer> mFragment = new HashMap<>();
    private final HashMap<String, Integer> mFragmentNumber = new HashMap<>();
    private final HashMap<Integer, String> mFragmentName = new HashMap<>();

    public SectionStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment (Fragment fragment, String fragmentName){
        fragmentList.add(fragment);
        mFragment.put(fragment, fragmentList.size()-1);
        mFragmentNumber.put(fragmentName, fragmentList.size()-1);
        mFragmentName.put(fragmentList.size()-1, fragmentName);
    }

    public Integer getFragmentNumber(String framentName){
        if (mFragmentNumber.containsKey(framentName)){
            return mFragmentNumber.get(framentName);
        } else {
            return null;
        }
    }

    public Integer getFragmentNumber(Fragment fragment){
        if (mFragmentNumber.containsKey(fragment)){
            return mFragmentNumber.get(fragment);
        } else {
            return null;
        }
    }

    public String getFragmentName(Integer fragmentNumber){
        if (mFragmentName.containsKey(fragmentNumber)){
            return mFragmentName.get(fragmentNumber);
        } else {
            return null;
        }
    }
}
