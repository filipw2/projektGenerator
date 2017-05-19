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

    public static Generator instance;

    public static Generator getInstance(){
        if(instance == null){
            instance = new Generator();
        }
        return instance;
    }

    public void generate(int passwordLenght){
        String generated;
        //selecting random field from user data

        for(int i=0; i<3; i++){
            int randomNum =  rn.nextInt(size-1 - 0 + 1) + 0;
            generated= (String) data.get(randomNum);
            pass.add(generated);
        }




    }

    private Generator() {
        rn = new Random(new Date().getTime());
        data = new ArrayList();
        pass = new ArrayList();
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
