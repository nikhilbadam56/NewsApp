package com.example.triveousassignment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MaterialToolbar topappbar;

    //fragment declarations
    nbc_news_fragment nbc_news_fragment;
    cnn_fragment cnn_fragment;
    google_news google_news;
    associated_press_fragment associated_press_fragment;
    entertainment_weekly_fragment entertainment_weekly_fragment;
    bbc_news_fragment bbc_news_fragment;

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topappbar = findViewById(R.id.topAppbar);
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);

        nbc_news_fragment = new nbc_news_fragment();
        cnn_fragment = new cnn_fragment();
        google_news = new google_news();
        associated_press_fragment = new associated_press_fragment();
        entertainment_weekly_fragment = new entertainment_weekly_fragment();
        bbc_news_fragment = new bbc_news_fragment();

        setSupportActionBar(topappbar);

        tabLayout.setupWithViewPager(viewPager); //tablyout for displaying the different news sources.

        //viewpager adapter
        Viewpageradapter viewpageradapter = new Viewpageradapter(getSupportFragmentManager(),0);
        viewpageradapter.addfragment(nbc_news_fragment,"NBC");
        viewpageradapter.addfragment(cnn_fragment,"CNN");
        viewpageradapter.addfragment(google_news,"Google News");
        viewpageradapter.addfragment(associated_press_fragment,"AssociatedPress");
        viewpageradapter.addfragment(entertainment_weekly_fragment,"EntertainmentWeekly");
        viewpageradapter.addfragment(bbc_news_fragment,"BBC News");
        viewPager.setAdapter(viewpageradapter);

    }

    //viewpager adapter for loading in the respective fragment whenever there is a swipe left or right
    private class Viewpageradapter extends FragmentPagerAdapter{



        private List<Fragment>  fragments = new ArrayList<Fragment>();
        private List<CharSequence> fragmenttitle = new ArrayList<CharSequence>();
        public Viewpageradapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addfragment(Fragment fm,CharSequence title)
        {
            fragments.add(fm);
            fragmenttitle.add(title);
        }
        @NonNull
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
            return fragmenttitle.get(position);
        }
    }

}