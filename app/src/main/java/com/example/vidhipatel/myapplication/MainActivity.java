package com.example.vidhipatel.myapplication;

import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Collection;
import java.util.Iterator;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    @Bind(R.id.drawer_layout) DrawerLayout drawerLayout;
    @Bind(R.id.toolbar) Toolbar toolbar;
    ContentFragment fragment;
    UserListFragment userListFragment;
    android.support.v4.app.FragmentTransaction fragmentTransaction;
    Boolean isTablet=false;
    public static final String MY_FRAG="MY_FRAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if(drawerLayout==null)
            isTablet=true;

        //actionbar
        toolbar.setTitle("Users");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setVisibility(View.VISIBLE);

        //navigation button in actionbar
        if(!isTablet) {
            toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }

        //navigation
        NavigationView view = (NavigationView) findViewById(R.id.navigation_view);
        view.getMenu().getItem(2).setChecked(true); //by default select Users
        fragment = new ContentFragment();
        userListFragment = new UserListFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, userListFragment,MY_FRAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {

                    case R.id.navigation_home:

                    case R.id.navigation_message:
                        android.support.v4.app.Fragment myFragment1 =
                                (android.support.v4.app.Fragment) getSupportFragmentManager().findFragmentByTag(MY_FRAG);
                        if (myFragment1 != null && !myFragment1.equals(fragment)) {
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(myFragment1.getId(), fragment, MY_FRAG);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                        Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_LONG).show();
                        break;
                    default:
                        android.support.v4.app.Fragment myFragment2 =
                                (android.support.v4.app.Fragment) getSupportFragmentManager().findFragmentByTag(MY_FRAG);
                        if (myFragment2 != null && !myFragment2.equals(userListFragment)) {
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(myFragment2.getId(), userListFragment, MY_FRAG);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                }
                if (!isTablet)
                    drawerLayout.closeDrawers();

                return true;
            }
        });



        stackusage();
        Log.i("Collection", "--------------------");
        collectionusage();

    }

    private void collectionusage() {
        MyCollection<String> myCollection = new MyCollection<String>();
        Log.i("SIZE", myCollection.size() + "");
        myCollection.add("AA");
        myCollection.add("BB");
        Log.i("SIZE", myCollection.size() + "");
        Log.i("BB?", myCollection.contains("BB") + "");
        myCollection.remove("BB");
        Log.i("BB?", myCollection.contains("BB") + "");
        myCollection.add("BB");
        myCollection.add("CC");
        myCollection.add("DD");
        Log.i("EMPTY?", myCollection.isEmpty() + "");
        Log.i("SIZE", myCollection.size() + "");
        //myCollection.clear();
        //Log.i("EMPTY?", myCollection.isEmpty() + "");

        MyCollection<String> myCollection1=new MyCollection<String>();
        myCollection1.addAll(myCollection);
        Log.i("SIZE", myCollection1.size() + "");
        Log.i("CONTAINSALL",myCollection1.containsAll(myCollection)+"");
        Iterator<String> iterator= (Iterator<String>) myCollection1.iterator();
        while(iterator.hasNext()){
            Log.i("ELE",iterator.next());
        }
        myCollection.remove("CC");//AA BB DD
        myCollection1.removeAll(myCollection); //CC
        Log.i("SIZE", myCollection1.size() + "");

        iterator= (Iterator<String>) myCollection1.iterator();
        while(iterator.hasNext()){
            Log.i("ELE",iterator.next());
        }

        //retainAll
        Log.i("C","----------");
        myCollection.add("CC");
        iterator= (Iterator<String>) myCollection.iterator();
        while(iterator.hasNext()){
            Log.i("ELE",iterator.next());
        }
        myCollection.retainAll(myCollection1);
        Log.i("", "---Retain--");
        iterator= (Iterator<String>) myCollection.iterator();
        while(iterator.hasNext()){
            Log.i("ELE",iterator.next());
        }

        myCollection.add("BB");
        myCollection.add("AA");
        myCollection.add("DD");

        String[] s=myCollection.toArray(new String[5]);
        Log.i("","Length" + s.length);

        Object[] o=myCollection.toArray();
        Log.i("","Length" + o.length);
    }

    private void stackusage() {
        Stack<String> stack=new ArrayStack<String>();
        stack.push("AA");
        stack.push("BB");
        stack.push("DD");
        stack.push("EE");
        Log.i("TOS", "stack size:" + stack.tos());
        Log.i("POP", stack.pop());
        Log.i("POP", stack.pop());
        Log.i("TOS", "stack size:"+stack.tos());
       // Log.i("POP", stack.pop());
        stack.push("CC");
        Log.i("TOS", "stack size:" + stack.tos());
        stack.clear();
        Log.i("TOS", "stack size:" + stack.tos());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.search) {

        }

        return super.onOptionsItemSelected(item);
    }


}
