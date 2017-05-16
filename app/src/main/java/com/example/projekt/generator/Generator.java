package com.example.projekt.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {
    int size=0;
    private List data;
    private List pass;

    public static Generator instance;

    public static Generator getInstance(){
        if(instance == null){
            instance = new Generator();
        }
        return instance;
    }

    private void generate(){
        String generated;
        Random rn = new Random();
        for(int i=0; i<3; i++){
            //out of range exeption here randomly
            int randomNum =  rn.nextInt(size - 0 + 1) + 0;
            generated= (String) data.get(randomNum);
            pass.add(generated);
        }
    }

    private Generator() {
        data = new ArrayList();
        pass = new ArrayList();
        //test fragment here
        insert("imie");
        insert("nazwisko");
        insert("data");
        insert("kot");
        insert("Ala");
        //end of test
        generate();
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
