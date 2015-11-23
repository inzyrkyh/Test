package com.test.test.Model;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;

import com.test.test.ImportingFragment;
import com.test.test.MainActivity;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Handler;

/**
 * Created by hyk on 2015/10/27.
 */
public class ContactsHelper extends AsyncTask<String, String, String>{

    /**
     * 获取通讯录的数据
     * */
    public static void fetchAllContacts(Context context) {
        new ContactsHelper().execute();
    }

    /**
     * 通讯录去重
     * */
//    public static List<Card> removeDuplicate(List<Card> list) {
//        Set<Card> set = new LinkedHashSet<Card>();
//        set.addAll(list);
//        list.clear();
//        list.addAll(set);
//        return list;
//    }

    @Override
    protected String doInBackground(String... params) {
//        ContentResolver contentResolver = MainActivity.getInstance().getContentResolver();
//        if (contentResolver != null) {
//            ContentResolver cr = MainActivity.getInstance().getContentResolver();
//            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
//                    null, null, null, ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY);
//            if (cur.getCount() > 0) {
//                int i = 0;
//                while (cur.moveToNext() /*&& i < 10*/) {
//                    String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
//                    String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//                    if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
//                        //Query phone here.  Covered next
//                        if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
//                            Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                                    null,
//                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
//                                    new String[]{id}, null);
//                            while (pCur.moveToNext()) {
//                                // Do something with phones
//                                Log.d("ContactsHelper", name + " = " + pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
//                                //可以在本地通讯录创建联系人
//                                Card card = new Card(name, pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
//                                if (!MainActivity.dataList.contains(card)) {
//                                    MainActivity.dataList.add(card);
//                                }
//                            }
//                            pCur.close();
//                        }
//                    }
//                    Message msg = new Message();
//                    msg.what = ImportingFragment.MSG_PROGRESS_UPDATE;
//                    msg.arg1 = (int) (((float)cur.getPosition() + 1.0) / ((float)cur.getCount()) * 100);
////                    ((ImportingFragment) MainActivity.getInstance().getFragmentManager().findFragmentByTag("ImportingFragment"))
////                            .mHandler.sendMessage(msg);
//                    ImportingFragment importingFragment = (ImportingFragment) MainActivity.getInstance().getFragmentManager().findFragmentByTag("ImportingFragment");
//                    if (importingFragment != null) {
//                        importingFragment.mHandler.sendMessage(msg);
//                    }
//
//                    i++;
//                }
//            }
//            cur.close();
//        }
//        removeDuplicate(MainActivity.dataList);
//        MainActivity.adapter.notifyDataSetChanged();
        return null;
    }
}
