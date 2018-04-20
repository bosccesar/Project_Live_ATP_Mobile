package com.atp.live_atp_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by cesar on 13/03/2018.
 */

public class BreakActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String FORMAT = "%02d:%02d";
    private TextView tvBreak1;
    private TextView tvBreak2;
    private TextView tvBreak3;
    private TextView tvBreak4;
    private TextView tvBreak5;
    private TextView tvBreak6;
    private Chronometer chronoBreakVal1;
    private Chronometer chronoBreakVal2;
    private Chronometer chronoBreakVal3;
    private Chronometer chronoBreakVal4;
    private TextView breakSelect;
    private ImageButton buttonSubmit;
    private ImageButton buttonResume;
    private String idRencontre;
    private String chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_break);

        this.tvBreak1 = (TextView) findViewById(R.id.textViewBreakField);
        this.tvBreak2 = (TextView) findViewById(R.id.textViewBreakSet);
        this.tvBreak3 = (TextView) findViewById(R.id.textViewBreakToilet);
        this.tvBreak4 = (TextView) findViewById(R.id.textViewBreakHeal);
        this.tvBreak5 = (TextView) findViewById(R.id.textViewBreakExceptional);
        this.tvBreak6 = (TextView) findViewById(R.id.textViewBreakAbort);
        this.chronoBreakVal1 = (Chronometer) findViewById(R.id.chronometerBreakFieldVal);
        this.chronoBreakVal2 = (Chronometer) findViewById(R.id.chronometerBreakSetVal);
        this.chronoBreakVal3 = (Chronometer) findViewById(R.id.chronometerBreakToiletVal);
        this.chronoBreakVal4 = (Chronometer) findViewById(R.id.chronometerBreakHealVal);
        this.buttonSubmit = (ImageButton) findViewById(R.id.imageButtonSubmitBreak);
        this.buttonResume = (ImageButton) findViewById(R.id.imageButtonResume);

        chronoBreakVal1.setText(R.string.valBreakField);
        chronoBreakVal2.setText(R.string.valBreakSet);
        chronoBreakVal3.setText(R.string.valBreakToiletHeal);
        chronoBreakVal4.setText(R.string.valBreakToiletHeal);

        this.idRencontre = AuthenticationActivity.sharedpreferencesAuthentication.getString(AuthenticationActivity.IdRencontre, null);

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
            tvBreak5.setBackgroundResource(R.color.DarkGray);
            tvBreak6.setBackgroundResource(R.color.MiGreen);
        }
        if (v == buttonSubmit){
            if (breakSelect == tvBreak1){ //Pause changement de terrain
                //Appel post à la BDD pour incrementer le changement de terrain
                reverseChronometer(chronoBreakVal1, 90000, 50);//Millisecondes en parametre
                tvBreak2.setEnabled(false);
                tvBreak3.setEnabled(false);
                tvBreak4.setEnabled(false);
                tvBreak5.setEnabled(false);
                tvBreak6.setEnabled(false);
                buttonSubmit.setEnabled(false);
            }else if (breakSelect == tvBreak2){ //Pause set
                //Appel post à la BDD pour incrementer la pause set
                reverseChronometer(chronoBreakVal2, 120000, 50);//Millisecondes en parametre
                tvBreak1.setEnabled(false);
                tvBreak3.setEnabled(false);
                tvBreak4.setEnabled(false);
                tvBreak5.setEnabled(false);
                tvBreak6.setEnabled(false);
                buttonSubmit.setEnabled(false);
            }else if (breakSelect == tvBreak3){ //Pause toilettes
                //Appel post à la BDD pour incrementer la pause toilettes
                reverseChronometer(chronoBreakVal3, 180000, 50);//Millisecondes en parametre
                tvBreak1.setEnabled(false);
                tvBreak2.setEnabled(false);
                tvBreak4.setEnabled(false);
                tvBreak5.setEnabled(false);
                tvBreak6.setEnabled(false);
                buttonSubmit.setEnabled(false);
            }else if (breakSelect == tvBreak4){ //Pause soigneurs
                //Appel post à la BDD pour incrementer la pause soigneurs
                reverseChronometer(chronoBreakVal4, 180000, 50);//Millisecondes en parametre
                tvBreak1.setEnabled(false);
                tvBreak2.setEnabled(false);
                tvBreak3.setEnabled(false);
                tvBreak5.setEnabled(false);
                tvBreak6.setEnabled(false);
                buttonSubmit.setEnabled(false);
            }else if (breakSelect == tvBreak5){ //Arret exceptionnel du match
                this.chronometer = String.valueOf(MainActivity.timer);
                //Appel post à la BDD pour incrementer la pause exceptionnelle + inscrire le temps du match
                DataPostBDD postMatch = new DataPostBDD(BreakActivity.this);
                postMatch.postEndMatch(idRencontre);
                postMatch.postExceptionnalBreakMatch(idRencontre);
                postMatch.postTimeMatch(idRencontre, chronometer);
                Intent intent = new Intent(BreakActivity.this, AuthenticationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
            }else if (breakSelect == tvBreak6){ //Abandon d'un joueur
                Intent intent = new Intent(BreakActivity.this, AbortActivity.class);
                startActivity(intent); //Affiche activity abandon avec le joueur a selectionner
            }
        }
    }

    public void reverseChronometer(final Chronometer chronometerValue, int timeStart, int refresh){
        new CountDownTimer(timeStart, refresh) {

            public void onTick(long millisUntilFinished) {

                chronometerValue.setText(""+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                buttonResume.setEnabled(false);
            }

            public void onFinish() {
                chronometerValue.setText(R.string.valBreak);
                BreakActivity.this.finish();
            }
        }.start();
    }
}
