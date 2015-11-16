package com.test.test.Model;

import java.util.ArrayList;

/**
 * Created by bai on 2015/11/4.
 */
public class ContactsMgr {
    private ArrayList<Card> contacts = new ArrayList<>();
    private static ContactsMgr me = null;
    private ContactsMgr(){}
    public static ContactsMgr getInstance(){
        if ( me == null )
            me = new ContactsMgr();
        return me;
    }
//    public void Init(Activity activity){
//
//    }
    public ArrayList<Card> GetContacts(){
        return contacts;
    }
    public Card GetContactItem(int pos){
        return contacts.get(pos);
    }
    public void AddContact(Card contact){
        contacts.add(contact);
    }
    public int GetSize(){ return contacts.size(); }
}
