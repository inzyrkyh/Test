package com.test.test;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.daimajia.androidviewhover.BlurLayout;
import com.test.test.Model.Card;
import com.test.test.Model.ContactsMgr;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateCardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateCardFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageButton button_create_card;
    private ImageButton button_close;
    private EditText editTextName;
    private EditText editTextPhone;

    private Card card;
    private boolean isMyCard;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateCardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateCardFragment newInstance(String param1, boolean mycard) {
        CreateCardFragment fragment = new CreateCardFragment();
        Bundle args = new Bundle();
        args.putBoolean("isTheCardMine", mycard);
        fragment.setArguments(args);
        return fragment;
    }

    public CreateCardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isMyCard = getArguments().getBoolean("isTheCardMine");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_card, container, false);
        editTextName = (EditText) view.findViewById(R.id.editTextName);
        editTextName.requestFocus();
        button_create_card = (ImageButton) view.findViewById(R.id.button_create);
        button_close = (ImageButton) view.findViewById(R.id.button_close);
        button_create_card.setOnClickListener(this);
        button_close.setOnClickListener(this);
        editTextPhone = (EditText) view.findViewById(R.id.editTextPhone);

//        MainActivity.blurView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                MainActivity.blurView.getViewTreeObserver().removeOnPreDrawListener(this);
//                MainActivity.blurView.buildDrawingCache();
//                Bitmap bmp = MainActivity.blurView.getDrawingCache();
//                MainActivity.blur(bmp, MainActivity.blurView, 1);
//                MainActivity.blurView.setVisibility(View.INVISIBLE);
//                return true;
//            }
//        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_create:
                //TODO: 将新名片添加进本地数据库/临时List
                if (editTextName.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.getInstance(), "姓名不能为空", Toast.LENGTH_SHORT).show();
                }
                else if (editTextPhone.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.getInstance(), "电话不能为空", Toast.LENGTH_SHORT).show();
                }
                else{
                    card = new Card(editTextName.getText().toString(), editTextPhone.getText().toString());
                    if (card != null) {
                        ContactsMgr.getInstance().SetMeCard(card);
                        Toast.makeText(MainActivity.getInstance(), "创建成功", Toast.LENGTH_SHORT).show();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.remove(getFragmentManager().findFragmentByTag(FragmentTags.FRAGMENT_CREATE_CARD)).commit();
                        ((DrawerLayout) getActivity().findViewById(R.id.id_drawerLayout)).setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        //refresh datalist
                        CardListFragment.createCardListFragmentFrom(this);
                        MainActivity.adapter.notifyDataSetChanged();
//                        items.add(0, edittext.getText().toString());
//                        adapter.notifyDataSetChanged();
//                        MainActivity.lv.smoothScrollToPosition(0);
                        MainActivity.hideBlurCover();
                    }
                }
                break;
            case R.id.button_close:
                Log.d("1", "button close");
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.remove(getFragmentManager().findFragmentByTag(FragmentTags.FRAGMENT_CREATE_CARD)).commit();
                ((DrawerLayout) getActivity().findViewById(R.id.id_drawerLayout)).setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                MainActivity.hideBlurCover();
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
