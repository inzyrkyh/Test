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
    private Card meCard = null;
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
        Card card = IsHaveThisCard(contact);
        if( card == null )
        {
            int Gid = Utils.RandomInt(GStart+1,GStart+GetGroupCount());
            if( Gid!= AppConstants.iRetError && Gid!=GAll ) {
                Group group = new Group();
                group.SetGId(Gid);
                addGroup(group);
                contact.AddToGroup(group);
            }
            contacts.add(contact);
        }
        else
            contact.AddOtherCard(card);
    }
    private Card IsHaveThisCard(Card card){
        for (Card card0:GetContacts()){
            if( card0.getName().equals(card.getName()))
                return card0;
        }
        return null;
    }
    public Card GetMeCard(){
        return meCard;
    }
    public void SetMeCard(Card card){
        if( meCard == null )
        {
            meCard = card;
            contacts.add(0,meCard);
        }
        else
            meCard.AddOtherCard(card);
    }
    public int GetSize(){ return contacts.size(); }
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
        for (Group group1:groups) {
            if (group1.equals(group))
                return;
        }
        groups.add(group);
    }
}
