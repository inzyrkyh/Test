package com.test.test.Model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fortysevendeg.swipelistview.SwipeListView;
import com.test.test.CardActivity;
import com.test.test.R;

import java.util.List;

/**
 * Created by Hyk on 2015/10/30.
 */
public class CardListAdapter extends ArrayAdapter<Card> {

    private int resourceID;

    public CardListAdapter(Context context, int resource, List<Card> objects) {
        super(context, resource, objects);
        resourceID = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Card card = getItem(position);
//        View view;
//        if (convertView == null) {
//            view = LayoutInflater.from(getContext()).inflate(resourceID, null);
//        }
//        else {
//            view = convertView;
//        }

//        tvPhone.setText(card.getPhoneNumber());

        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.card_item, parent, false);
            holder = new ViewHolder();
            convertView.findViewById(R.id.id_card_item_back).setAlpha(0);
            holder.b_call = (ImageButton) convertView.findViewById(R.id.b_list_phonecall);
            holder.b_mail = (ImageButton) convertView.findViewById(R.id.b_list_email);
            holder.b_sms = (ImageButton) convertView.findViewById(R.id.b_list_sms);
            holder.b_gotoCard = (Button) convertView.findViewById(R.id.b_gotoCard);
            TextView tvName = (TextView) convertView.findViewById(R.id.card_name);
            TextView tvPhone = (TextView) convertView.findViewById(R.id.card_phone);
            tvName.setText(card.getName());
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ((SwipeListView)parent).recycle(convertView, position);

        holder.b_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + (card).getPhoneNumber()));
                getContext().startActivity(intent);
                Log.d("click", String.format("b_call clicked"));
            }
        });

        holder.b_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (isPlayStoreInstalled()) {
//                    context.startActivity(new Intent(Intent.ACTION_VIEW,
//                            Uri.parse("market://details?id=" + item.getPackageName())));
//                } else {
//                    context.startActivity(new Intent(Intent.ACTION_VIEW,
//                            Uri.parse("http://play.google.com/store/apps/details?id=" + item.getPackageName())));
//                }
                Log.d("click", String.format("b_mail clicked"));
            }
        });

        holder.b_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Uri packageUri = Uri.parse("package:" + item.getPackageName());
//                Intent uninstallIntent;
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
//                    uninstallIntent = new Intent(Intent.ACTION_DELETE, packageUri);
//                } else {
//                    uninstallIntent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE, packageUri);
//                }
//                context.startActivity(uninstallIntent);
                Log.d("click", String.format("b_sms clicked"));
            }
        });

        holder.b_gotoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("click", String.format("b_gotocard clicked"));
                CardActivity.startActivity(getContext(), position);
            }
        });


        return convertView;
    }

    static class ViewHolder {
        ImageView ivImage;
        TextView tvTitle;
        TextView tvDescription;
        ImageButton b_mail;
        ImageButton b_sms;
        ImageButton b_call;
        Button b_gotoCard;
    }
}
