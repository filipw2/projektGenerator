package com.example.projekt.generator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.lang.String;


//Singleton
public class Generator {
    private List data; //user data
    private static List pass; //generated passwords
    private Random rn;
    private String specialChars;
    private String allData;
    private int upperCount;
    private boolean ignore=true;
    private boolean specCase=true;
    private int passLen;

    public static Generator instance;

    public static Generator getInstance(){
        if(instance == null){
            instance = new Generator();
        }
        return instance;
    }

    //find element in array
    private static boolean contains(final int[] array, final Integer v) {
        for (final int e : array)
            if (e == v || v != null && v.equals(e))
                return true;

        return false;
    }
    
    private String upper(String generated, int count){
        char[] generatedChars = generated.toCharArray();
        int[] used = new int[count];
        int ch;
        //while array cant be filled with 0, so I putted any unused big integer here
        for (int j=0; j<count; j++)
            used[j]=999;
        for (int i=0; i<count; i++)
        {
            ch=rn.nextInt(generatedChars.length-1 - 0 + 1) + 0;
            while(contains(used, ch)){
                ch=rn.nextInt(generatedChars.length-1 - 0 + 1) + 0;
            }
            used[i]=ch;
            generatedChars[ch]=Character.toUpperCase(generatedChars[ch]);
        }
        generated=String.valueOf(generatedChars);
        return generated;
    }

    private String specchar(String generated, char c){
        int counter=0;
        int num[]=new int[50];
        char[] generatedChars = generated.toCharArray();
        for(int i=0; i<generatedChars.length; i++){
            if (generatedChars[i]==c){
                num[counter]=i;
                counter++;
            }
        }
        if(counter>0) {
            int sel=0;
            if (counter > 1) {
                sel = rn.nextInt(counter - 1 - 0 + 1) + 0;
            }
            switch (c) {
                case 'a':
                    generatedChars[num[sel]] = '@';
                    break;
                case 's':
                    generatedChars[num[sel]] = '$';
                    break;
            }
            generated=String.valueOf(generatedChars);
        }
        return generated;
    }



    public void generate(int passwordLength){
        passLen=passwordLength;
        int c=0;
        //3 passwords generation
        while (c<3) {
            String generated = "";
            //selecting all fields from user data and adding it to allData in random order
            while (data.size() > 0) {
                int num = rn.nextInt(data.size() - 1 - 0 + 1) + 0; //selecting
                allData += data.get(num).toString(); //adding
                data.remove(num); //deleting data from structure to avoid repeats
            }

            //my test algorithm
            //spaces removal
            allData = allData.replaceAll("\\s+", "");
            if(ignore){
                allData=allData.toLowerCase();
            }
            //password generation
            while (generated.length() < passwordLength) {
                //beginning of the string
                int start = rn.nextInt(allData.length() - 1 - 0 + 1) + 0;
                int passLen = rn.nextInt(7 - 3 + 1) + 3;
                //check if data length from beginning point is enough to fill preferred length
                if (start + passLen < allData.length()) {
                    for (int k = start; k <= start + passLen; k++) {
                        generated += allData.charAt(k);
                        if (generated.length() == passwordLength)
                            break;
                    }
                } else {
                    //if not enough filling as much as possible and selecting beginning point again
                    for (int k = start; k < allData.length(); k++) {
                        generated += allData.charAt(k);
                        //if length is already ok
                        if (generated.length() == passwordLength)
                            break;
                    }
                }
            }

            //special chars adding if enabled
            if(specCase)
            {
                generated = specchar(generated, 'a');
                generated = specchar(generated, 's');
            }
            //upper case letter adding if enabled
            if(upperCount!=0)
            {
                double ca=(double)passwordLength*(double)upperCount/(double)100;
                int count = (int)Math.round(ca);
                generated = upper(generated, count);
            }

            //checking for duplicates
            if(c==0)
            {
                pass.add(generated);
                c++;
            }
            else if (c == 1 && pass.get(0) != generated) {
                    pass.add(generated);
                    c++;
                }
            else if (c == 2 && pass.get(0) != generated && pass.get(1) != generated) {
                    pass.add(generated);
                    c++;
                }
        }

    }

    private Generator() {
        rn = new Random(new Date().getTime());
        allData="";
        upperCount=0;
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

    public void clearData(){
        allData="";
    }

    public void setUpper(int i, boolean j, boolean k){upperCount=i; ignore=j; specCase=k;}

    public int getPassLen(){return passLen;}
}
