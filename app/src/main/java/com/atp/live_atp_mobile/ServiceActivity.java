package com.atp.live_atp_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import static com.atp.live_atp_mobile.AuthenticationActivity.User;
import static com.atp.live_atp_mobile.AuthenticationActivity.sharedpreferencesAuthentication;

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
    private String user;
    private boolean admin;

    public static final String PLAYERS = "Players";
    public static final String CATEGORY = "categories";
    public static final String Player1 = "player1";
    public static final String ConcatPlayer1 = "concatPlayer1";
    public static final String Player2 = "player2";
    public static final String ConcatPlayer2 = "concatPlayer2";
    public static final String Category = "category";
    public static final String CodeJ1 = "codeJ1";
    public static final String CodeJ2 = "codeJ2";
    public static final String Player1Team1 = "player1Team1";
    public static final String Player2Team1 = "player2Team1";
    public static final String Player1Team2 = "player1Team2";
    public static final String Player2Team2 = "player2Team2";
    public static SharedPreferences sharedpreferencesService;

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
        this.user = sharedpreferencesAuthentication.getString(User, null);

        sharedpreferencesService = getSharedPreferences(PLAYERS, Context.MODE_PRIVATE);
        sharedpreferencesService = getSharedPreferences(CATEGORY, Context.MODE_PRIVATE);

        //Méthodes
        modeAdmin();
        displayTournament();
        displayStateTournamentCategoryAndPlayers();

        tvJ1.setOnClickListener(this);
        tvJ2.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == tvJ1){
            player = ConcatPlayer1;
            tvJ2.setBackgroundResource(R.drawable.flat_textviewwhite); //0xffffffff
            tvJ1.setBackgroundResource(R.drawable.flat_textviewgreen); //0xff00ff00
        }
        if (v == tvJ2){
            player = ConcatPlayer2;
            tvJ1.setBackgroundResource(R.drawable.flat_textviewwhite);
            tvJ2.setBackgroundResource(R.drawable.flat_textviewgreen);
        }
        if (v == submit){
            String valJ1=tvJ1.getText().toString();
            String valJ2=tvJ2.getText().toString();

            SharedPreferences.Editor editor = sharedpreferencesService.edit();

            if (player.equals(ConcatPlayer1)){
                editor.putString(ConcatPlayer1, valJ1);
                editor.putString(ConcatPlayer2, valJ2);
                editor.apply();
                Intent intent = new Intent(ServiceActivity.this, MainActivity.class);
                startActivity(intent);
            }
            else if (player.equals(ConcatPlayer2)){
                editor.putString(ConcatPlayer1, valJ2);
                editor.putString(ConcatPlayer2, valJ1);
                editor.putString(CodeJ1, CodeJ2);
                editor.putString(CodeJ2, CodeJ1);
                editor.putString(Player1Team1, Player1Team2);
                editor.putString(Player2Team1, Player2Team2);
                editor.putString(Player1Team2, Player1Team1);
                editor.putString(Player2Team2, Player2Team1);
                editor.apply();
                Intent intent = new Intent(ServiceActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

    public void displayTournament(){
        //Récupérer le nom du tournoi depuis l'activity précédente
        tvTournament.setText(sharedpreferencesAuthentication.getString(AuthenticationActivity.Tournament, null));
    }

    public void displayStateTournamentCategoryAndPlayers(){
        final SharedPreferences.Editor editor = sharedpreferencesService.edit();
        if (modeAdmin()){
            String resultState= "8e de finale";
            String resultCategory = "Simple messieurs";
            String player1 = "A.MURRAY";
            String player2 = "J.W.TSONGA";
            editor.putString(Category, resultCategory);
            editor.apply();
            tvStateTournament.setText(resultState);
            tvCategory.setText(resultCategory);
            tvJ1.setText(player1);
            tvJ2.setText(player2);
        }else {
            final ConfigBDD stateTournament = new ConfigBDD(ServiceActivity.this);
            stateTournament.setMyCallback(new MyCallback() {
                @Override
                public void onCallbackTournament(String nameTournament, String dateTournament) {
                }
                public void onCallbackStateTournament(String nameTour) {
                    tvStateTournament.setText(nameTour); //Affichage final du tour
                }
                @Override
                public void onCallbackBoard(int idCategory) {
                    stateTournament.loadModelCategoryFromFirebase(idCategory);
                }
                @Override
                public void onCallbackCategory(String nameCategory) {
                    tvCategory.setText(nameCategory); //Affichage final de la categorie
                    editor.putString(Category, nameCategory);
                    editor.apply();
                }
                @Override
                public void onCallbackUser(int idRencontre, String user, String passwordUser) {
                }
                @Override
                public void onCallbackMatch(boolean equipe, int idTableau, int idTour, int player1, int player2, int idTeam1, int idTeam2) {
                    stateTournament.loadModelStateTournamentFromFirebase(idTour); //Appel pour récupérer le nom du tour en passant l'idTour
                    stateTournament.loadModelBoardFromFirebase(idTableau); //Appel pour récupérer le nom de la categorie en passant l'idTableau
                    if (equipe) {
                        stateTournament.loadModelTeamFromFirebase(idTeam1, idTeam2, Category); //Appel pour récupérer la team en passant leur idTeam et la categorie
                    }else {
                        stateTournament.loadModelPlayersFromFirebase(player1, player2, Category); //Appel pour récupérer les joueurs en passant leur id et la categorie
                    }
                }
                @Override
                public void onCallbackPlayer1(int idPlayer1, String firstNamePlayer, String lastNamePlayer, String codeNationality) {
                    String concatName = firstNamePlayer.substring(0,1) + "." + lastNamePlayer;
                    tvJ1.setText(concatName); //Affichage final du joueur1
                    editor.putString(CodeJ1, codeNationality);
                    editor.putString(Player1, String.valueOf(idPlayer1));
                    editor.apply();
                }
                @Override
                public void onCallbackPlayer2(int idPlayer2, String firstNamePlayer, String lastNamePlayer, String codeNationality) {
                    String concatName = firstNamePlayer.substring(0,1) + "." + lastNamePlayer;
                    tvJ1.setText(concatName); //Affichage final du joueur2
                    editor.putString(CodeJ2, codeNationality);
                    editor.putString(Player2, String.valueOf(idPlayer2));
                    editor.apply();
                }
                @Override
                public void onCallbackTeam1(String codeNationality, int idPlayer1, int idPlayer2, String nameTeam) {
                    tvJ1.setText(nameTeam); //Affichage final de la team 1
                    editor.putString(CodeJ1, codeNationality);
                    editor.putString(Player1Team1, String.valueOf(idPlayer1));
                    editor.putString(Player2Team1, String.valueOf(idPlayer2));
                    editor.apply();
                }
                @Override
                public void onCallbackTeam2(String codeNationality, int idPlayer1, int idPlayer2, String nameTeam) {
                    tvJ2.setText(nameTeam); //Affichage final de la team 2
                    editor.putString(CodeJ2, codeNationality);
                    editor.putString(Player1Team2, String.valueOf(idPlayer1));
                    editor.putString(Player2Team2, String.valueOf(idPlayer2));
                    editor.apply();
                }
            });
            stateTournament.loadModelMatchFromFirebase(); //Appel pour recupérer l'idTour de la rencontre
        }
    }

    private boolean modeAdmin(){
        if (user.equals("admin")){
            admin = true;
        }
        return admin;
    }
}
