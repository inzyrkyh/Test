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
    private ArrayList<Card> others;
    private String name;
    private String phoneNumber;
    private String sortKey;
    private boolean mycard;

    public Card() {
        name = "";
        phoneNumber = "";
        mycard = false;
        InitDefaultGroup();
    }

    public Card(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        mycard = false;
        InitDefaultGroup();
    }

    private void InitDefaultGroup(){
        Group group = new Group();
        group.SetGId(ContactsMgr.GAll);
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

    public boolean getMyCard() {return mycard;}

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

    public void setMycard(boolean value) {
        mycard = value;
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
        mGroups.remove(group.GetGId());
    }
    public boolean IsInAGroup(int gId){
        for( HashMap.Entry<Integer,Group> entry : mGroups.entrySet() ){
            if( gId == entry.getKey() )
                return true;
        }
//            Object val = entry.getValue();
        return false;
    }
    public HashMap<Integer,Group> GetAllGroups(){ return mGroups;}
}
