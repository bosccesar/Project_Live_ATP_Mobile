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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_of_match);

        this.tvPlayerWin = (TextView) findViewById(R.id.textViewPlayerWin);
        this.tvScoreWin = (TextView) findViewById(R.id.textViewScorePlayerWin);
        this.tvScoreLost = (TextView) findViewById(R.id.textViewPlayerLost);
        this.buttonMatchEnd = (Button) findViewById(R.id.buttonEndMatch);

        fillTextView();

        buttonMatchEnd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonMatchEnd){
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