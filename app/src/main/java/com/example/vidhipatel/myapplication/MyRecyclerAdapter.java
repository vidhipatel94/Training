package com.example.vidhipatel.myapplication;

import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vidhi.patel on 6/27/2015.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private List<User> items;
    private int itemLayout;
    User e;
    OnItemClickListener mOnItemClickListener;
    OnItemLongClickListener mOnItemLongClickListener;

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
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        e=items.get(i);
        viewHolder.mTextviewName.setText(e.getName());
        viewHolder.mTextviewDesignation.setText(e.getUsername());
        viewHolder.mTextviewEmail.setText(e.getEmail());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(v, i);
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mOnItemLongClickListener!=null)
                    mOnItemLongClickListener.onItemLongClick(v,i);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void add(User item, int position){
        items.add(item);
        notifyItemInserted(position);
    }

    void remove(User item){
        int position=items.indexOf(item);
        items.remove(position);
        notifyItemRemoved(position);
    }

    //If you declare a member class that does not require access to an enclosing
    // instance, always put the static modifier in its declaration,
    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_name) TextView mTextviewName;
        @Bind(R.id.tv_designation) TextView mTextviewDesignation;
        @Bind(R.id.tv_email) TextView mTextviewEmail;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
    interface OnItemClickListener{
        public void onItemClick(View v,int position);
    }

    void setOnItemClickListener(final OnItemClickListener onItemClickListener){
        this.mOnItemClickListener=onItemClickListener;
    }

    interface OnItemLongClickListener{
        public void onItemLongClick(View v,int position);
    }

    void setOnItemLongClickListener(final OnItemLongClickListener mOnItemLongClickListener){
        this.mOnItemLongClickListener=mOnItemLongClickListener;
    }
}
