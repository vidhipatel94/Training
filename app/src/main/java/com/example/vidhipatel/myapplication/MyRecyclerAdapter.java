package com.example.vidhipatel.myapplication;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

/**
 * Created by vidhi.patel on 6/27/2015.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> implements View.OnClickListener{

    private List<User> items;
    private int itemLayout;
    //private OnRecyclerViewItemClickListener<User> itemClickListener;

    public MyRecyclerAdapter(List<User> items,int itemLayout){
        this.items=items;
        this.itemLayout=itemLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        User e=items.get(i);
        viewHolder.mTextviewName.setText(e.getName());
        viewHolder.mTextviewDesignation.setText(e.getUsername());
        viewHolder.mTextviewEmail.setText(e.getEmail());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(fa.getApplicationContext(), EmployeeInfo.class);
                //intent.putExtra("User", itemValue);
                intent.putExtra("User", position);
                startActivity(intent);
                */
                Object o=v.getTag();
                Log.i("ITEM SELECTED",o.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(User item, int position){
        items.add(item);
        notifyItemInserted(position);
    }

    public void remove(User item){
        int position=items.indexOf(item);
        items.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onClick(View v) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextviewName;
        private TextView mTextviewDesignation;
        private TextView mTextviewEmail;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextviewName = (TextView) itemView.findViewById(R.id.tv_name);
            mTextviewDesignation = (TextView) itemView.findViewById(R.id.tv_designation);
            mTextviewEmail = (TextView) itemView.findViewById(R.id.tv_email);
        }
    }
}
