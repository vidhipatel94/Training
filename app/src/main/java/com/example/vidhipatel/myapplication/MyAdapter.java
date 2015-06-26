package com.example.vidhipatel.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vidhi.patel on 6/18/2015.
 */
public class MyAdapter extends BaseAdapter {

    private final Context context;
    private final List<Employee> values;

    public MyAdapter(Context context, List<Employee> values) {
        //super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Employee getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.listviewlayout, parent, false);

        holder = new ViewHolder();
        holder.mTextviewName = (TextView) rowView.findViewById(R.id.tv_name);
        holder.mTextviewDesignation = (TextView) rowView.findViewById(R.id.tv_designation);
        holder.mTextviewEmail = (TextView) rowView.findViewById(R.id.tv_email);

        //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        Employee e=getItem(position);
        holder.mTextviewName.setText(e.getName());
        holder.mTextviewDesignation.setText(e.getDesignation());
        holder.mTextviewEmail.setText(e.getEmail());

        // change the icon for Windows and iPhone
     /*   String s = values[position];
        if (s.startsWith("iPhone")) {
            imageView.setImageResource(R.drawable.no);
        } else {
            imageView.setImageResource(R.drawable.ok);
        }
*/
        return rowView;
    }

    static class ViewHolder {
        private TextView mTextviewName;
        private TextView mTextviewDesignation;
        private TextView mTextviewEmail;
    }
}

