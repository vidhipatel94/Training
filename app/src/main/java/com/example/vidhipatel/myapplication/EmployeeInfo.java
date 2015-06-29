package com.example.vidhipatel.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class EmployeeInfo extends AppCompatActivity {
    ViewPager viewPager;
    myPageAdapter pageAdapter;
    DatabaseHandler db;
    int currentUserIndex = 0;
    List<User> mUserList;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_info);

        //Back button in actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DatabaseHandler(this);
        mUserList = db.getAllUsers();
        int pos = getIntent().getExtras().getInt("User");

        viewPager = (ViewPager) findViewById(R.id.pager);
        pageAdapter = new myPageAdapter(this.getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);
        viewPager.setCurrentItem(pos);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);    //MODE_SCROLLABLE=0


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_employee_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.delete) {
            currentUserIndex = viewPager.getCurrentItem();
            db.deleteUser(mUserList.get(currentUserIndex));
            mUserList.remove(currentUserIndex);
            pageAdapter.notifyDataSetChanged();
            tabLayout.removeTabAt(currentUserIndex);
            return true;
        }
        if(id == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class myPageAdapter extends FragmentStatePagerAdapter {

        public myPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return EmployeeInfoFragment.newInstance(mUserList.get(position));
        }

        @Override
        public int getCount() {
            return mUserList.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }



        @Override
        public CharSequence getPageTitle(int position) {
            return mUserList.get(position).getName();
        }

    }

}
