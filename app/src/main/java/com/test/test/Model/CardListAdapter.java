package com.test.test.Model;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fortysevendeg.swipelistview.SwipeListView;
import com.test.test.CardActivity;
import com.test.test.GroupActivity;
import com.test.test.MainActivity;
import com.test.test.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Hyk on 2015/10/30.
 */
public class CardListAdapter extends ArrayAdapter<Card> {

    private int resourceID;

    private boolean isShowCheckBox;

    //private boolean[] mCheckedState;

    public CardListAdapter(Context context, int resource, List<Card> objects) {
        super(context, resource, objects);
        resourceID = resource;
        //mCheckedState = new boolean[5];
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Card card = getItem(position);
//        Log.d("CardList", "position = " + position);
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
            convertView = li.inflate(resourceID, null, false);
            holder = new ViewHolder();
            convertView.findViewById(R.id.id_card_item_back).setAlpha(0);
            holder.b_call = (ImageButton) convertView.findViewById(R.id.b_list_phonecall);
            holder.b_mail = (ImageButton) convertView.findViewById(R.id.b_list_email);
            holder.b_sms = (ImageButton) convertView.findViewById(R.id.b_list_sms);
            holder.b_gotoCard = (Button) convertView.findViewById(R.id.b_gotoCard);
            holder.imageViewFront = (ImageView) convertView.findViewById(R.id.diban02);
            holder.cb = (CheckBox) convertView.findViewById(R.id.id_card_item_checkbox);
            if (isShowCheckBox) {
                showCheckBox(holder.imageViewFront);
            }
            else {
                hideCheckBox(holder.imageViewFront);
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            if (isShowCheckBox) {
                showCheckBox(holder.imageViewFront);
            }
            else {
                hideCheckBox(holder.imageViewFront);
            }
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.card_name);
        TextView tvPhone = (TextView) convertView.findViewById(R.id.card_phone);
        tvName.setText(card.getName());

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

//        convertView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                v.setTranslationX(50);
//                return false;
//            }
//        });
//        holder.imageViewFront.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("CardList", "position = " + position);
//            }
//        });

        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                mCheckedState[position] = isChecked;
                if (isChecked) {
                    getItem(position).AddToGroup(GroupActivity.testGroup);
                }
                else {
                    getItem(position).DeleteFromGroup(GroupActivity.testGroup);
                }
            }
        });
//        holder.cb.setChecked(mCheckedState[position]);

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
        ImageView imageViewFront;
        CheckBox cb;
    }

    public void showCheckBox(View v) {
        ObjectAnimator animation = ObjectAnimator.ofFloat(((RelativeLayout)v.getParent().getParent()).findViewById(R.id.id_card_item_frame), "translationX", 150);
        animation.setDuration(300);
        animation.setTarget(((RelativeLayout) v.getParent().getParent()).findViewById(R.id.id_card_item_frame));
        animation.start();
        ObjectAnimator animation1 = ObjectAnimator.ofFloat(((RelativeLayout)v.getParent().getParent()).findViewById(R.id.id_card_item_back), "translationX", 150);
        animation1.setDuration(300);
        animation1.setTarget(((RelativeLayout) v.getParent().getParent()).findViewById(R.id.id_card_item_back));
        animation1.start();
        ObjectAnimator animation2 = ObjectAnimator.ofFloat(((RelativeLayout)v.getParent().getParent()).findViewById(R.id.id_card_item_front), "translationX", 150);
        animation2.setDuration(300);
        animation2.setTarget(((RelativeLayout) v.getParent().getParent()).findViewById(R.id.id_card_item_front));
        animation2.start();
        ((RelativeLayout)v.getParent().getParent()).findViewById(R.id.id_card_item_checkbox).setVisibility(View.VISIBLE);
        MainActivity.lv.setSwipeMode(SwipeListView.SWIPE_MODE_NONE);
    }

    public void hideCheckBox(View v) {
        ObjectAnimator animation = ObjectAnimator.ofFloat(((RelativeLayout)v.getParent().getParent()).findViewById(R.id.id_card_item_frame), "translationX", 0);
        animation.setDuration(300);
        animation.setTarget(((RelativeLayout) v.getParent().getParent()).findViewById(R.id.id_card_item_frame));
        animation.start();
        ObjectAnimator animation1 = ObjectAnimator.ofFloat(((RelativeLayout)v.getParent().getParent()).findViewById(R.id.id_card_item_back), "translationX", 0);
        animation1.setDuration(300);
        animation1.setTarget(((RelativeLayout) v.getParent().getParent()).findViewById(R.id.id_card_item_back));
        animation1.start();
        ObjectAnimator animation2 = ObjectAnimator.ofFloat(((RelativeLayout)v.getParent().getParent()).findViewById(R.id.id_card_item_front), "translationX", 0);
        animation2.setDuration(300);
        animation2.setTarget(((RelativeLayout) v.getParent().getParent()).findViewById(R.id.id_card_item_front));
        animation2.start();
        ((RelativeLayout)v.getParent().getParent()).findViewById(R.id.id_card_item_checkbox).setVisibility(View.GONE);
        MainActivity.lv.setSwipeMode(SwipeListView.SWIPE_MODE_RIGHT);
    }

    public void setShowCheckBox(boolean value) {
        isShowCheckBox = value;
    }

    public boolean getShowCheckBox() {
        return isShowCheckBox;
    }

//    @Override
//    public void notifyDataSetChanged() {
//        super.notifyDataSetChanged();
//        boolean[] tempArray = new boolean[ContactsMgr.getInstance().GetSize()];
//        Log.d("Dynamic Array", "new total count is " + ContactsMgr.getInstance().GetSize());
//        Log.d("Dynamic Array", "old array's length is " + mCheckedState.length);
//        for (int i = 0; i < tempArray.length; ++i) {
//            if (i < mCheckedState.length) {
//                tempArray[i] = mCheckedState[i];
//            }
//            else {
//                tempArray[i] = false;
//            }
//        }
//        mCheckedState = tempArray;
//    }
}
