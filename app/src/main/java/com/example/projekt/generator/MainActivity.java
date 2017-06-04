package com.example.projekt.generator;


import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import receiver.WakefulReceiver;

public class MainActivity extends AppCompatActivity {
    Generator gen;
    LinearLayout ll;
    List<EditText> editTextList = new ArrayList<EditText>();
    private WakefulReceiver wr;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    int passwordLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, true);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        wr = new WakefulReceiver();


        gen = Generator.getInstance();
        ll = (LinearLayout) findViewById(R.id.field_container);


        Button b = (Button) findViewById(R.id.addButton);
        b.setOnClickListener(new Button.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     addField();
                                 }
                             }
        );

        initFields();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_generator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, PreferencesActivity.class);
            startActivity(intent);
        }

        if (id == R.id.exit) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

    // Called when the user taps the Send button
    public void sendMessage(View view) {
        //clearing generator fields
        gen.clear();
        //fetching data from user


        try {
            if (setData()) {

                //generating
                gen.generate(passwordLength);
                gen.clearData();
                wr.setAlarm(this);
                //creating new activity
                Intent intent = new Intent(this, GeneratorActivity.class);

                //starting new activity with results
                startActivity(intent);
            }
        } catch (Exception e) {

        }
    }


    //TODO check if fields contain data
    private Boolean setData() {
        if (!editTextList.isEmpty())
            for (EditText e : editTextList) {
                insertData(e);
            }
        else
            return Boolean.FALSE;

        TextView tv = (EditText) findViewById(R.id.passwordLength);
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


    private void initFields() {
        addField("Imię");
        addField("Nazwisko");
        addField("Miejscowość");
    }

    private void addField() {
        EditText eT = new EditText(this);
        TableRow.LayoutParams params = new TableRow.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT, 1.0f);
        eT.setLayoutParams(params);
        eT.setHint(R.string.input_placeholder);
        eT.setText("");
        eT.setId(View.generateViewId());
        editTextList.add(eT);

        ll.addView(eT);
    }


    private void addField(String text) {
        addField();
        editTextList.get(editTextList.size() - 1).setHint(text);
    }


}