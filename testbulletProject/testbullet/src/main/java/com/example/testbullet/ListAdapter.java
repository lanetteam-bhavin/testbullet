package com.example.testbullet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.example.testbullet.singleclass.Name;
import com.example.testbullet.singleclass.Seperator;

import java.util.List;

public class ListAdapter extends BaseAdapter {

    Context ctx;
    List<String> names;
    List<Object> namesToSend;
    LayoutInflater inflator;
    viewHolderOne holder;
//	public ListAdapter(Context applicationContext, List<String> names)
//	{
//		this.ctx = applicationContext;
//		this.names = names;
//	}

    public ListAdapter(Context applicationContext, List<Object> names) {
        this.ctx = applicationContext;
        this.namesToSend = names;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return this.namesToSend.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return (namesToSend.get(position) instanceof Seperator ? 1 : 0);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public boolean isEnabled(int position) {
        return getItemViewType(position) != 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder = new viewHolderOne();
        final int type = getItemViewType(position);
        View v = convertView;
        TextView tv;
        TextView tvName, tvAddress, tvGender;
        ImageView ivPerson;
        if (v == null) {
            inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflator.inflate(type == 1 ? R.layout.seperator_row : R.layout.row_layout, null);
        }

        if (type == 1) {
            Seperator temp1 = (Seperator) namesToSend.get(position);
            tv = (TextView) v.findViewById(R.id.tvSeperator);
            tv.setText(temp1.getNames());
        } else {
            Name temp1 = (Name) namesToSend.get(position);

            ivPerson = (ImageView) v.findViewById(R.id.ivPerson);
            AQuery aq = new AQuery(ctx);
            aq.id(ivPerson).image("http://www.androidpeople.com/wp-content/uploads/2010/03/android.png", false, false);
            tvName = (TextView) v.findViewById(R.id.tvName);
            tvAddress = (TextView) v.findViewById(R.id.tvAddress);
            tvGender = (TextView) v.findViewById(R.id.tvGender);
            tv = (TextView) v.findViewById(R.id.tv1);
            tvName.setText(temp1.getNames());
            tvAddress.setText(temp1.getAddress());
            tvGender.setText(temp1.getGender());
        }
        return v;
    }

    public class viewHolderOne {
        TextView tvName, tvAddress, tvGender;
    }
}
