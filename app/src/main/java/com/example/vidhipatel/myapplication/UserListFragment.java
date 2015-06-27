package com.example.vidhipatel.myapplication;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class UserListFragment extends Fragment {
    //ListView lv;
    RecyclerView mRecyclerView;
   // MyAdapterUser adapter1;
    MyRecyclerAdapter myRecyclerAdapter;

    List<User> mUserList;
    User mUser;
    DatabaseHandler db;
    SwipeRefreshLayout mSwipeRefreshLayout;
    FloatingActionButton fab;
    NotificationCompat.Builder builder;

    FragmentActivity fa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadUserData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fa=super.getActivity();
        View v=inflater.inflate(R.layout.fragment_user_list, container, false);
        // Inflate the layout for this fragment
        mUserList = new ArrayList<User>();
        //lv = (ListView) v.findViewById(R.id.list);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);

        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.activity_main_swipe_refresh_layout);
        fab = (FloatingActionButton) v.findViewById(R.id.fab);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.accent_material_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadUserData();
            }
        });
       fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });

        builder = new NotificationCompat.Builder(fa.getApplicationContext());
        builder.setSmallIcon(R.drawable.person);    //icon in status bar
        return v;
    }

    private void loadUserData() {
        getActivity().deleteDatabase("userdb");
        db = new DatabaseHandler(getActivity());

        Api api = new RestAdapter.Builder()
                .setEndpoint("http://jsonplaceholder.typicode.com")
                .build()
                .create(Api.class);
        api.getUser(new Callback<List<User>>() {
            @Override
            public void success(List<User> users, Response response) {
                // Log.i("JSON",users.get(0).getAddress());
                for (int i = 0; i < users.size(); i++) {
                    mUser = users.get(i);
                    //mUserList.add(mUser);
                    db.addUser(mUser);
                }
                displayUser();
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(fa.getApplicationContext(), "Connection error", Toast.LENGTH_LONG).show();
             /*   mUser = new User();
                mUser.setId(mUserList.size() + 1);
                mUser.setName("Vidhi Patel");
                mUser.setUsername("vidhi");
                mUser.setEmail("vidhi@xyz.com");
                mUserList.add(mUser);
                db.addUser(mUser);

                mUser = new User();
                mUser.setId(mUserList.size() + 1);
                mUser.setName("Radhi Patel");
                mUser.setUsername("vidhi");
                mUser.setEmail("vidhi@xyz.com");
                mUserList.add(mUser);
                db.addUser(mUser);
                displayUser();
                */
            }
        });
    }

    private void displayUser() {
        mUserList = db.getAllUsers();
        /*adapter1 =
                new MyAdapterUser(fa.getApplicationContext(), mUserList);
        lv.setAdapter(adapter1);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item value
                //  User itemValue = (User) lv.getItemAtPosition(position);

                Intent intent = new Intent(fa.getApplicationContext(), EmployeeInfo.class);
                //intent.putExtra("User", itemValue);
                intent.putExtra("User", position);
                startActivity(intent);

            }

        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                db.deleteUser(mUserList.get(position));
                String s = mUserList.get(position).getName();
                mUserList.remove(position);
                adapter1.notifyDataSetChanged();

                // This intent is fired when notification is clicked
                //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, this.getIntent(), 0);
                // Set the intent that will fire when the user taps the notification.
                //builder.setContentIntent(pendingIntent);

                builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white_24dp));
                builder.setContentTitle("Users");
                builder.setContentText("User: " + s + " is deleted");
                builder.setTicker("User: " + s + " is deleted");
                builder.setAutoCancel(true);
                NotificationManager notificationManager = (NotificationManager) fa.getSystemService(fa.NOTIFICATION_SERVICE);
                notificationManager.notify(1, builder.build());
                return true;
            }
        });
        */

        myRecyclerAdapter=new MyRecyclerAdapter(mUserList,R.layout.listviewlayout);
        mRecyclerView.setAdapter(myRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    private void addUser() {
        mUser = new User();
        mUser.setId(mUserList.size() + 1);
        mUser.setName("Vidhi Patel");
        mUser.setUsername("vidhi");
        mUser.setEmail("vidhi@xyz.com");
        //mUserList.add(mUser);
        //adapter1.notifyDataSetChanged();
        myRecyclerAdapter.add(mUser,mUserList.size());
        db.addUser(mUser);
        Toast.makeText(fa.getApplicationContext(), "User is added ", Toast.LENGTH_LONG).show();


        // This intent is fired when notification is clicked
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, this.getIntent(), 0);
        // Set the intent that will fire when the user taps the notification.
        //builder.setContentIntent(pendingIntent);

        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_person_add_white_24dp));
        builder.setContentTitle("Users");
        builder.setContentText("User is added");
        builder.setTicker("User is added"); //to display in status bar
        builder.setAutoCancel(true);    //remove notification after redirecting pending events
        NotificationManager notificationManager=(NotificationManager)fa.getSystemService(fa.NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());

    }




}
