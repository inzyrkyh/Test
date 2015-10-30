package com.test.test.Model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.test.test.R;

import java.util.List;

/**
 * Created by Hyk on 2015/10/30.
 */
public class CardAdapter extends ArrayAdapter<Card> {

    private int resourceID;

    public CardAdapter(Context context, int resource, List<Card> objects) {
        super(context, resource, objects);
        resourceID = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Card card = getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceID, null);
        }
        else {
            view = convertView;
        }
        TextView tvName = (TextView) view.findViewById(R.id.card_name);
        TextView tvPhone = (TextView) view.findViewById(R.id.card_phone);
        tvName.setText(card.getName());
        tvPhone.setText(card.getPhoneNumber());
        return view;
    }
}
