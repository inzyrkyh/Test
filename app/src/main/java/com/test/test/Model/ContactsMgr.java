package com.test.test.Model;

import com.xiaoniao.bai.utils.AppConstants;
import com.xiaoniao.bai.utils.Utils;

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
    public final static int GAll = GStart+1;
    public final static int GFriends = GStart+2;
    public final static int GCom = GStart+3;
    //
    private ArrayList<Card> contacts = new ArrayList<>();
    private ArrayList<Group> groups = new ArrayList<>();
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
        int Gid = Utils.RandomInt(GStart+1,GStart+GetGroupCount());
        if( Gid!= AppConstants.iRetError && Gid!=GAll ) {
            Group group = new Group();
            group.SetGId(Gid);
//            addGroup(group);
            contact.AddToGroup(group);
        }
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

    private int newGroup(){
        mGroupCount++;
        // save to db;
        return GetGroupCount()+GStart;
    }

    public Group newGroup(String GName){
//        mGroupCount++;
        Group group = new Group();
        group.SetGName(GName);
        group.SetGId(newGroup());
        addGroup(group);
        // save to db;
        return group;
    }

    public void DeleteCard(int pos){

    }
    public ArrayList<Card> GetCards(int goupid){
        if( goupid <= GStart || goupid > GStart + GetGroupCount() )
            return null;
        if( goupid == GAll )
            return GetContacts();
        ArrayList<Card> cards = new ArrayList<>();
        for (int i=0;i<contacts.size();++i){
            Card card = contacts.get(i);
            if( card.IsInAGroup(goupid))
                cards.add(card);
        }
        return cards;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void addGroup(Group group) {
        groups.add(group);
    }
}
