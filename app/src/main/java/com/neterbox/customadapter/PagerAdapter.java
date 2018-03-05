package com.neterbox.customadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.neterbox.fragment.FragmentOneToOne;
import com.neterbox.fragment.GroupChatFragment;

/**
 * Created by sejal on 1/22/2018.
 */

public class PagerAdapter extends FragmentPagerAdapter {
    private final int NumOfTabs = 0;
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                GroupChatFragment tab1 = new GroupChatFragment();
                return tab1;
            case 1:
                FragmentOneToOne tab2 = new FragmentOneToOne();
                return tab2;
            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
