package com.test.test.Model;

/**
 * Created by MiJiefei on 2015/10/28.
 */
public class Card {
    private String name;
    private String phoneNumber;
    private boolean mycard;

    public Card(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        mycard = false;
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
}
