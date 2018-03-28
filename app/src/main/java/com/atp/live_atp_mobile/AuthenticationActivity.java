package com.atp.live_atp_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by cesar on 27/02/2018.
 */

public class AuthenticationActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvTournament;
    private TextView tvDate;
    private EditText editLogin;
    private EditText editPassword;
    private View vue;
    private ImageButton submit;

    public static final String RECUPBDD = "RecupBdd";
    public static final String Tournament = "tournament";
    public static SharedPreferences sharedpreferencesAuthentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        //Initialisation des éléments
        this.tvTournament = (TextView) findViewById(R.id.textViewTournament);
        this.tvDate = (TextView) findViewById(R.id.textViewDate);
        this.editLogin = (EditText) findViewById(R.id.editTextLogin);
        this.editPassword = (EditText) findViewById(R.id.editTextPassword);
        this.vue = findViewById(android.R.id.content);
        this.submit = (ImageButton) findViewById(R.id.imageButtonSubmit);

        //Méthodes
        displayTournament();

        //Activation de l'intéraction
        vue.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == vue){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(vue.getWindowToken(), 0);
        }
        if (v == submit){
            verifIdent();
        }
    }

    public void verifIdent(){
        String login = editLogin.getText().toString();
        String password = editPassword.getText().toString();
        //Comparaison à la bdd de l'autentification renseigné
        //boolean loginRenseigné = résultat de la requete à la bdd
        //Comparaison à la bdd du password renseigné
        //boolean passwordRenseigné = résultat de la requete à la bdd
        if (login.equals("admin")){ //Remplacer par loginRenseigné == true
            if (password.equals("admin")){ //Remplacer par loginRenseigné == true
                Intent intent = new Intent(AuthenticationActivity.this, ServiceActivity.class);
                startActivity(intent);
            }else {
                editPassword.setText("");
            }
        }else {
            editLogin.setText("");
        }
    }

    public void getGps(ConfigBDD configBDD){
        Gps gps = new Gps(AuthenticationActivity.this, AuthenticationActivity.this);
        gps.observers.add(configBDD);
        gps.startGMS();
    }

    public void displayTournament(){
        sharedpreferencesAuthentication = getSharedPreferences(RECUPBDD, Context.MODE_PRIVATE);
        ConfigBDD tournament = new ConfigBDD(AuthenticationActivity.this);
        getGps(tournament);
        tournament.setMyCallback(new MyCallback() {
            public void onCallbackTournament(String value, String dateTournament) { //Nom et date du tournoi récupérés de la bdd
                tvTournament.setText(value);
                tvDate.setText(dateTournament);
                SharedPreferences.Editor editor = sharedpreferencesAuthentication.edit();
                editor.putString(Tournament, value); //Insertion du resultat de la requete dans la sauvegarde
                editor.apply();
            }
        });
    }
}
