package com.test.test;


import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.test.test.Listener.SwipeDismissTouchListener;
import com.test.test.Model.Card;
import com.test.test.Model.ContactsMgr;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardStackFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardStackFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView mtextViewName;

    FrameLayout mMainLayout;

    Card card;

    Button button_phone_call;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CardStackFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CardStackFragment newInstance(String param1, Card param2) {
        CardStackFragment fragment = new CardStackFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        fragment.card = param2;
        return fragment;
    }

    public CardStackFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_card, container, false);
        Bundle bdl = getArguments();
//        mMainLayout = (FrameLayout) v.findViewById(R.id.main_layout);
        mtextViewName = (TextView) v.findViewById(R.id.fragment_card_name);
        mtextViewName.setText(card.getName());
        button_phone_call = (Button) v.findViewById(R.id.button_phone_call);
        button_phone_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("button", "phone call clicked" + card.getPhoneNumber());
                //            phone call
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + (card).getPhoneNumber()));
                startActivity(intent);
            }
        });

        return v;
    }
    public void updateUI(String str){
        if(str!=null && str!="" && mtextViewName!=null)
            mtextViewName.setText(str);
    }

}
