package com.docdoku.countrycontentresolver;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by elisabelgenereux on 11/02/15.
 */
public class ContactsAdapter extends BaseAdapter {

    Cursor mContactsCursor;
    Context mContext;

    public ContactsAdapter(Cursor cursor, Context context) {
        mContactsCursor = cursor;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mContactsCursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        mContactsCursor.moveToPosition(position);

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

        TextView titleView = view.findViewById(android.R.id.text1);
        titleView.setText(mContactsCursor.getString(mContactsCursor.getColumnIndex("display_name")));

        return view;
    }

}
