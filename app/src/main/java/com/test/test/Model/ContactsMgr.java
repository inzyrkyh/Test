package com.test.test.Model;

import com.xiaoniao.bai.utils.AppConstants;
import com.xiaoniao.bai.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by bai on 2015/11/4.
 */
public class ContactsMgr {
    // group op
    public final static int Gnew = -1;
    private final static int GStart = 100;
    public final static int GAll = GStart+0;
    public final static int GFriends = GStart+1;
    public final static int GCom = GStart+2;
    //
    private ArrayList<Card> contacts = new ArrayList<>();
    private HashMap<String,Group> groups = new HashMap<>();
    private HashMap<Integer,Card>  mTempCardList = null;
    private static ContactsMgr me = null;
    private Card meCard = null;

    private ContactsMgr(){}
    public static ContactsMgr getInstance(){
        if ( me == null ){
            me = new ContactsMgr();
            me.addGroup(new Group(GAll,"所有人"));
            me.addGroup(new Group(GFriends, "朋友"));
            me.addGroup(new Group(GCom,"公司"));
        }
        return me;
    }
    // temp op;
    public void AddToTemp(int pos,Card card){
        if( mTempCardList == null )
            mTempCardList = new HashMap<>();
        mTempCardList.put(pos,card);
    }
    public void DeleteFromTemp(int pos){
        if( mTempCardList != null )
            mTempCardList.remove(pos);
    }
    public void ClearTemp(){
        if( mTempCardList != null )
           mTempCardList.clear();
    }
    public ArrayList<Card> GetTempAll(){
        if( mTempCardList == null )
            return null;
        ArrayList<Card> cards = new ArrayList<>();
        for( Map.Entry<Integer,Card> entry:mTempCardList.entrySet() )
            cards.add(entry.getValue());
        return cards;
    }
    public int GetTempSize(){
        if( mTempCardList != null )
            return mTempCardList.size();
        return 0;
    }
    // end temp op;
    public ArrayList<Card> GetContacts(){
        return contacts;
    }
    public Card GetContactItem(int pos){
        return GetContacts().get(pos);
    }
    public void AddContact(Card contact){
        Card card = IsHaveThisCard(contact);
        if( card == null ){
            int Gid = Utils.RandomInt(GFriends,GCom);
            if( Gid!= AppConstants.iRetError ) {
                contact.AddToGroup(getAGroup(Gid));
            }
            contacts.add(contact);
            for(int i=0;i<1;++i) {
                Card card1 = new Card(contact);
                card1.setName(card1.getName() + "副本"+i+1);
                contact.AddOtherCard(card1);
            }
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
        if( meCard == null ){
            meCard = card;
            contacts.add(0,meCard);
        }
        else
            meCard.AddOtherCard(card);
    }
    // group op
    private int AllocGroupId(){
        int maxGid = 0;
        for(Map.Entry<String,Group> entry : groups.entrySet() ){
            int curId = entry.getValue().GetGId();
            if( curId > maxGid )
                maxGid = curId;
        }
        return maxGid+1;
    }
    public Group newGroup(String GName){
        if( GName==null || GName.equals("") || getAGroup(GName)!=null )
            return null;
        Group group = new Group();
        group.SetGName(GName);
        group.SetGId( AllocGroupId() );
        addGroup(group);
        // save to db;
        return group;
    }
    public Group getAGroup(String GName){
        for(Map.Entry<String,Group> entry : groups.entrySet() ){
            if( entry.getKey().equals(GName))
                return entry.getValue();
        }
        return null;
    }
    public void DeleteCard(int pos){

    }
    public ArrayList<Card> GetCards(int goupid){
        if( getAGroup(goupid)==null )
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
        ArrayList<Group> groupsList = new ArrayList<>();
        for( Map.Entry<String,Group> entry : groups.entrySet() ) {
            groupsList.add(entry.getValue());
        }
        Collections.sort(groupsList, new Comparator<Group>(){
            public int compare(Group g1,Group g2){
                return g1.GetGId()-g2.GetGId();
            }
        });
        return groupsList;
    }
    public Group getAGroup(int goupId) {
        for(Map.Entry<String,Group> entry : groups.entrySet() ){
            if( entry.getValue().GetGId() == goupId )
                return entry.getValue();
        }
        return null;
    }
    private void addGroup(Group group) {
        groups.put(group.GetGName(), group);
    }
}
