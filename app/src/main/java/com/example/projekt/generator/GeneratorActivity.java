package com.example.projekt.generator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class GeneratorActivity extends AppCompatActivity {
    private EditText pass1;
    private EditText pass2;
    private EditText pass3;
    private List pass;
    @Override
    public void onBackPressed() {
        Generator.getInstance().clearData();
        super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator);

        Button reg = (Button) findViewById(R.id.btnR);
        reg.setOnClickListener(new Button.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     regenerate();
                                 }
                             }
        );

        Button ret = (Button) findViewById(R.id.btnRet);
        ret.setOnClickListener(new Button.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     onBackPressed();
                                 }
                             }
        );

        // Capture the layout's TextView and set the string as its text
        pass1 = (EditText) findViewById(R.id.pass1);
        pass1.setKeyListener(null);
        pass2 = (EditText) findViewById(R.id.pass2);
        pass2.setKeyListener(null);
        pass3 = (EditText) findViewById(R.id.pass3);
        pass3.setKeyListener(null);

        //getting data from generator and printing it
        pass=Generator.getInstance().getPassword();
        String t= (String) pass.get(0);
        pass1.setText(t);
        t= (String) pass.get(1);
        pass2.setText(t);
        t= (String) pass.get(2);
        pass3.setText(t);
    }

    private void regenerate(){
        Generator.getInstance().clear();
        Generator.getInstance().generate(Generator.getInstance().getPassLen());
        pass=Generator.getInstance().getPassword();
        String t= (String) pass.get(0);
        pass1.setText(t);
        t= (String) pass.get(1);
        pass2.setText(t);
        t= (String) pass.get(2);
        pass3.setText(t);
    }
}
