package com.atp.live_atp_mobile;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by cesar on 01/03/2018.
 */

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvJ1;
    private TextView tvJ2;
    private int colorJ1;
    private int colorJ2;
    private ImageButton submit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        this.tvJ1 = (TextView) findViewById(R.id.textJ1);
        this.tvJ2 = (TextView) findViewById(R.id.textJ2);
        this.submit = (ImageButton) findViewById(R.id.imageButtonSubmit);

        tvJ1.setOnClickListener(this);
        tvJ2.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == tvJ1){
            tvJ2.setBackgroundResource(R.drawable.flat_textviewwhite); //0xffffffff
            tvJ1.setBackgroundResource(R.drawable.flat_textviewgreen); //0xff00ff00
        }
        if (v == tvJ2){
            tvJ1.setBackgroundResource(R.drawable.flat_textviewwhite);
            tvJ2.setBackgroundResource(R.drawable.flat_textviewgreen);
        }
        if (v == submit){
            this.colorJ1 = ((ColorDrawable) tvJ1.getBackground()).getColor();
            this.colorJ2 = ((ColorDrawable) tvJ2.getBackground()).getColor();
            if (colorJ1 != colorJ2){
                tvJ1.setText("yes");
            }
        }
        //Intent intent = new Intent(ServiceActivity.this, MainActivity.class);
        //startActivity(intent);
    }
}
