package com.example.projekt.generator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

//Singleton
public class Generator {
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
        String allData="";

        //selecting all fields from user data and adding it to allData in random order
        while (data.size()>0) {
            int num = rn.nextInt(data.size()-1 - 0 + 1) + 0; //selecting
            allData+=data.get(num).toString(); //adding
            data.remove(num); //deleting data from structure to avoid repeats
        }

        /*
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
        */

        //my test algorithm
        //spaces removal
        allData = allData.replaceAll("\\s+","");
        //password generation
        while(generated.length()<passwordLength) {
            //beginning of the string
            int start = rn.nextInt(allData.length() - 1 - 0 + 1) + 0;
            //check if data length from beginning point is enough to fill preferred length
            if (start + passwordLength < allData.length()) {
                for (int k = start; k <= start + passwordLength; k++) {
                    generated += allData.charAt(k);
                }
            } else {
                //if not enough filling as much as possible and selecting beginning point again
                for (int k = start; k < allData.length(); k++) {
                    generated += allData.charAt(k);
                    //if length is already ok
                    if(generated.length()==passwordLength)
                        break;
                }
            }
        }
        //special chars section here
        //randomly replace 'a' to '@' 's' to '$'
        //will be done later



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
    }

    public void clear(){
        pass.clear();
    }
}
