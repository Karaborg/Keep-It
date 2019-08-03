package com.example.karaborg.keep;

import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class CheckText extends AppCompatActivity {

    public boolean fiveLetter(EditText editText){

        if(editText.getText().toString().trim().length() < 5)
            return false;
        else
            return true;
    }

}
