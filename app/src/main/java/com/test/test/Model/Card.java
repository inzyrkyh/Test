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
        name = "";
        phoneNumber = "";
        InitDefaultGroup();
    }

    public Card(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
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
        Group group = new Group();
        group.SetGId(ContactsMgr.GAll);
        group.SetGName("所有人");
        ContactsMgr.getInstance().addGroup(group);
        this.AddToGroup(group);
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
        if( group.GetGId() > ContactsMgr.GStart+ContactsMgr.getInstance().GetGroupCount() )
            return;
        mGroups.put(group.GetGId(),group);
    }
    public void DeleteFromGroup(Group group){
        if( group.GetGId() > ContactsMgr.GStart+ContactsMgr.getInstance().GetGroupCount() )
            return;
        //if( mGroups.get(group.GetGId()) == null )
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
