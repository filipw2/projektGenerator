package com.example.projekt.generator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.lang.String;

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

    private String specchar(String generated, String c){
        int counter=0;
        int num[]=new int[50];
        int i=0;
        if(generated.toLowerCase().contains(c)) {
            num[i] = generated.indexOf(c);
            i++;
            counter++;
            /*
            //this section is for replacing chars if it is more then one symbol
            //but currently it doesnt work
            while (generated.toLowerCase().substring(num[i-1]).contains(c)) {
                num[i] = generated.indexOf(c);
                i++;
                counter++;
            }
            */
            char[] generatedChars = generated.toCharArray();
            int sel=0;
            if (counter > 1) {
                sel = rn.nextInt(counter - 1 - 0 + 1) + 0;
            }
            switch (c) {
                case "a":
                    generatedChars[num[sel]] = '@';
                    break;
                case "s":
                    generatedChars[num[sel]] = '$';
                    break;
            }
            generated=String.valueOf(generatedChars);
        }
        return generated;
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

        generated=specchar(generated, "a");
        generated=specchar(generated, "s");

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
