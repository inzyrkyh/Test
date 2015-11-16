package com.test.test.Model;

/**
 * Created by MiJiefei on 2015/10/28.
 */
public class Card {
    private String name;
    private String phoneNumber;
    private String sortKey;
    private boolean mycard;

    public Card() {
        name = "";
        phoneNumber = "";
        mycard = false;
    }

    public Card(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        mycard = false;
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
}
