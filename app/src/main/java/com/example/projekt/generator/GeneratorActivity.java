package com.example.projekt.generator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class GeneratorActivity extends AppCompatActivity {
    private EditText pass1;
    private EditText pass2;
    private EditText pass3;
    private List pass;

    //copy selected password to clipboard
    private void copy(EditText e) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", e.getText().toString());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Hasło zostało skopiowane",
                Toast.LENGTH_LONG).show();
    }

    //back button action
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

        View.OnClickListener myHandler = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.pass1:
                        copy(pass1);
                        break;
                    case R.id.pass2:
                        copy(pass2);
                        break;
                    case R.id.pass3:
                        copy(pass3);
                        break;
                }
            }
        };

        // Capture the layout's EditText and set the string as its text
        pass1 = (EditText) findViewById(R.id.pass1);
        pass1.setFocusable(false);
        pass1.setOnClickListener(myHandler);
        pass2 = (EditText) findViewById(R.id.pass2);
        pass2.setFocusable(false);
        pass2.setOnClickListener(myHandler);
        pass3 = (EditText) findViewById(R.id.pass3);
        pass3.setFocusable(false);
        pass3.setOnClickListener(myHandler);

        //getting data from generator and printing it
        pass = Generator.getInstance().getPassword();
        String t = (String) pass.get(0);
        pass1.setText(t);
        t = (String) pass.get(1);
        pass2.setText(t);
        t = (String) pass.get(2);
        pass3.setText(t);
    }

    //generating new password
    private void regenerate() {
        Generator.getInstance().clear();
        Generator.getInstance().generate(Generator.getInstance().getPassLen());
        pass = Generator.getInstance().getPassword();
        String t = (String) pass.get(0);
        pass1.setText(t);
        t = (String) pass.get(1);
        pass2.setText(t);
        t = (String) pass.get(2);
        pass3.setText(t);
    }
}
