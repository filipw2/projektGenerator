package com.example.projekt.generator;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.*;
/**
 * Created by Filip on 2017-04-11.
 */

class Generator {
    public Activity activity;
    Button generateButton;


    Generator(Activity _activity) {
        this.activity=_activity;
        generateButton = (Button) activity.findViewById(R.id.button);

        generateButton.setOnClickListener(new View.OnClickListener() {
                                              public void onClick(View v) {
                                                  generateButtonHandler();
                                              }
                                          }
        );
    }

    private void generateButtonHandler(){
        TextView textView = (TextView) activity.findViewById(R.id.textView2);
        EditText editText = (EditText) activity.findViewById(R.id.editText);
        textView.setText(editText.getText());
    }

    private void getData(){

    }

    private void generate(){

    }

    private void printPasswords(){

    }

}
