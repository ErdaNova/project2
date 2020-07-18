package com.example.myapplication_week1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Phonebook implements Serializable, Comparable<Phonebook> {
    private String name;
    private String number;
    private int friendly;
    static ArrayList<Phonebook> pblist=new ArrayList<Phonebook>();

    public Phonebook(){}
    public Phonebook(String name,String number, int friendly){
        this.name=name;
        this.number=number;
        this.friendly=friendly;
    }

    public void setName(String name){ this.name=name; }
    public void setNumber(String number){
        this.number=number;
    }
    public void setFriendly(int friendly) { this.friendly=friendly; }
    public String getName(){
        return name;
    }
    public String getNumber(){
        return number;
    }
    public int getFriendly() { return friendly; }
    public ArrayList<Phonebook> getList(){ return pblist; }

    @Override
    public int compareTo(Phonebook phonebook) {
        if(this.getFriendly()>phonebook.getFriendly()) return -1;
        else if(this.getFriendly()<phonebook.getFriendly()) return 1;
        return 0;
    }
}