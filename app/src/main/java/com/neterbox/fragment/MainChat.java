package com.neterbox.fragment;

import android.app.TabActivity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.Toolbar;

import com.neterbox.FreindProfile;
import com.neterbox.R;
import com.neterbox.customadapter.PagerAdapter;
import com.neterbox.jsonpojo.AddChat.AddChat;
import com.neterbox.jsonpojo.chatlist.ChatList;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainChat extends Fragment implements View.OnClickListener,TabHost.OnTabChangeListener{

    Context context;
    private Toolbar toolbar;
    private ViewPager viewPager;
    ListView groupchat, onechat;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getContext();
        View view = inflater.inflate(R.layout.fragment_main_chat, container, false);
        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.pager_header);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));

        ViewPager viewPager = (ViewPager)view.findViewById(R.id.viewpager);
        final FragmentPagerAdapter adapter = new PagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager = (ViewPager)view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new FragmentOneToOne(), "One-on-One");
        adapter.addFragment(new GroupChatFragment(), "Group");
        viewPager.setAdapter(adapter);
    }
    @Override
    public void onTabChanged(String s) {
        String tabs = null; {
            TabActivity tHost = null;
            if (tabs.equals("first")) {
                tHost.getTabWidget().getChildAt(0) .setBackgroundResource(R.drawable.tab_selector);
                tHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.BLUE);
                tHost.getTabWidget().getChildAt(2).setBackgroundColor(Color.BLUE);
            }
            else if (tabs.equals("second")) {
                tHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.tab_selector);
                tHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.WHITE);
                tHost.getTabWidget().getChildAt(2).setBackgroundColor(Color.WHITE);
            }
        }
    }
    @Override
    public void onClick(View v) {

    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
