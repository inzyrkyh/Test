package com.test.test.Model;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by MiJiefei on 2015/11/6.
 */
public class CardStackAdapter extends FragmentPagerAdapter {

    private int resourceID;

//    public CardStackAdapter(Context context, int resource, List<Card> objects) {
//        super(context, resource, objects);
//        resourceID = resource;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        Card card = getItem(position);
//        View view;
//        if (convertView == null) {
//            view = LayoutInflater.from(getContext()).inflate(resourceID, null);
//        }
//        else {
//            view = convertView;
//        }
//        TextView tvName = (TextView) view.findViewById(R.id.fragment_card_name);
////        TextView tvPhone = (TextView) view.findViewById(R.id.card_button_text7);
//        tvName.setText(card.getName());
////        tvPhone.setText(card.getPhoneNumber());
//        return view;
//    }

    private List<Fragment> fragments;

    public CardStackAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }

    public void remove(Fragment fragment) {
        fragments.remove(fragment);
    }
}

