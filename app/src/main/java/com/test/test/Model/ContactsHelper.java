package com.test.test.Model;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.test.test.MainActivity;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hyk on 2015/10/27.
 */
public class ContactsHelper {

    /**
     * 获取通讯录的数据
     * */
    public static void fetchAllContacts(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        if (contentResolver != null) {
            ContentResolver cr = context.getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);
            if (cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        //Query phone here.  Covered next
                        if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                            Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                                    new String[]{id}, null);
                            while (pCur.moveToNext()) {
                                // Do something with phones
                                Log.d("ContactsHelper", name + " = " + pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                                //可以在本地通讯录创建联系人
                                Card card = new Card(name, pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                                if (!MainActivity.dataList.contains(card)) {
                                    MainActivity.dataList.add(card);
                                }
                            }
                            pCur.close();
                        }
                    }
                }
            }
            cur.close();
        }
    }

    /**
     * 通讯录去重
     * */
    public static List<Card> removeDuplicate(List<Card> list) {
        Set<Card> set = new LinkedHashSet<Card>();
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }
}
