package com.test.test.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by MiJiefei on 2015/10/28.
 */
public class Card {
    private HashMap<Integer,Group> mGroups = new HashMap<>();
    private ArrayList<Card> others = null;
    private String name;
    private String phoneNumber;
    private String sortKey;

    public Card() {
        setName("");
        setPhoneNumber("");
        InitDefaultGroup();
    }
    public Card(Card card) { // error
        setName(card.getName());
        setPhoneNumber(card.getPhoneNumber());
        //InitDefaultGroup();
    }
    public Card(String name, String phoneNumber) {
        setName(name);
        setPhoneNumber(phoneNumber);
        InitDefaultGroup();
    }
    public void AddOtherCard(Card card){
        if( others == null )
            others = new ArrayList<>();
        others.add(card);
    }
    public ArrayList<Card> GetAllOthers(){
        return others;
    }
    public Card GetOtherOne( int pos ){
        if( others == null || pos >= others.size() )
            return null;
        return others.get(pos);
    }
    public int OtherSize(){
        if( others == null )
            return 0;
        return others.size();
    }
    private void InitDefaultGroup(){
        this.AddToGroup(ContactsMgr.getInstance().getAGroup(ContactsMgr.GAll));
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public boolean equals(Object object) {
        boolean sameSame = false;

        if (object != null && object instanceof Card)
        {
            if (this.name.equals(((Card) object).name) && this.phoneNumber.equals(((Card) object).phoneNumber)) {
                sameSame = true;
            }
        }
        return sameSame;
    }
    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public String getSortKey(String sortKeyString) {
        String key = sortKeyString.substring(0, 1).toUpperCase();
        if (key.matches("[A-Z]")) {
            return key;
        }
        return "#";
    }
    // group op
    public void AddToGroup(Group group){
        if( group == null || ContactsMgr.getInstance().getAGroup(group.GetGName())==null )
            return;
        mGroups.put(group.GetGId(),group);
    }
    public void DeleteFromGroup(Group group){
        if( group == null || ContactsMgr.getInstance().getAGroup(group.GetGName())==null )
            return;
        mGroups.remove(group.GetGId());
    }
    public boolean IsInAGroup(int gId){
        for( HashMap.Entry<Integer,Group> entry : mGroups.entrySet() ){
            if( gId == entry.getKey() )
                return true;
        }
        return false;
    }
    public HashMap<Integer,Group> GetAllGroups(){ return mGroups;}
}
