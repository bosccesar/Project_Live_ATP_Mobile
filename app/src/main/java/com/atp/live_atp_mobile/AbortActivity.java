package com.atp.live_atp_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by cesar on 14/03/2018.
 */

public class AbortActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvPlayer1;
    private TextView tvPlayer2;
    private TextView abortSelect;
    private ImageButton buttonSubmit;
    private ImageButton buttonResume;
    private String valJ1;
    private String valJ2;
    private String idRencontre;
    private String chronometer;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abort);

        this.tvPlayer1 = (TextView) findViewById(R.id.textviewAbortJ1);
        this.tvPlayer2 = (TextView) findViewById(R.id.textviewAbortJ2);
        this.buttonSubmit = (ImageButton) findViewById(R.id.imageButtonSubmitAbort);
        this.buttonResume = (ImageButton) findViewById(R.id.imageButtonAbortResume);

        fillTextView();

        this.idRencontre = AuthenticationActivity.sharedpreferencesAuthentication.getString(AuthenticationActivity.IdRencontre, null);
        if (AuthenticationActivity.login.equals("admin")){
            this.category = ServiceActivity.sharedpreferencesService.getString(ServiceActivity.CategoryAdmin, null); //Récuperation de la category en mode admin
        }else {
            this.category = ServiceActivity.sharedpreferencesService.getString(ServiceActivity.Category, null); //Récuperation de la category pour évaluer s'il y a super tie-break et 2 ou 3 set gangants
        }
        this.valJ1 = ServiceActivity.sharedpreferencesService.getString(ServiceActivity.IdPlayer1, null);
        this.valJ2 = ServiceActivity.sharedpreferencesService.getString(ServiceActivity.IdPlayer2, null);

        tvPlayer1.setOnClickListener(this);
        tvPlayer2.setOnClickListener(this);
        buttonSubmit.setOnClickListener(this);
        buttonResume.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonResume){
            AbortActivity.this.finish();
        }
        if (v == tvPlayer1) {
            abortSelect = tvPlayer1;
            tvPlayer2.setBackgroundResource(R.drawable.flat_textviewwhite);
            tvPlayer1.setBackgroundResource(R.drawable.flat_textviewgreen);
        }
        if (v == tvPlayer2) {
            abortSelect = tvPlayer2;
            tvPlayer1.setBackgroundResource(R.drawable.flat_textviewwhite);
            tvPlayer2.setBackgroundResource(R.drawable.flat_textviewgreen);
        }
        if (v == buttonSubmit){
            long timer = SystemClock.elapsedRealtime() - MainActivity.timer.getBase() - 3600000;
            this.chronometer = (String) DateFormat.format("HH:mm:ss", timer);
            if (abortSelect == tvPlayer1){
                //Appel post à la BDD pour inscrire l'abandon via l'id du joueur 1 au travers du string juste au dessus
                DataPostBDD postMatch = new DataPostBDD(AbortActivity.this);
                postMatch.postEndMatch(idRencontre);
                postMatch.postStatsAbortMatch(idRencontre, valJ1, valJ2, category);
                postMatch.postTimeMatch(idRencontre, chronometer);

                Intent intent = new Intent(AbortActivity.this, AuthenticationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
            }else if (abortSelect == tvPlayer2){
                //Appel post à la BDD pour inscrire l'abandon via l'id du joueur 2 au travers du string juste au dessus
                DataPostBDD postMatch = new DataPostBDD(AbortActivity.this);
                postMatch.postEndMatch(idRencontre);
                postMatch.postStatsAbortMatch(idRencontre, valJ2, valJ1, category);
                postMatch.postTimeMatch(idRencontre, chronometer);

                Intent intent = new Intent(AbortActivity.this, AuthenticationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
            }
        }
    }

    public void fillTextView(){
        tvPlayer1.setText(MainActivity.sharedpreferencesMainActivity.getString(MainActivity.Player1, null));
        if (tvPlayer1.getText().length() > 15){
            tvPlayer1.setTextSize(28);
        }
        tvPlayer2.setText(MainActivity.sharedpreferencesMainActivity.getString(MainActivity.Player2, null));
        if (tvPlayer2.getText().length() > 15){
            tvPlayer2.setTextSize(28);
        }
    }
}
