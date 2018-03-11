package com.atp.live_atp_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by cesar on 01/03/2018.
 */

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvTournament;
    private TextView tvStateTournament;
    private TextView tvCategory;
    private TextView tvJ1;
    private TextView tvJ2;
    private ImageButton submit;
    private String player;

    public static final String PLAYERS = "Players";
    public static final String CATEGORY = "Players";
    public static final String Player1 = "player1";
    public static final String Player2 = "player2";
    public static final String Category = "Double dames";
    public static SharedPreferences sharedpreferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        this.tvTournament = (TextView) findViewById(R.id.textViewTournament);
        this.tvStateTournament = (TextView) findViewById(R.id.textViewStateTournament);
        this.tvCategory = (TextView) findViewById(R.id.textViewCategory);
        this.tvJ1 = (TextView) findViewById(R.id.textJ1);
        this.tvJ2 = (TextView) findViewById(R.id.textJ2);
        this.submit = (ImageButton) findViewById(R.id.imageButtonSubmit);
        this.player = "";

        tvJ1.setOnClickListener(this);
        tvJ2.setOnClickListener(this);
        submit.setOnClickListener(this);

        sharedpreferences = getSharedPreferences(PLAYERS, Context.MODE_PRIVATE);
        sharedpreferences = getSharedPreferences(CATEGORY, Context.MODE_PRIVATE);

        //Méthodes
        displayTournament();
        displayStateTournament();
        displayCategory();
    }

    @Override
    public void onClick(View v) {
        if (v == tvJ1){
            player = Player1;
            tvJ2.setBackgroundResource(R.drawable.flat_textviewwhite); //0xffffffff
            tvJ1.setBackgroundResource(R.drawable.flat_textviewgreen); //0xff00ff00
        }
        if (v == tvJ2){
            player = Player2;
            tvJ1.setBackgroundResource(R.drawable.flat_textviewwhite);
            tvJ2.setBackgroundResource(R.drawable.flat_textviewgreen);
        }
        if (v == submit){
            String valJ1=tvJ1.getText().toString();
            String valJ2=tvJ2.getText().toString();

            SharedPreferences.Editor editor = sharedpreferences.edit();

            if (player.equals("player1")){
                editor.putString(Player1, valJ1);
                editor.putString(Player2, valJ2);
                editor.commit();
                Intent intent = new Intent(ServiceActivity.this, MainActivity.class);
                startActivity(intent);
            }
            else if (player.equals("player2")){
                editor.putString(Player1, valJ2);
                editor.putString(Player2, valJ1);
                editor.commit();
                Intent intent = new Intent(ServiceActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

    public void displayTournament(){
        //Récupérer le nom du tournoi depuis l'activity précédente
        tvTournament.setText(AuthenticationActivity.sharedpreferences.getString(AuthenticationActivity.Tournament, null));
    }

    public void displayStateTournament(){
        //Appel get du tour en fonction de la rencontre
        //GetString du résultat de la bdd
        //Exemple
        String resultBdd = "8e de finale";
        tvStateTournament.setText(resultBdd);
    }

    public void displayCategory(){
        //Appel get de la categorie en fonction du tableau
        //GetString du résultat de la bdd
        //Exemple
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String resultBdd = "Double messieurs";
        editor.putString(Category, resultBdd); //Insertion du resultat de la requete dans la sauvegarde
        editor.commit();
        tvCategory.setText(resultBdd); //id de la tv category
    }
}
