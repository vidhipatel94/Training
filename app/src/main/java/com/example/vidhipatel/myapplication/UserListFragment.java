package com.example.vidhipatel.myapplication;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import android.support.v7.app.AlertDialog;


public class UserListFragment extends Fragment {
    @Bind(R.id.list)
    RecyclerView mRecyclerView;
    MyRecyclerAdapter myRecyclerAdapter;

    List<User> mUserList;
    User mUser;
    DatabaseHandler db;
    @Bind(R.id.activity_main_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    NotificationCompat.Builder builder;
    FragmentActivity fa;
    public static final String API_URL = "http://jsonplaceholder.typicode.com";

    @OnClick(R.id.fab)
    void addUser() {
        //AppCompat dialog view
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.add_user_dialog, null);

        final EditText addName = (EditText) view.findViewById(R.id.add_name);
        final EditText addUsername = (EditText) view.findViewById(R.id.add_username);
        final EditText addEmail = (EditText) view.findViewById(R.id.add_email);

        //create dialog
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Add User");
        builder.setView(view);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = String.valueOf(addName.getText());
                String username = String.valueOf(addUsername.getText());
                String email = String.valueOf(addEmail.getText());
                if (!name.isEmpty() && !username.isEmpty() && !email.isEmpty()) {
                    if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        //create and add User
                        mUser = new User(mUserList.size() + 1, name, username, email);
                        myRecyclerAdapter.add(mUser, mUserList.size());
                        db.addUser(mUser);

                        notifyUserAdded();
                    } else
                        Toast.makeText(getActivity(), "Invalid user email", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getActivity(), "Incomplete user information", Toast.LENGTH_LONG).show();

            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();

    }

    private void notifyUserAdded() {
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_person_add_white_24dp));
        builder.setContentTitle("Users");
        builder.setContentText("User is added");
        builder.setTicker("User is added"); //to display in status bar
        builder.setAutoCancel(true);    //remove notification after redirecting pending events
        NotificationManager notificationManager = (NotificationManager) fa.getSystemService(fa.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadUserData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fa = super.getActivity();
        View v = inflater.inflate(R.layout.fragment_user_list, container, false);
        ButterKnife.bind(this, v);

        // Inflate the layout for this fragment
        mUserList = new MyList<User>();
        mRecyclerView.setHasFixedSize(true);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.accent_material_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadUserData();
            }
        });
        builder = new NotificationCompat.Builder(fa.getApplicationContext());
        builder.setSmallIcon(R.drawable.person);    //icon in status bar
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        displayUser();
    }

    private void loadUserData() {
        getActivity().deleteDatabase(DatabaseHandler.DB_NAME);
        db = new DatabaseHandler(getActivity());

        Api api = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .build()
                .create(Api.class);
        api.getUser(new Callback<List<User>>() {
            @Override
            public void success(List<User> users, Response response) {
                for (int i = 0; i < users.size(); i++) {
                    mUser = users.get(i);
                    db.addUser(mUser);
                }
                displayUser();
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(fa.getApplicationContext(), "Connection error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void displayUser() {
        mUserList = db.getAllUsers();
        myRecyclerAdapter = new MyRecyclerAdapter(mUserList, R.layout.listviewlayout);
        mRecyclerView.setAdapter(myRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        myRecyclerAdapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(fa.getApplicationContext(), EmployeeInfo.class);
                intent.putExtra("User", position);
                startActivity(intent);
            }
        });
        myRecyclerAdapter.setOnItemLongClickListener(new MyRecyclerAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, int position) {
                db.deleteUser(mUserList.get(position));
                String userName = mUserList.get(position).getName();
                myRecyclerAdapter.remove(mUserList.get(position));

                notifyUserDeleted(userName);

            }
        });

    }

    private void notifyUserDeleted(String userName) {
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white_24dp));
        builder.setContentTitle("Users");
        builder.setContentText("User: " + userName + " is deleted");
        builder.setTicker("User: " + userName + " is deleted");
        builder.setAutoCancel(true);
        NotificationManager notificationManager = (NotificationManager) fa.getSystemService(fa.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }


}
