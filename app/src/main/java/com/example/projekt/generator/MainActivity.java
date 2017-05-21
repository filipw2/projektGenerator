package com.example.projekt.generator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Generator gen;
    LinearLayout ll;
    List<EditText> editTextList = new ArrayList<EditText>();
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    int passwordLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        gen = Generator.getInstance();
        ll = (LinearLayout) findViewById(R.id.linearLayout);

        Button prefButton = (Button) findViewById(R.id.preferences);
        prefButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),PreferencesActivity.class);
                startActivity(intent);

            }
        });

        Button b = (Button) findViewById(R.id.addButton);
        b.setOnClickListener(new Button.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     addField();
                                 }
                             }
        );

    }

    // Called when the user taps the Send button
    public void sendMessage(View view) {
        //clearing generator fields
        gen.clear();
        //fetching data from user


        if (setData()) {

            //generating
            gen.generate(passwordLength);
            //creating new activity
            Intent intent = new Intent(this, GeneratorActivity.class);
            //sending additional text info to new activity
            EditText editText = (EditText) findViewById(R.id.editText);
            String message = editText.getText().toString();
            intent.putExtra(EXTRA_MESSAGE, message);
            //starting new activity with results
            startActivity(intent);
        }
    }

    private Boolean setData() {
        if (!editTextList.isEmpty())
            for (EditText e : editTextList) {
                insertData(e);
            }
        else
            return Boolean.FALSE;

        TextView tv = (EditText) findViewById(R.id.passwordLenght);
        try {

            passwordLength = Integer.parseInt(tv.getText().toString());
            if (passwordLength < 1 || passwordLength > 30)
                return Boolean.FALSE;

        } catch (NumberFormatException e) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    private void insertData(EditText et) {

        gen.insert(et.getText().toString());
    }

    private void addField() {
        EditText eT = new EditText(this);
        editTextList.add(eT);
        ll.addView(eT);
    }

}
