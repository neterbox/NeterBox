package com.neterbox;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.RequiresPermission;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toolbar;

import com.neterbox.customadapter.ChatAdapter.ConntactForGroupChatAdapter;
import com.neterbox.customadapter.PagerAdapter;
import com.neterbox.fragment.GroupChatFragment;
import com.neterbox.fragment.OneToOneFragment;

import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
public class ContactsForChatActivityNew extends AppCompatActivity implements View.OnClickListener,TabHost.OnTabChangeListener {
=======
public class ContactsForChatActivityNew extends AppCompatActivity {
>>>>>>> a0de5c62a9502ef0dea4c15546a815d39399a20f

    Context context;

    private Toolbar toolbar;
    private ViewPager viewPager;
    //    Button boneononechat;
    ImageView ileft, iright, ichat, icircle, iplay;
    TextView title;
    ListView groupchat, onechat;
    RelativeLayout bottom_layout;
    public ContactsForChatActivityNew contactsforChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_for_chat_new);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.pager_header);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

<<<<<<< HEAD
        context=ContactsForChatActivityNew.this;
=======
        context = ContactsForChatActivityNew.this;
>>>>>>> a0de5c62a9502ef0dea4c15546a815d39399a20f
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);

        contactsforChat = this;

<<<<<<< HEAD
        Resources res = getResources();

=======
>>>>>>> a0de5c62a9502ef0dea4c15546a815d39399a20f
        ileft = (ImageView) findViewById(R.id.ileft);
        iright = (ImageView) findViewById(R.id.iright);
        title = (TextView) findViewById(R.id.title);
        ileft.setImageResource(R.drawable.home);
        iright.setImageResource(R.drawable.plus);
        ichat = (ImageView) findViewById(R.id.ichat);
        icircle = (ImageView) findViewById(R.id.icircle);
        iplay = (ImageView) findViewById(R.id.iplay);
        groupchat = (ListView) findViewById(R.id.groupchat);
        onechat = (ListView) findViewById(R.id.onechat);
        bottom_layout = (RelativeLayout) findViewById(R.id.bottom_layout);
        title.setText("Contacts");

        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ContactsForChatActivityNew.this, HomePage.class);
                startActivity(i);
                finish();
            }
        });
        iright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ContactsForChatActivityNew.this, Create_group.class);
                startActivity(i);
                finish();
            }
        });
<<<<<<< HEAD

        ichat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ContactsForChatActivityNew.this, ContactsForChatActivityNew.class);
                startActivity(i);
                finish();
            }
        });
=======
//
//        ichat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(ContactsForChatActivityNew.this, ContactsForChatActivityNew.class);
//                startActivity(i);
//                finish();
//            }
//        });
>>>>>>> a0de5c62a9502ef0dea4c15546a815d39399a20f

        icircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ContactsForChatActivityNew.this, Circles.class);
                startActivity(i);
                finish();
            }
        });

        iplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ContactsForChatActivityNew.this, PlayGridview.class);
                startActivity(i);
                finish();
            }
        });
    }
<<<<<<< HEAD
=======

>>>>>>> a0de5c62a9502ef0dea4c15546a815d39399a20f
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneToOneFragment(), "One-on-One");
        adapter.addFragment(new GroupChatFragment(), "Group");
        viewPager.setAdapter(adapter);
    }

<<<<<<< HEAD
    @Override
    public void onClick(View view) {
        bottom_layout.setBackgroundColor(R.drawable.loginbluebox);


    }

    @Override
    public void onTabChanged(String s) {
          String tabs = null; {
            TabActivity tHost = null;
            if (tabs.equals("first")) { tHost.getTabWidget().getChildAt(0) .setBackgroundResource(R.drawable.tab_selector);
            tHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.WHITE);
            tHost.getTabWidget().getChildAt(2).setBackgroundColor(Color.WHITE);
            }
            else if (tabs.equals("second"))
            {
                tHost.getTabWidget().getChildAt(1) .setBackgroundResource(R.drawable.tab_selector);
            } tHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.WHITE); tHost.getTabWidget().getChildAt(2).setBackgroundColor(Color.WHITE);
        }
    }
=======
//    @Override
//    public void onTabChanged(String s) {
//          String tabs = null; {
//            TabActivity tHost = null;
//            if (tabs.equals("first"))
//            {
//                //tHost.getTabWidget().getChildAt(1) .setBackgroundResource(R.drawable.tab_selector);
//           // tHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.WHITE);
//          //  tHost.getTabWidget().getChildAt(2).setBackgroundColor(Color.WHITE);
//            }
//            else if (tabs.equals("second")) {
//               // tHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.tab_selector);
//              //  tHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.BLUE);
//                // tHost.getTabWidget().getChildAt(2).setBackgroundColor(Color.WHITE);
//            }
//        }
//    }
>>>>>>> a0de5c62a9502ef0dea4c15546a815d39399a20f

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

    @Override
    public void onBackPressed() {
        Intent i=new Intent(ContactsForChatActivityNew.this,HomePage.class);
        startActivity(i);
        finish();
    }
}