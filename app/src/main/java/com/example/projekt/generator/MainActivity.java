package com.example.projekt.generator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Generator gen;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gen=Generator.getInstance();

    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        gen.clear();
        String data;
        EditText editText2 = (EditText) findViewById(R.id.editText2);
        data = editText2.getText().toString();
        gen.insert(data);
        EditText editText3 = (EditText) findViewById(R.id.editText3);
        data = editText3.getText().toString();
        gen.insert(data);
        EditText editText4 = (EditText) findViewById(R.id.editText4);
        data = editText4.getText().toString();
        gen.insert(data);
        EditText editText5 = (EditText) findViewById(R.id.editText5);
        data = editText5.getText().toString();
        gen.insert(data);
        EditText editText6 = (EditText) findViewById(R.id.editText6);
        data = editText6.getText().toString();
        gen.insert(data);
        gen.generate();
        Intent intent = new Intent(this, GeneratorActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }
}
