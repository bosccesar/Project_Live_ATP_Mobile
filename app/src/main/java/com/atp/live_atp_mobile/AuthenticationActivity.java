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
import android.widget.Toast;

/**
 * Created by cesar on 27/02/2018.
 */

public class AuthenticationActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvTournament;
    private TextView tvDate;
    public static EditText editLogin;
    public static EditText editPassword;
    private View vue;
    private ImageButton submit;

    public static String login;
    public static String password;

    public static final String RECUPBDD = "RecupBdd";
    public static final String Tournament = "tournament";
    public static final String User = "user";
    public static final String IdRencontre = "IdRencontre";
    public static SharedPreferences sharedpreferencesAuthentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        //Initialisation des éléments
        this.tvTournament = (TextView) findViewById(R.id.textViewTournament);
        this.tvDate = (TextView) findViewById(R.id.textViewDate);
        editLogin = (EditText) findViewById(R.id.editTextLogin);
        editPassword = (EditText) findViewById(R.id.editTextPassword);
        this.vue = findViewById(android.R.id.content);
        this.submit = (ImageButton) findViewById(R.id.imageButtonSubmit);

        sharedpreferencesAuthentication = getSharedPreferences(RECUPBDD, Context.MODE_PRIVATE);

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

    public void getGps(ConfigBDD configBDD){
        Gps gps = new Gps(AuthenticationActivity.this, AuthenticationActivity.this);
        gps.observers.add(configBDD);
        gps.startGMS();
    }

    public void displayTournament(){
        ConfigBDD tournament = new ConfigBDD(AuthenticationActivity.this);
        getGps(tournament);
        tournament.setMyCallback(new MyCallback() {
            @Override
            public void onCallbackTournament(String value, String dateTournament) { //Nom et date du tournoi récupérés de la bdd
                tvTournament.setText(value);
                tvDate.setText(dateTournament);
                SharedPreferences.Editor editor = sharedpreferencesAuthentication.edit();
                editor.putString(Tournament, value); //Insertion du resultat de la requete dans la sauvegarde
                editor.apply();
            }
        });
    }

    public void verifIdent(){
        login = editLogin.getText().toString();
        password = editPassword.getText().toString();
        ConfigBDD user = new ConfigBDD(AuthenticationActivity.this);
        user.setMyCallback(new MyCallback() {
            @Override
            public void onCallbackUser(int idRencontre, String user, String passwordUser) {
                if (login.equals(user) || login.equals("admin")){ //Si le login qui a ete renseigné correspond à celui de la BDD ou si on lance l'application en mode admin
                    SharedPreferences.Editor editor = sharedpreferencesAuthentication.edit();
                    editor.putString(User, user); //Sauvegarde du user afin de recuperer les joueurs des rencontres qui lui sont assignés pour les afficher sur l'activityService
                    editor.putString(IdRencontre, String.valueOf(idRencontre)); //Sauvegarde de l'id de la rencontre user afin de recuperer le tour du tournoi pour l'afficher sur l'activityService
                    editor.apply();
                    Intent intent = new Intent(AuthenticationActivity.this, ServiceActivity.class);
                    startActivity(intent);
                }
            }
        });
        user.loadModelUserFromFirebase();
    }
}
