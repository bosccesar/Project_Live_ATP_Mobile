package com.atp.live_atp_mobile;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by cesar on 27/02/2018.
 */

public class Login extends AppCompatActivity implements View.OnClickListener{

    private EditText editLogin;
    private EditText editPassword;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        this.editLogin = (EditText) findViewById(R.id.editTextLogin);
        this.editPassword = (EditText) findViewById(R.id.editTextPassword);
        this.button = (Button) findViewById(R.id.button4);

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == button){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editLogin.getWindowToken(), 0);
        }
    }
}
