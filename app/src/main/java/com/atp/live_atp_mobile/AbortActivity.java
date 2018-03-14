package com.atp.live_atp_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abort);

        this.tvPlayer1 = (TextView) findViewById(R.id.textviewAbortJ1);
        this.tvPlayer2 = (TextView) findViewById(R.id.textviewAbortJ2);
        this.buttonSubmit = (ImageButton) findViewById(R.id.imageButtonSubmitAbort);
        this.buttonResume = (ImageButton) findViewById(R.id.imageButtonAbortResume);

        fillTextView();

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
            if (abortSelect == tvPlayer1){
                String valJ1 = tvPlayer1.getText().toString();
                //Appel post à la BDD pour inscrire l'abandon via l'id du joueur 1 au travers du string juste au dessus
                Intent intent = new Intent(AbortActivity.this, AuthenticationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
            }else if (abortSelect == tvPlayer2){
                String valJ2 = tvPlayer2.getText().toString();
                //Appel post à la BDD pour inscrire l'abandon via l'id du joueur 2 au travers du string juste au dessus
                Intent intent = new Intent(AbortActivity.this, AuthenticationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
            }
        }
    }

    public void fillTextView(){
        tvPlayer1.setText(MainActivity.sharedpreferences.getString(MainActivity.Player1, null));
        tvPlayer2.setText(MainActivity.sharedpreferences.getString(MainActivity.Player2, null));
    }
}
