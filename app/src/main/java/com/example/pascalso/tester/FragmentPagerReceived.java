package com.example.pascalso.tester;


import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Pascal So on 7/13/2015.
 */
public class FragmentPagerReceived extends FragmentActivity {
    static final int NUM_TABS = 2;
    MyAdapter mAdapter;
    ViewPager mPager;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);
        final ActionBar actionBar = getActionBar();
        mAdapter = new MyAdapter(getSupportFragmentManager());
        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener(){
            public void onPageSelected(int position){
                getActionBar().setSelectedNavigationItem(position);
            }
        });
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener(){
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft){
                mPager.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft){

            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft){

            }
        };
        actionBar.addTab(actionBar.newTab().setText("Sent Problems").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Received Problems").setTabListener(tabListener));
    }

    public static class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm){
            super(fm);
        }

        public int getCount(){
            return NUM_TABS;
        }

        @Override
        public Fragment getItem(int position){
            return ArrayListFragment.newInstance(position);
        }

    }

    public static class ArrayListFragment extends ListFragment {
        int mNum;

        static ArrayListFragment newInstance(int num){
            ArrayListFragment f = new ArrayListFragment();
            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);
            return f;
        }

        public void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            View v = inflater.inflate(R.layout.activity_fragment_pager_list, container, false);
            return v;
        }

        public void onActivityCreated(Bundle savedInstanceState){
            super.onActivityCreated(savedInstanceState);
            setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1));
        }

        public void onListItemClick(ListView l, View v, int position, long id){

        }
    }
}
