package com.atp.live_atp_mobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        fillTextView();

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
        if (v == buttonResume){
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
            if (playerSelect == tvPlayer1 && (sanctionSelect == tvSanction1 || sanctionSelect == tvSanction2 || sanctionSelect == tvSanction3)){
                String valJ1 = tvPlayer1.getText().toString();
                //Appel post à la BDD pour inscrire la sanction via l'id du joueur 1 au travers du string juste au dessus
                SanctionActivity.this.finish();
            }else if (playerSelect == tvPlayer2 && (sanctionSelect == tvSanction1 || sanctionSelect == tvSanction2 || sanctionSelect == tvSanction3)){
                String valJ2 = tvPlayer2.getText().toString();
                //Appel post à la BDD pour inscrire la sanction via l'id du joueur 2 au travers du string juste au dessus
                SanctionActivity.this.finish();
            }
        }
    }

    public void fillTextView(){ //Textview des joueurs remplis avec les textview de serviceActivity
        tvPlayer1.setText(ServiceActivity.sharedpreferences.getString(ServiceActivity.Player1, null));
        tvPlayer2.setText(ServiceActivity.sharedpreferences.getString(ServiceActivity.Player2, null));
    }
}
