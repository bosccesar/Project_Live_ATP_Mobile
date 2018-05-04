package com.atp.live_atp_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by cesar on 13/03/2018.
 */

public class SanctionActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvPlayer1;
    private TextView tvPlayer2;
    private TextView playerSelect;
    private TextView tvSanction1;
    private TextView tvSanction2;
    private TextView tvSanction3;
    private TextView sanctionSelect;
    private ImageButton buttonSubmit;
    private ImageButton buttonResume;
    private String idRencontre;
    private String valJ1;
    private String valJ2;
    private String chronometer;
    private String category;

    public static final String PLAYERSANCTION= "PlayerSanction";
    public static final String PlayerSanction = "PlayerSanction";
    public static final String SanctionGame = "SanctionGame";
    public static final String Resume = "Resume";
    public static final String IdSanction = "IdSanction";
    public static SharedPreferences sharedpreferencesSanction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanction);

        this.tvPlayer1 = (TextView) findViewById(R.id.textSanctionJ1);
        this.tvPlayer2 = (TextView) findViewById(R.id.textSanctionJ2);
        this.tvSanction1 = (TextView) findViewById(R.id.textViewRappel);
        this.tvSanction2 = (TextView) findViewById(R.id.textViewSanctionGame);
        this.tvSanction3 = (TextView) findViewById(R.id.textViewExclusion);
        this.buttonSubmit = (ImageButton) findViewById(R.id.imageButtonSubmitSanction);
        this.buttonResume = (ImageButton) findViewById(R.id.imageButtonResume);

        sharedpreferencesSanction = getSharedPreferences(PLAYERSANCTION, Context.MODE_PRIVATE);

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
        tvSanction1.setOnClickListener(this);
        tvSanction2.setOnClickListener(this);
        tvSanction3.setOnClickListener(this);
        buttonSubmit.setOnClickListener(this);
        buttonResume.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = sharedpreferencesSanction.edit();
        editor.putString(Resume, "false"); //Valeur par defaut
        editor.apply();
        if (v == buttonResume){
            editor.putString(Resume, "true");
            editor.apply();
            SanctionActivity.this.finish();
        }
        if (v == tvPlayer1){
            playerSelect = tvPlayer1;
            tvPlayer2.setBackgroundResource(R.drawable.flat_textviewwhite); //0xffffffff
            tvPlayer1.setBackgroundResource(R.drawable.flat_textviewgreen); //0xff00ff00
        }
        if (v == tvPlayer2){
            playerSelect = tvPlayer2;
            tvPlayer1.setBackgroundResource(R.drawable.flat_textviewwhite);
            tvPlayer2.setBackgroundResource(R.drawable.flat_textviewgreen);
        }
        if (v == tvSanction1){
            sanctionSelect = tvSanction1;
            tvSanction2.setBackgroundResource(R.color.DarkGray);
            tvSanction3.setBackgroundResource(R.color.DarkGray);
            tvSanction1.setBackgroundResource(R.color.MiGreen);
        }
        if (v == tvSanction2){
            sanctionSelect = tvSanction2;
            tvSanction1.setBackgroundResource(R.color.DarkGray);
            tvSanction3.setBackgroundResource(R.color.DarkGray);
            tvSanction2.setBackgroundResource(R.color.MiGreen);
        }
        if (v == tvSanction3){
            sanctionSelect = tvSanction3;
            tvSanction1.setBackgroundResource(R.color.DarkGray);
            tvSanction2.setBackgroundResource(R.color.DarkGray);
            tvSanction3.setBackgroundResource(R.color.MiGreen);
        }
        if (v == buttonSubmit){
            long timer = SystemClock.elapsedRealtime() - MainActivity.timer.getBase() - 3600000;
            this.chronometer = (String) DateFormat.format("HH:mm:ss", timer);
            if (playerSelect == tvPlayer1){
                editor.putString(IdSanction, "IdSanction"); //Valeur par default
                editor.apply();
                if (sanctionSelect == tvSanction1){
                    //Appel post à la BDD pour inscrire le rappel a l'ordre via l'id du joueur 1
                    DataPostBDD postMatch = new DataPostBDD(SanctionActivity.this);
                    postMatch.postSanctionOrderMatch(idRencontre, valJ1, category);
                    SanctionActivity.this.finish();
                }else if (sanctionSelect == tvSanction2){
                    //Appel post à la BDD pour inscrire la sanction jeu via l'id du joueur 1
                    editor.putString(PlayerSanction, tvPlayer1.getText().toString()); //Recuperation du joueur pour la sanction
                    editor.putString(SanctionGame, "true");
                    editor.putString(IdSanction, "sanction2");
                    editor.apply();
                    DataPostBDD postMatch = new DataPostBDD(SanctionActivity.this);
                    postMatch.postSanctionGameMatch(idRencontre, valJ1, category);
                    SanctionActivity.this.finish();
                }else if (sanctionSelect == tvSanction3){
                    //Appel post à la BDD pour inscrire l'exclusion
                    DataPostBDD postMatch = new DataPostBDD(SanctionActivity.this);
                    postMatch.postEndMatch(idRencontre);
                    postMatch.postSanctionExclusionMatch(idRencontre, valJ1, valJ2, category);
                    postMatch.postTimeMatch(idRencontre, chronometer);
                    Intent intent = new Intent(SanctionActivity.this, AuthenticationActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                }
            }else if (playerSelect == tvPlayer2){
                if (sanctionSelect == tvSanction1){
                    //Appel post à la BDD pour inscrire le rappel a l'ordre via l'id du joueur 2
                    DataPostBDD postMatch = new DataPostBDD(SanctionActivity.this);
                    postMatch.postSanctionOrderMatch(idRencontre, valJ2, category);
                    SanctionActivity.this.finish();
                }else if (sanctionSelect == tvSanction2){
                    //Appel post à la BDD pour inscrire la sanction jeu via l'id du joueur 2
                    editor.putString(PlayerSanction, tvPlayer2.getText().toString()); //Recuperation du joueur pour la sanction
                    editor.putString(SanctionGame, "true");
                    editor.apply();
                    DataPostBDD postMatch = new DataPostBDD(SanctionActivity.this);
                    postMatch.postSanctionGameMatch(idRencontre, valJ2, category);
                    SanctionActivity.this.finish();
                }else if (sanctionSelect == tvSanction3){
                    //Appel post à la BDD pour inscrire l'exclusion
                    DataPostBDD postMatch = new DataPostBDD(SanctionActivity.this);
                    postMatch.postEndMatch(idRencontre);
                    postMatch.postSanctionExclusionMatch(idRencontre, valJ2, valJ1, category);
                    postMatch.postTimeMatch(idRencontre, chronometer);
                    Intent intent = new Intent(SanctionActivity.this, AuthenticationActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                }
            }
        }
    }

    public void fillTextView(){ //Textview des joueurs remplis avec les textview de serviceActivity
        tvPlayer1.setText(ServiceActivity.sharedpreferencesService.getString(ServiceActivity.ConcatPlayer1, null));
        if (tvPlayer1.getText().length() > 15){
            tvPlayer1.setTextSize(28);
        }
        tvPlayer2.setText(ServiceActivity.sharedpreferencesService.getString(ServiceActivity.ConcatPlayer2, null));
        if (tvPlayer2.getText().length() > 15){
            tvPlayer2.setTextSize(28);
        }
    }
}
