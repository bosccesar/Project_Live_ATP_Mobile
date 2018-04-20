package com.atp.live_atp_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by cesar on 12/03/2018.
 */

public class EndMatchActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvPlayerWin;
    private TextView tvScoreWin;
    private TextView tvScoreLost;
    private Button buttonMatchEnd;
    private String idRencontre;
    private String chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_of_match);

        this.tvPlayerWin = (TextView) findViewById(R.id.textViewPlayerWin);
        this.tvScoreWin = (TextView) findViewById(R.id.textViewScorePlayerWin);
        this.tvScoreLost = (TextView) findViewById(R.id.textViewPlayerLost);
        this.buttonMatchEnd = (Button) findViewById(R.id.buttonEndMatch);

        this.idRencontre = AuthenticationActivity.sharedpreferencesAuthentication.getString(AuthenticationActivity.IdRencontre, null);

        fillTextView();

        buttonMatchEnd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonMatchEnd){
            String winner = MainActivity.sharedpreferencesMainActivity.getString(MainActivity.IdPlayerWin, null);
            String looser = MainActivity.sharedpreferencesMainActivity.getString(MainActivity.IdPlayerLoose, null);
            this.chronometer = String.valueOf(MainActivity.timer);
            //Appel post à la BDD pour inscrire l'id des joueurs dans statsRencontre + inscrire le temps du match
            DataPostBDD postMatch = new DataPostBDD(EndMatchActivity.this);
            postMatch.postEndMatch(idRencontre);
            postMatch.postStatsEndMatch(idRencontre, winner, looser);
            postMatch.postTimeMatch(idRencontre, chronometer);
            Intent intent = new Intent(EndMatchActivity.this, AuthenticationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        }
    }

    public void fillTextView(){
        tvPlayerWin.setText(MainActivity.sharedpreferencesMainActivity.getString(MainActivity.PlayerWin, null));
        if (tvPlayerWin.getText().length() > 20){
            tvPlayerWin.setTextSize(30);
        }
        tvScoreWin.setText(MainActivity.sharedpreferencesMainActivity.getString(MainActivity.ScoreWin, null));
        tvScoreLost.setText(MainActivity.sharedpreferencesMainActivity.getString(MainActivity.ScoreLost, null));
    }
}