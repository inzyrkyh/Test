package com.test.test.Model;

import java.util.ArrayList;

/**
 * Created by MiJiefei on 2015/11/20.
 */
public class Group {
    private int GId;
    private String GName;
    public Group(){
        GId = 0;
        GName = "";
    }
    public int GetGId(){ return GId; }
    public void SetGId(int gid){ GId = gid; }
    public String GetGName(){return GName;}
    public void SetGName(String s){ GName = s;}
}