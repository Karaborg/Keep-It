package com.example.karaborg.keep;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SectionActivity extends AppCompatActivity {

    String userName;

    Button button;
    EditText editText;

    DatabaseSection DS = new DatabaseSection(this);
    CheckText CT = new CheckText();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);

        button = (Button) findViewById(R.id.buttonSaveTitle);
        editText = (EditText) findViewById(R.id.editTextTitle);

        setTitle("Add Section");

        // GET USER NAME
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                userName = null;
            } else {
                userName = extras.getString("USER_NAME_HERE");
            }
        } else {
            userName = (String) savedInstanceState.getSerializable("USER_NAME_HERE");
        }

    }

    public void createSection(View view){

        String sectionName = editText.getText().toString().trim();

        if (CT.fiveLetter(editText) == true){

            DS.AddSection(sectionName, userName);
            DS.close();
            Toast.makeText(SectionActivity.this, "Done!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, ListActivity.class);
            intent.putExtra("USER_NAME_HERE", userName);
            startActivity(intent);
            this.finish();

        }else {
            Toast.makeText(SectionActivity.this, "Minimum 5 letters/digits!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("USER_NAME_HERE", userName);
        startActivity(intent);
        this.finish();
        //super.onBackPressed();
    }
}
