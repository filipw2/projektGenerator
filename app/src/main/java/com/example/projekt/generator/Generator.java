package com.example.projekt.generator;

import java.util.ArrayList;
import java.util.List;

public class Generator {
    private List data;
    private List pass;

    public Generator() {
        data = new ArrayList();
        pass = new ArrayList();
    }

    public List getPassword() {
        return pass;
    }

    public void insert(String s){
       data.add("element 0");
    }

    public void remove(int n){
        data.remove(n);
    }

    public void clear(){
        pass.clear();
    }
}
