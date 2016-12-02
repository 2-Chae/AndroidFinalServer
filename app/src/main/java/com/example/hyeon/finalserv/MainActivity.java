package com.example.hyeon.finalserv;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager mViewPager;


    GetSyInfo getSyInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        //탭 추가
        tabLayout.addTab(tabLayout.newTab().setText("강의정보 관리"));//.setIcon(R.drawable.book_tab));
        tabLayout.addTab(tabLayout.newTab().setText("시험시간표 관리"));//.setIcon(R.drawable.stats_tab));

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {            }
        });
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        int tabCount;

        public SectionsPagerAdapter(FragmentManager fm, int tabNum) {
            super(fm);
            tabCount = tabNum;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);
            switch (position) {
                case 0:
                    return new SugangFrag();
                case 1:
                    return new ExamFrag();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return this.tabCount;
        }
    }

}
