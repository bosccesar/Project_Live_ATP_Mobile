package com.atp.live_atp_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by cesar on 15/03/2018.
 */

public class CloseAppActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonYes;
    private Button buttonNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_app);

        this.buttonYes = (Button) findViewById(R.id.buttonYes);
        this.buttonNo = (Button) findViewById(R.id.buttonNo);

        buttonYes.setOnClickListener(this);
        buttonNo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonYes){
            Intent intent = new Intent(CloseAppActivity.this, AuthenticationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        }
        if (v == buttonNo){
            CloseAppActivity.this.finish();
        }
    }
}
