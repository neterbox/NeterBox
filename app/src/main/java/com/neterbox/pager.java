package com.neterbox;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by DeLL on 17-01-2018.
 */

public class pager extends FragmentStatePagerAdapter {

    int tabCount;

    public pager(FragmentManager fm,int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                contacts_one_to_one tab1 = new contacts_one_to_one();
                return tab1;
            case 1:
                contact_group_chat tab2 = new contact_group_chat();
                return tab2;

            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return tabCount;
    }
}
