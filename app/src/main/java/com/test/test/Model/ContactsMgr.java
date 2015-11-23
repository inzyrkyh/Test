package com.test.test.Model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by bai on 2015/11/4.
 */
public class ContactsMgr {
    // group op
    private int mGroupCount = 3;
    public final static int GStart = 100;
    private final static int GAll = GStart+1;
    private final static int GFriends = GStart+2;
    private final static int GCom = GStart+3;
    //
    private ArrayList<Card> contacts = new ArrayList<>();
    private static ContactsMgr me = null;
    private String mCurUserInfo = null;
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
    public String GetCurUserInfo(){
        if( mCurUserInfo == null ){
            StringBuffer params = new StringBuffer();
            params.append("name>>").append(new java.util.Random().nextInt(200)+100)
                    .append("$$tel>>").append(new java.util.Random().nextInt(200)+500);
            mCurUserInfo = params.toString();
        }
        return mCurUserInfo;
    }
//    public static List<Card> removeDuplicate(List<Card> list) {
//        Set<Card> set = new LinkedHashSet<Card>();
//        set.addAll(list);
//        list.clear();
//        list.addAll(set);
//        return list;
//    }
    // group op
    public int GetGroupCount(){
        return mGroupCount;
    }
    public int newGroup(String GName){
        mGroupCount++;
        // save to db;
        return GetGroupCount()+GStart;
    }
    public void DeleteCard(int pos){

    }
    public ArrayList<Card> GetCards(int goupid){
        ArrayList<Card> cards = new ArrayList<>();
        for (int i=0;i<contacts.size();++i){
            Card card = contacts.get(i);
            if( card.IsInAGroup(goupid))
                cards.add(card);
        }
        return cards;
    }
}
