package com.example.projekt.generator;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import receiver.WakefulReceiver;

public class MainActivity extends AppCompatActivity {
    Generator gen;
    LinearLayout ll;
    LinearLayout myLayout;
    List<EditText> editTextList = new ArrayList<EditText>();
    private WakefulReceiver wr;
    int passwordLength;
    boolean ignore=true;
    boolean specCase=true;
    boolean settingsVisible=false;
    TextView upp;

    //action on created activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //prevent keyboard opening on program start
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, true);

        Button showhide = (Button) findViewById(R.id.btnActThree);
        showhide.setOnClickListener(new Button.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     showHide();
                                 }
                             }
        );

        //hiding generating options by default
        myLayout = (LinearLayout) findViewById(R.id.settings_container);
        myLayout.setVisibility(LinearLayout.GONE);

        //options fields config
        final TextView tv = (EditText) findViewById(R.id.passwordLength);
        tv.setFocusable(false);
        tv.setEnabled(false);
        tv.setText("6");
        tv.setCursorVisible(false);
        tv.setKeyListener(null);
        tv.setBackgroundColor(Color.TRANSPARENT);

        upp = (EditText) findViewById(R.id.upper);
        upp.setFocusable(false);
        upp.setEnabled(false);
        upp.setText("0");
        upp.setCursorVisible(false);
        upp.setKeyListener(null);
        upp.setBackgroundColor(Color.TRANSPARENT);

        //pass length configuration
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        //quantity of uppercase letters config
        SeekBar seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                upp.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

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
        //checking settings
        CheckBox cb = (CheckBox) findViewById(R.id.checkBox);
        CheckBox cb2 = (CheckBox) findViewById(R.id.checkBox2);
        if (cb.isChecked()) {
            ignore=true;
        }
        else {
            ignore = false;
        }
        if (cb2.isChecked()) {
            specCase=true;
        }
        else {
            specCase = false;
        }
        gen.setUpper(Integer.parseInt(upp.getText().toString()), ignore, specCase);
        //fetching data from user
        try {
            if (setData()) {

                //generating
                gen.generate(passwordLength);
                //gen.clearData();
                wr.setAlarm(this);
                //creating new activity
                Intent intent = new Intent(this, GeneratorActivity.class);
                //starting new activity with results
                startActivity(intent);
            }
        } catch (Exception e) {}
    }

    //TODO close keyboard on enter press

    private Boolean setData() {
        int c=0;
        if (!editTextList.isEmpty())
            for (EditText e : editTextList) {
                if(!e.getText().toString().matches("")) {
                    insertData(e);
                    c++;
                }
            }
        else
            return Boolean.FALSE;

        TextView tv = (EditText) findViewById(R.id.passwordLength);
        try {
            passwordLength = Integer.parseInt(tv.getText().toString());
            //checking password length
            if (passwordLength < 6){
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
                dlgAlert.setMessage(R.string.tooshort);
                dlgAlert.setTitle(R.string.error);
                dlgAlert.setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {}
                        });
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                return Boolean.FALSE;
            }

        } catch (NumberFormatException e) {
            return Boolean.FALSE;
        }

        if(c<3) {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage(R.string.toofew);
            dlgAlert.setTitle(R.string.error);
            dlgAlert.setPositiveButton(R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {}
                    });
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();

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
        eT.setImeOptions(EditorInfo.IME_ACTION_DONE);
        eT.setId(View.generateViewId());
        editTextList.add(eT);

        ll.addView(eT);
    }

    //show or hide generation options
    private void showHide() {
        myLayout = (LinearLayout) findViewById(R.id.settings_container);
        if(settingsVisible) {
            myLayout.setVisibility(LinearLayout.GONE);
            settingsVisible=false;
        }
        else {
            myLayout.setVisibility(LinearLayout.VISIBLE);
            settingsVisible=true;
        }
    }

    private void addField(String text) {
        addField();
        editTextList.get(editTextList.size() - 1).setHint(text);
    }

}