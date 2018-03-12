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

public class PopEndMatch extends AppCompatActivity implements View.OnClickListener {

    private TextView tvPlayerWin;
    private TextView tvScoreWin;
    private TextView tvScoreLost;
    private Button matchEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_endofmatch);

        this.tvPlayerWin = (TextView) findViewById(R.id.textViewPlayerWin);
        this.tvScoreWin = (TextView) findViewById(R.id.textViewScorePlayerWin);
        this.tvScoreLost = (TextView) findViewById(R.id.textViewPlayerLost);
        this.matchEnd = (Button) findViewById(R.id.buttonEndMatch);

        fillTextView();
    }

    @Override
    public void onClick(View v) {
        if (v == matchEnd){
            Intent i = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage( getBaseContext().getPackageName() );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }

    public void fillTextView(){
        tvPlayerWin.setText(MainActivity.sharedpreferences.getString(MainActivity.PlayerWin, null));
        tvScoreWin.setText(MainActivity.sharedpreferences.getString(MainActivity.ScoreWin, null));
        tvScoreLost.setText(MainActivity.sharedpreferences.getString(MainActivity.ScoreLost, null));
    }
}