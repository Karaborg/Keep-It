package com.example.karaborg.keep;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class SubSectionActivity extends AppCompatActivity {

    String beenHere;
    String SECTION;
    String USER;

    EditText SubTitle;
    EditText IDText;
    EditText PasswordText;
    Button SaveSubSection;

    Button btnCopyID;
    Button btnCopyPassword;

    Spinner spinner;

    DatabaseSection DS = new DatabaseSection(this);
    DatabaseSubSection DSS = new DatabaseSubSection(this);
    CheckText CT = new CheckText();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_section);

        SubTitle = (EditText) findViewById(R.id.editTextSubTitle);
        IDText = (EditText) findViewById(R.id.editTextID);
        PasswordText = (EditText) findViewById(R.id.editTextPassword);
        spinner = (Spinner) findViewById(R.id.spinner);
        SaveSubSection = (Button) findViewById(R.id.buttonSaveSubTitle);

        btnCopyID = (Button) findViewById(R.id.btnCopyID);
        btnCopyPassword = (Button) findViewById(R.id.btnCopyPassword);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                beenHere = null;
                SECTION = null;
                USER = null;
            } else {
                beenHere = extras.getString("BEEN_HERE");
                SECTION = extras.getString("SECTION");
                USER = extras.getString("USER_NAME");
            }
        } else {
            beenHere = (String) savedInstanceState.getSerializable("BEEN_HERE");
            SECTION = (String) savedInstanceState.getSerializable("SECTION");
            USER = (String) savedInstanceState.getSerializable("USER_NAME");
        }

        editTheTexts();
    }

    public void editTheTexts() {

        if (beenHere.equals("new")) {

            setTitle("Add Sub Section");

            SubTitle.setText("");
            IDText.setText("");
            PasswordText.setText("");
            doSpinner();

            btnCopyID.setEnabled(false);
            btnCopyPassword.setEnabled(false);

            SaveSubSection.setVisibility(View.VISIBLE);

        }else if (beenHere.equals("old")) {

            setTitle("");

            String subSection = DSS.Subsectionname(SECTION, USER);
            String id = DSS.Subsectionid(SECTION, USER);
            String password = DSS.SubsectionPassword(SECTION, USER);

            SubTitle.setText(subSection);
            IDText.setText(id);
            PasswordText.setText(password);

            SubTitle.setFocusable(false);
            IDText.setFocusable(false);
            PasswordText.setFocusable(false);

            spinner.setVisibility(View.INVISIBLE);
            SaveSubSection.setVisibility(View.INVISIBLE);

        }

        //Toast.makeText(SubSectionActivity.this, "" + SECTION, Toast.LENGTH_SHORT).show();

    }

    public void doSpinner(){

        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, DS.sections(USER)); // when select sub section, user null
        spinner.setAdapter(arrayAdapter);
        spinner.setVisibility(View.VISIBLE);
        DS.close();

    }

    public void Save (View view) {

        String title = SubTitle.getText().toString().trim();
        String id = IDText.getText().toString().trim();
        String password = PasswordText.getText().toString().trim();
        String section = spinner.getSelectedItem().toString();

        if (CT.fiveLetter(SubTitle) == true && CT.fiveLetter(IDText) == true && CT.fiveLetter(PasswordText) == true){

            DSS.AddSubSection(title, id, password, section, USER);
            DSS.close();
            Toast.makeText(SubSectionActivity.this, "Done!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, ListActivity.class);
            intent.putExtra("USER_NAME_HERE", USER);
            startActivity(intent);
            this.finish();

            //onBackPressed();

        }else {
            Toast.makeText(SubSectionActivity.this, "Minimum 5 letters/digits!", Toast.LENGTH_SHORT).show();
        }

    }

    public void CopyID(View view) {

        Object clipboardService = getSystemService(CLIPBOARD_SERVICE);
        final ClipboardManager clipboardManager = (ClipboardManager)clipboardService;

        // Create a new ClipData.
        ClipData clipData = ClipData.newPlainText("Source Text", IDText.getText().toString());
        // Set it as primary clip data to copy text to system clipboard.
        clipboardManager.setPrimaryClip(clipData);

        Toast.makeText(SubSectionActivity.this, "'" + IDText.getText().toString() + "' copied!", Toast.LENGTH_SHORT).show();

    }

    public void CopyPassword(View view){

        Object clipboardService = getSystemService(CLIPBOARD_SERVICE);
        final ClipboardManager clipboardManager = (ClipboardManager)clipboardService;

        // Create a new ClipData.
        ClipData clipData = ClipData.newPlainText("Source Text", PasswordText.getText().toString());
        // Set it as primary clip data to copy text to system clipboard.
        clipboardManager.setPrimaryClip(clipData);

        Toast.makeText(SubSectionActivity.this, "'" + PasswordText.getText().toString() + "' copied!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("USER_NAME_HERE", USER);
        startActivity(intent);
        finish();
        //super.onBackPressed();
    }
}
