package com.example.projekt.generator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

//Singleton
public class Generator {
    int size=0; //count of used data fields
    private List data; //user data
    private List pass; //generated passwords
    private Random rn;
    private String specialChars;

    public static Generator instance;

    public static Generator getInstance(){
        if(instance == null){
            instance = new Generator();
        }
        return instance;
    }

    public void generate(int passwordLength){
        String generated="";
        //selecting random field from user data
        String allData="";

        for(int i=0;i<data.size();i++)
        {
            allData+=data.get(i).toString();
        }

        for(int i=0; i<passwordLength; i++){

            int cas = rn.nextInt(100);
            int sc = rn.nextInt(specialChars.length());
            int r = rn.nextInt(allData.length());
            if(cas >90)
                generated+=Character.toUpperCase(allData.charAt(r));
            else if(cas<20)
                generated+=specialChars.charAt(sc);
            else
                generated+=allData.charAt(r);

        }

        pass.add(generated);


    }

    private Generator() {
        rn = new Random(new Date().getTime());
        data = new ArrayList();
        pass = new ArrayList();
        specialChars="!@#$%^&*()";
    }

    public List getPassword() {
        return pass;
    }

    public void insert(String s){
        data.add(s);
        size++;
    }

    public void remove(int n){
        data.remove(n);
        size--;
    }

    public void clear(){
        pass.clear();
    }
}
