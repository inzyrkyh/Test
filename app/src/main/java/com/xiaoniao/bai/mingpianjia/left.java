package com.xiaoniao.bai.mingpianjia;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.test.test.CardListFragment;
import com.test.test.CreateCardFragment;
import com.test.test.FragmentTags;
import com.test.test.R;

public class left extends Fragment implements AdapterView.OnItemClickListener
{
    private ListView mLv;
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.left, container, false);
        mLv = (ListView) view.findViewById(R.id.id_leftlist);
        String[] str = new String[] { "创建自己的名片", "item2", "item3"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.my_function_list_item, str);
        mLv.setAdapter(adapter);
        mLv.setOnItemClickListener(this);
        //
        return view;
	}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (mLv.getAdapter().getItem(position).toString()) {
            case "创建自己的名片":
                ((DrawerLayout) getActivity().findViewById(R.id.id_drawerLayout)).setDrawerListener(new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {

                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {

                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        Fragment newFragment = new CreateCardFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.create_card, newFragment, FragmentTags.FRAGMENT_CREATE_CARD);
//                transaction.addToBackStack(null);
                        transaction.commit();
//                getFragmentManager().executePendingTransactions();
                        ((DrawerLayout) getActivity().findViewById(R.id.id_drawerLayout)).setDrawerListener(null);
                        ((DrawerLayout) getActivity().findViewById(R.id.id_drawerLayout)).setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {

                    }
                });
                ((DrawerLayout) getActivity().findViewById(R.id.id_drawerLayout)).closeDrawer(Gravity.LEFT);
                Log.d("1", mLv.getAdapter().getItem(position).toString());
                break;
            case "item2":
                Log.d("1", mLv.getAdapter().getItem(position).toString());
                break;
            case "item3":
                Log.d("1", mLv.getAdapter().getItem(position).toString());
                break;

        }
    }
}