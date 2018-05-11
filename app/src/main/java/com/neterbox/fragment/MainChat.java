package com.neterbox.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.neterbox.ChatModule;
import com.neterbox.FreindProfile;
import com.neterbox.R;
import com.neterbox.customadapter.PackageChatAdapter.OneToOneChatAdapter;
import com.neterbox.customadapter.PagerAdapter;
import com.neterbox.jsonpojo.AddChat.AddChat;
import com.neterbox.jsonpojo.chatlist.ChatList;
import com.neterbox.jsonpojo.chatlist.ChatListDatum;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.utils.Constants;
import com.neterbox.utils.Sessionmanager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.neterbox.R.id.pager_header;
import static com.neterbox.utils.Sessionmanager.index;

public class MainChat extends Fragment implements View.OnClickListener,TabHost.OnTabChangeListener{

    Activity context;
    private Toolbar toolbar;
    ViewPager viewPager;
    ListView groupchat, onechat;
    String index="1";
    ViewPagerAdapter adapter;
    Sessionmanager sessionmanager;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        sessionmanager = new Sessionmanager(context);
        View view = inflater.inflate(R.layout.fragment_main_chat, container, false);
        call_Chatlist(sessionmanager.getValue(Sessionmanager.Id),index);
        TabLayout tabLayout = (TabLayout)view.findViewById(pager_header);
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));

//        ViewPager viewPager = (ViewPager)view.findViewById(R.id.viewpager);
////        final FragmentPagerAdapter adapter = new PagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
////        viewPager.setAdapter(adapter);

        viewPager = (ViewPager)view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnAdapterChangeListener(new ViewPager.OnAdapterChangeListener() {
            @Override
            public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable android.support.v4.view.PagerAdapter pagerAdapter, @Nullable android.support.v4.view.PagerAdapter pagerAdapter1) {

            }
        });


        return view;
    }
    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new FragmentOneToOne(), "One-on-One");
        adapter.addFragment(new GroupChatFragment(), "Group");
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onTabChanged(String s) {
        String tabs = null; {
            TabActivity tHost = null;
            if (tabs.equals("first")) {
                ChatModule.iright.setVisibility(View.INVISIBLE);
                tHost.getTabWidget().getChildAt(0) .setBackgroundResource(R.drawable.tab_selector);
                tHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.BLUE);
                tHost.getTabWidget().getChildAt(2).setBackgroundColor(Color.BLUE);
            }
            else if (tabs.equals("second")) {
                ChatModule.iright.setVisibility(View.VISIBLE);
                tHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.tab_selector);
                tHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.WHITE);
                tHost.getTabWidget().getChildAt(2).setBackgroundColor(Color.WHITE);
            }
        }
    }

    @Override
    public void onClick(View v) {

    }


//    @Override
//    public void onClick(View v) {
//        String tabs = null;
//        if(tabs.equals("first")) {
//            new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            };
//        }
//    }

    //   pager_header

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

    // TODO : API CALLING CHATLIST
    public void call_Chatlist(String sender_id,String index) {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Please Wait...");
        dialog.show();
        Call<ChatList> chatCall = apiInterface.chatlistpojo(sender_id,index);

        chatCall.enqueue(new Callback<ChatList>()
        {
            @Override
            public void onResponse(Call<ChatList> call, Response<ChatList> response)
            {
                dialog.dismiss();
                if (response.body().getStatus().equals("Success"))
                {
                    if(Constants.one!=null)
                        Constants.one.clear();
                    if(Constants.group !=null)
                        Constants.group .clear();

                    for (int i = 0; i < response.body().getData().size(); i++)
                    {
                        if(response.body().getData().get(i).getTblDailog().getDialogType().equalsIgnoreCase("private"))
                        {
                            Constants.one.add(response.body().getData().get(i));
                        }
                        else
                        {
                            Constants.group.add(response.body().getData().get(i));
                        }
                    }
                    setupViewPager(viewPager);
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ChatList> call, Throwable t)
            {
                dialog.dismiss();
                Toast.makeText(context,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
