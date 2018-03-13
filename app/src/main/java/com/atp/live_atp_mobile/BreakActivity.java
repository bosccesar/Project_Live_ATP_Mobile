package com.atp.live_atp_mobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by cesar on 13/03/2018.
 */

public class BreakActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvBreak1;
    private TextView tvBreak2;
    private TextView tvBreak3;
    private TextView tvBreak4;
    private TextView tvBreak5;
    private TextView tvBreak6;
    private TextView tvBreakVal1;
    private TextView tvBreakVal2;
    private TextView tvBreakVal3;
    private TextView tvBreakVal4;
    private TextView breakSelect;
    private ImageButton buttonSubmit;
    private ImageButton buttonResume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanction);

        this.tvBreak1 = (TextView) findViewById(R.id.textViewBreakField);
        this.tvBreak2 = (TextView) findViewById(R.id.textViewBreakSet);
        this.tvBreak3 = (TextView) findViewById(R.id.textViewBreakToilet);
        this.tvBreak4 = (TextView) findViewById(R.id.textViewBreakHeal);
        this.tvBreak5 = (TextView) findViewById(R.id.textViewBreakExceptional);
        this.tvBreak6 = (TextView) findViewById(R.id.textViewBreakAbort);
        this.tvBreakVal1 = (TextView) findViewById(R.id.textViewBreakFieldVal);
        this.tvBreakVal2 = (TextView) findViewById(R.id.textViewBreakSetVal);
        this.tvBreakVal3 = (TextView) findViewById(R.id.textViewBreakToiletVal);
        this.tvBreakVal4 = (TextView) findViewById(R.id.textViewBreakHealVal);
        this.buttonSubmit = (ImageButton) findViewById(R.id.imageButtonSubmitBreak);
        this.buttonResume = (ImageButton) findViewById(R.id.imageButtonResume);


        tvBreak1.setOnClickListener(this);
        tvBreak2.setOnClickListener(this);
        tvBreak3.setOnClickListener(this);
        tvBreak4.setOnClickListener(this);
        tvBreak5.setOnClickListener(this);
        tvBreak6.setOnClickListener(this);
        buttonSubmit.setOnClickListener(this);
        buttonResume.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonResume){
            BreakActivity.this.finish();
        }
        if (v == tvBreak1){
            breakSelect = tvBreak1;
            tvBreak2.setBackgroundResource(R.color.DarkGray);
            tvBreak3.setBackgroundResource(R.color.DarkGray);
            tvBreak4.setBackgroundResource(R.color.DarkGray);
            tvBreak5.setBackgroundResource(R.color.DarkGray);
            tvBreak6.setBackgroundResource(R.color.DarkGray);
            tvBreak1.setBackgroundResource(R.color.MiGreen);
        }
        if (v == tvBreak2){
            breakSelect = tvBreak2;
            tvBreak1.setBackgroundResource(R.color.DarkGray);
            tvBreak3.setBackgroundResource(R.color.DarkGray);
            tvBreak4.setBackgroundResource(R.color.DarkGray);
            tvBreak5.setBackgroundResource(R.color.DarkGray);
            tvBreak6.setBackgroundResource(R.color.DarkGray);
            tvBreak2.setBackgroundResource(R.color.MiGreen);
        }
        if (v == tvBreak3){
            breakSelect = tvBreak3;
            tvBreak1.setBackgroundResource(R.color.DarkGray);
            tvBreak2.setBackgroundResource(R.color.DarkGray);
            tvBreak4.setBackgroundResource(R.color.DarkGray);
            tvBreak5.setBackgroundResource(R.color.DarkGray);
            tvBreak6.setBackgroundResource(R.color.DarkGray);
            tvBreak3.setBackgroundResource(R.color.MiGreen);
        }
        if (v == tvBreak4){
            breakSelect = tvBreak4;
            tvBreak1.setBackgroundResource(R.color.DarkGray);
            tvBreak2.setBackgroundResource(R.color.DarkGray);
            tvBreak3.setBackgroundResource(R.color.DarkGray);
            tvBreak5.setBackgroundResource(R.color.DarkGray);
            tvBreak6.setBackgroundResource(R.color.DarkGray);
            tvBreak4.setBackgroundResource(R.color.MiGreen);
        }
        if (v == tvBreak5){
            breakSelect = tvBreak5;
            tvBreak1.setBackgroundResource(R.color.DarkGray);
            tvBreak2.setBackgroundResource(R.color.DarkGray);
            tvBreak3.setBackgroundResource(R.color.DarkGray);
            tvBreak4.setBackgroundResource(R.color.DarkGray);
            tvBreak6.setBackgroundResource(R.color.DarkGray);
            tvBreak5.setBackgroundResource(R.color.MiGreen);
        }
        if (v == tvBreak6){
            breakSelect = tvBreak6;
            tvBreak1.setBackgroundResource(R.color.DarkGray);
            tvBreak2.setBackgroundResource(R.color.DarkGray);
            tvBreak3.setBackgroundResource(R.color.DarkGray);
            tvBreak4.setBackgroundResource(R.color.DarkGray);
            tvBreak6.setBackgroundResource(R.color.DarkGray);
            tvBreak5.setBackgroundResource(R.color.MiGreen);
        }
        if (v == buttonSubmit){
            if (breakSelect == tvBreak1){
                //Appel post à la BDD pour incrementer le changement de terrain
                BreakActivity.this.finish();
            }else if (breakSelect == tvBreak2){
                //Appel post à la BDD pour incrementer la pause set
                BreakActivity.this.finish();
            }else if (breakSelect == tvBreak3){
                //Appel post à la BDD pour incrementer la pause toilettes
                BreakActivity.this.finish();
            }else if (breakSelect == tvBreak4){
                //Appel post à la BDD pour incrementer la pause soigneurs
                BreakActivity.this.finish();
            }else if (breakSelect == tvBreak5){
                //Appel post à la BDD pour incrementer la pause exceptionnelle
                BreakActivity.this.finish();
            }else if (breakSelect == tvBreak6){
                //Affiche activite selection joueur pour abandon et appel post à la BDD pour incrementer la pause abandon selon l'd du joueur
                BreakActivity.this.finish();
            }
        }
    }
}
