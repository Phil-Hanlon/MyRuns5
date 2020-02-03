package com.example.myruns3;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ImpFragmentPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragments;

    public ImpFragmentPagerAdapter(FragmentManager fragmentManager, ArrayList<Fragment> fragments) {

        super(fragmentManager);

        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {

        return fragments.get(position);
    }

    @Override
    public int getCount() {

        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        if( position == 0 ) {

            return "START";
        }
        else if( position == 1 ) {

            return "HISTORY";
        }
        else if( position == 2 ) {

            return "SETTINGS";
        }
        else {

            return "FRAGMENT_NAME_ERROR";
        }
    }
}
