package com.atp.live_atp_mobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.atp.live_atp_mobile.SanctionActivity.sharedpreferencesSanction;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static int[] tabPoint = {0, 15, 30, 40, -1};

    private TextView tvJ1;
    private TextView tvJ2;
    private TextView tvScoreJ1;
    private TextView tvScoreJ2;
    private TextView tvChallengeJ1;
    private TextView tvChallengeJ2;
    private TextView tvScoreSetTotalJ1;
    private TextView tvScoreSetTotalJ2;
    private TextView tvSet1J1;
    private TextView tvSet2J1;
    private TextView tvSet3J1;
    private TextView tvSet4J1;
    private TextView tvSet5J1;
    private TextView tvSet1J2;
    private TextView tvSet2J2;
    private TextView tvSet3J2;
    private TextView tvSet4J2;
    private TextView tvSet5J2;
    private TextView tvTieBreak;
    private ImageView ballServiceJ1;
    private ImageView ballServiceJ2;
    private ImageView nationalityJ1;
    private ImageView nationalityJ2;
    private Button buttonStart;
    private Button buttonChallengeJ1;
    private Button buttonChallengeJ2;
    private Button buttonAceJ1;
    private Button buttonAceJ2;
    private Button button2emeServiceJ1;
    private Button button2emeServiceJ2;
    private Button buttonLetJ1;
    private Button buttonLetJ2;
    private Button buttonOutJ1;
    private Button buttonOutJ2;
    private Button buttonNetJ1;
    private Button buttonNetJ2;
    private Button buttonDownJ1;
    private Button buttonDownJ2;
    private Button buttonCancelDownJ1;
    private Button buttonCancelDownJ2;
    private ImageButton buttonJ1;
    private ImageButton buttonJ2;
    private ImageButton buttonBreak;
    private ImageButton buttonAdvertissement;
    private ImageButton buttonClose;
    private Chronometer timer;

    private boolean tieBreak;
    private boolean superTieBreak;
    private boolean previousTieBreak;
    private boolean isBreak;
    private boolean finalTieBreak;
    private boolean advertissement; //Permet de savoir si la sanctionActivity a ete appele
    private int countNbService;
    private int numSet;
    private boolean twoSetWin;
    private String tvPreviousScoreJ1;
    private String tvPreviousScoreJ2;
    private String firstServiceTieBreak;
    private String tournament;
    private String category;

    public static final String PLAYERSWINLOST = "PlayersWinLost";
    public static final String PlayerWin = "playerWin";
    public static final String Player1 = "player1";
    public static final String Player2 = "player2";
    public static final String ScoreWin = "scoreWin";
    public static final String ScoreLost = "scoreLost";
    public static SharedPreferences sharedpreferencesMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialisation des éléments
        //Element recupéré d'autres activity et BDD
        this.tournament = AuthenticationActivity.sharedpreferencesAuthentication.getString(AuthenticationActivity.Tournament, null); //Récuperation du tournoi pour évaluer si c'est un tournoi du Grand Chelem
        this.category = ServiceActivity.sharedpreferencesService.getString(ServiceActivity.Category, null); //Récuperation de la category pour évaluer s'il y a super tie-break et 2 ou 3 set gangants

        //Joueurs
        this.tvJ1 = (TextView) findViewById(R.id.textJ1);
        this.tvJ2 = (TextView) findViewById(R.id.textJ2);
        //Drapeau nationalite
        this.nationalityJ1 = (ImageView) findViewById(R.id.imageViewNationalityJ1);
        this.nationalityJ2 = (ImageView) findViewById(R.id.imageViewNationalityJ2);
        playerNationality();

        //Chronomètre
        this.buttonStart = (Button) findViewById(R.id.buttonStart);
        this.timer = (Chronometer) findViewById(R.id.chronometerMain);

        //Score
        this.buttonJ1 = (ImageButton) findViewById(R.id.imageButtonModifPointJ1);
        this.buttonJ2 = (ImageButton) findViewById(R.id.imageButtonModifPointJ2);
        this.tvScoreJ1 = (TextView) findViewById(R.id.textScoreJ1);
        this.tvScoreJ2 = (TextView) findViewById(R.id.textScoreJ2);
        this.tvPreviousScoreJ1 = "";
        this.tvPreviousScoreJ2 = "";

        //Challenge
        this.buttonChallengeJ1 = (Button) findViewById(R.id.buttonChallengeJ1);
        this.buttonChallengeJ2 = (Button) findViewById(R.id.buttonChallengeJ2);
        this.tvChallengeJ1 = (TextView) findViewById(R.id.textViewNbChallengeJ1);
        this.tvChallengeJ2 = (TextView) findViewById(R.id.textViewNbChallengeJ2);
        this.buttonDownJ1 = (Button) findViewById(R.id.buttonDownJ1);
        this.buttonDownJ2 = (Button) findViewById(R.id.buttonDownJ2);
        this.buttonDownJ1.setVisibility(View.INVISIBLE);
        this.buttonDownJ2.setVisibility(View.INVISIBLE);
        this.buttonCancelDownJ1 = (Button) findViewById(R.id.buttonCancelDownJ1);
        this.buttonCancelDownJ2 = (Button) findViewById(R.id.buttonCancelDownJ2);
        this.buttonCancelDownJ1.setVisibility(View.INVISIBLE);
        this.buttonCancelDownJ2.setVisibility(View.INVISIBLE);

        //Ace
        this.buttonAceJ1 = (Button) findViewById(R.id.buttonAceJ1);
        this.buttonAceJ2 = (Button) findViewById(R.id.buttonAceJ2);

        //Faute
        this.buttonOutJ1 = (Button) findViewById(R.id.buttonOutJ1);
        this.buttonOutJ2 = (Button) findViewById(R.id.buttonOutJ2);
        this.buttonNetJ1 = (Button) findViewById(R.id.buttonFiletJ1);
        this.buttonNetJ2 = (Button) findViewById(R.id.buttonFiletJ2);

        //Jeu
        this.tvScoreSetTotalJ1 = (TextView) findViewById(R.id.textViewScoreSetTotalJ1);
        this.tvScoreSetTotalJ2 = (TextView) findViewById(R.id.textViewScoreSetTotalJ2);
        this.tvSet1J1 = (TextView) findViewById(R.id.textViewScore1SetJ1);
        this.tvSet2J1 = (TextView) findViewById(R.id.textViewScore2SetJ1);
        this.tvSet3J1 = (TextView) findViewById(R.id.textViewScore3SetJ1);
        this.tvSet4J1 = (TextView) findViewById(R.id.textViewScore4SetJ1);
        this.tvSet5J1 = (TextView) findViewById(R.id.textViewScore5SetJ1);
        this.tvSet1J2 = (TextView) findViewById(R.id.textViewScore1SetJ2);
        this.tvSet2J2 = (TextView) findViewById(R.id.textViewScore2SetJ2);
        this.tvSet3J2 = (TextView) findViewById(R.id.textViewScore3SetJ2);
        this.tvSet4J2 = (TextView) findViewById(R.id.textViewScore4SetJ2);
        this.tvSet5J2 = (TextView) findViewById(R.id.textViewScore5SetJ2);
        this.tvSet1J1.setTextColor(0); //Met le texte en transparent
        this.tvSet2J1.setTextColor(0); //Met le texte en transparent
        this.tvSet3J1.setTextColor(0); //Met le texte en transparent
        this.tvSet4J1.setTextColor(0); //Met le texte en transparent
        this.tvSet5J1.setTextColor(0); //Met le texte en transparent
        this.tvSet1J2.setTextColor(0); //Met le texte en transparent
        this.tvSet2J2.setTextColor(0); //Met le texte en transparent
        this.tvSet3J2.setTextColor(0); //Met le texte en transparent
        this.tvSet4J2.setTextColor(0); //Met le texte en transparent
        this.tvSet5J2.setTextColor(0); //Met le texte en transparent

        //Tie-Break
        this.tvTieBreak = (TextView) findViewById(R.id.textViewTieBreak);
        tvTieBreak.setVisibility(View.INVISIBLE);
        this.tieBreak = false;
        this.previousTieBreak = false;
        this.superTieBreak = false;
        this.finalTieBreak = rulesLastSetTournament();

        //Set
        this.numSet = 1;
        this.twoSetWin = rulesSetWinTournament();

        //Point rejoué
        this.button2emeServiceJ1 = (Button) findViewById(R.id.button2emeServiceJ1);
        this.button2emeServiceJ2 = (Button) findViewById(R.id.button2emeServiceJ2);
        this.buttonLetJ1 = (Button) findViewById(R.id.buttonLetJ1);
        this.buttonLetJ2 = (Button) findViewById(R.id.buttonLetJ2);

        //Changement de service
        this.ballServiceJ1 = (ImageView) findViewById(R.id.imageViewBallServiceJ1);
        this.ballServiceJ2 = (ImageView) findViewById(R.id.imageViewBallServiceJ2);
        //Service
        this.countNbService = 0;
        this.isBreak = false;
        this.firstServiceTieBreak = "";
        this.ballServiceJ2.setVisibility(View.INVISIBLE);
        this.button2emeServiceJ2.setVisibility(View.INVISIBLE);
        this.buttonLetJ2.setVisibility(View.INVISIBLE);

        //Pause
        this.buttonBreak = (ImageButton) findViewById(R.id.imageButtonPause);

        //Advertissement
        this.buttonAdvertissement = (ImageButton) findViewById(R.id.imageButtonAdvertissement);

        //sanctionActivity
        this.advertissement = false;

        //Close
        this.buttonClose = (ImageButton) findViewById(R.id.imageButtonClose);

        sharedpreferencesMainActivity = getSharedPreferences(PLAYERSWINLOST, Context.MODE_PRIVATE);

        //Interaction impossible sur les boutons
        interactionButtonFalse();

        //Activation du click de chaque bouton
        buttonStart.setOnClickListener(this);
        buttonJ1.setOnClickListener(this);
        buttonJ2.setOnClickListener(this);
        buttonChallengeJ1.setOnClickListener(this);
        buttonChallengeJ2.setOnClickListener(this);
        buttonAceJ1.setOnClickListener(this);
        buttonAceJ2.setOnClickListener(this);
        buttonOutJ1.setOnClickListener(this);
        buttonOutJ2.setOnClickListener(this);
        buttonNetJ1.setOnClickListener(this);
        buttonNetJ2.setOnClickListener(this);
        button2emeServiceJ1.setOnClickListener(this);
        button2emeServiceJ2.setOnClickListener(this);
        buttonLetJ1.setOnClickListener(this);
        buttonLetJ2.setOnClickListener(this);
        buttonDownJ1.setOnClickListener(this);
        buttonDownJ2.setOnClickListener(this);
        buttonCancelDownJ1.setOnClickListener(this);
        buttonCancelDownJ2.setOnClickListener(this);
        buttonBreak.setOnClickListener(this);
        buttonAdvertissement.setOnClickListener(this);
        buttonClose.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (advertissement) {
            incrementationGameSanction(); //Recuperation la sanction d'activitySanction pour incrementer le set en cours
        }
    }

    @Override
    public void onClick(View view) {
        //Démarrage du match
        if (view == buttonStart){
            startChronometer();
        }
        if (view == buttonBreak){
            SharedPreferences.Editor editor = sharedpreferencesMainActivity.edit();
            editor.putString(Player1, tvJ1.getText().toString());
            editor.putString(Player2, tvJ2.getText().toString());
            editor.commit();
            Intent intent = new Intent(MainActivity.this, BreakActivity.class);
            startActivity(intent);
        }
        if (view == buttonAdvertissement){
            Intent intent = new Intent(MainActivity.this, SanctionActivity.class);
            startActivity(intent);
            advertissement = true;
        }
        if (view == buttonClose){
            Intent intent = new Intent(MainActivity.this, CloseAppActivity.class);
            startActivity(intent);
        }
        //Score
        if (view == buttonJ1){
            if (superTieBreak){
                superTieBreak(tvScoreJ1, tvScoreJ2, verifSetFinish(tvSet1J1, tvSet2J1, tvSet3J1, tvSet4J1, tvSet5J1));
                //Ajouter fenetre résumant le score et indiquant quel joueur a gagné avec bouton close pour réinitialiser l'appli
            }else {
                if (!tieBreak) {
                    onClickButtonScoreUp(tvScoreJ1, tvScoreJ2, tvSet1J1, tvSet2J1, tvSet3J1, tvSet4J1, tvSet5J1, tvSet1J2, tvSet2J2, tvSet3J2, tvSet4J2, tvSet5J2);
                    if (tvScoreJ1.getText().toString().equals("00") && tvSet1J1.getText().toString().equals("0") && numSet == 1) {
                        buttonChallengeJ2.setEnabled(false);
                    } else if (tvScoreJ2.getText().toString().equals("00") && tvSet1J2.getText().toString().equals("0") && numSet == 1) {
                        buttonChallengeJ1.setEnabled(false);
                    }
                } else {
                    onClickButtonScoreUpTieBreak(tvScoreJ1, tvScoreJ2, verifSetFinish(tvSet1J1, tvSet2J1, tvSet3J1, tvSet4J1, tvSet5J1));
                }
            }
        }
        if (view == buttonJ2){
            if (superTieBreak){
                superTieBreak(tvScoreJ2, tvScoreJ1, verifSetFinish(tvSet1J2, tvSet2J2, tvSet3J2, tvSet4J2, tvSet5J2));
                //Ajouter fenetre résumant le score et indiquant quel joueur a gagné avec bouton close pour réinitialiser l'appli
            }else {
                if (!tieBreak) {
                    onClickButtonScoreUp(tvScoreJ2, tvScoreJ1, tvSet1J2, tvSet2J2, tvSet3J2, tvSet4J2, tvSet5J2, tvSet1J1, tvSet2J1, tvSet3J1, tvSet4J1, tvSet5J1);
                    if (tvScoreJ1.getText().toString().equals("00") && tvSet1J1.getText().toString().equals("0") && numSet == 1) {
                        buttonChallengeJ2.setEnabled(false);
                    } else if (tvScoreJ2.getText().toString().equals("00") && tvSet1J2.getText().toString().equals("0") && numSet == 1) {
                        buttonChallengeJ1.setEnabled(false);
                    }
                } else {
                    onClickButtonScoreUpTieBreak(tvScoreJ2, tvScoreJ1, verifSetFinish(tvSet1J2, tvSet2J2, tvSet3J2, tvSet4J2, tvSet5J2));
                }
            }
        }
        if (view == buttonAceJ1){
            if (superTieBreak){
                superTieBreak(tvScoreJ1, tvScoreJ2, verifSetFinish(tvSet1J1, tvSet2J1, tvSet3J1, tvSet4J1, tvSet5J1));
                toast(view);
                //Ajouter fenetre résumant le score et indiquant quel joueur a gagné avec bouton close pour réinitialiser l'appli
            }else {
                if (!tieBreak) {
                    onClickButtonAce(tvScoreJ1, tvScoreJ2, tvSet1J1, tvSet2J1, tvSet3J1, tvSet4J1, tvSet5J1, tvSet1J2, tvSet2J2, tvSet3J2, tvSet4J2, tvSet5J2);
                    toast(view);
                    if (tvScoreJ1.getText().toString().equals("00") && tvSet1J1.getText().toString().equals("0") && numSet == 1) {
                        buttonChallengeJ2.setEnabled(false);
                    } else if (tvScoreJ2.getText().toString().equals("00") && tvSet1J2.getText().toString().equals("0") && numSet == 1) {
                        buttonChallengeJ1.setEnabled(false);
                    }
                } else {
                    onClickButtonScoreUpTieBreak(tvScoreJ1, tvScoreJ2, verifSetFinish(tvSet1J1, tvSet2J1, tvSet3J1, tvSet4J1, tvSet5J1));
                    toast(view);
                }
            }
        }
        if (view == buttonAceJ2){
            if (superTieBreak){
                superTieBreak(tvScoreJ2, tvScoreJ1, verifSetFinish(tvSet1J2, tvSet2J2, tvSet3J2, tvSet4J2, tvSet5J2));
                toast(view);
                //Ajouter fenetre résumant le score et indiquant quel joueur a gagné avec bouton close pour réinitialiser l'appli
            }else {
                if (!tieBreak) {
                    onClickButtonAce(tvScoreJ2, tvScoreJ1, tvSet1J2, tvSet2J2, tvSet3J2, tvSet4J2, tvSet5J2, tvSet1J1, tvSet2J1, tvSet3J1, tvSet4J1, tvSet5J1);
                    toast(view);
                    if (tvScoreJ1.getText().toString().equals("00") && tvSet1J1.getText().toString().equals("0") && numSet == 1) {
                        buttonChallengeJ2.setEnabled(false);
                    } else if (tvScoreJ2.getText().toString().equals("00") && tvSet1J2.getText().toString().equals("0") && numSet == 1) {
                        buttonChallengeJ1.setEnabled(false);
                    }
                } else {
                    onClickButtonScoreUpTieBreak(tvScoreJ2, tvScoreJ1, verifSetFinish(tvSet1J2, tvSet2J2, tvSet3J2, tvSet4J2, tvSet5J2));
                    toast(view);
                }
            }
        }
        //Challenge
        if (view == buttonChallengeJ1){
            onClickButtonIncrementationChallenge(tvChallengeJ1, buttonDownJ2, buttonCancelDownJ2);
        }
        if (view == buttonChallengeJ2){
            onClickButtonIncrementationChallenge(tvChallengeJ2, buttonDownJ1, buttonCancelDownJ1);
        }
        //Faute
        if (view == buttonOutJ1){
            if (superTieBreak){
                superTieBreak(tvScoreJ2, tvScoreJ1, verifSetFinish(tvSet1J2, tvSet2J2, tvSet3J2, tvSet4J2, tvSet5J2));
                toast(view);
                //Ajouter fenetre résumant le score et indiquant quel joueur a gagné avec bouton close pour réinitialiser l'appli
            }else {
                if (!tieBreak) {
                    onClickButtonFaute(tvScoreJ2, tvScoreJ1, tvSet1J2, tvSet2J2, tvSet3J2, tvSet4J2, tvSet5J2, tvSet1J1, tvSet2J1, tvSet3J1, tvSet4J1, tvSet5J1);
                    toast(view);
                } else {
                    onClickButtonScoreUpTieBreak(tvScoreJ2, tvScoreJ1, verifSetFinish(tvSet1J2, tvSet2J2, tvSet3J2, tvSet4J2, tvSet5J2));
                    toast(view);
                }
            }
        }
        if (view == buttonOutJ2){
            if (superTieBreak){
                superTieBreak(tvScoreJ1, tvScoreJ2, verifSetFinish(tvSet1J1, tvSet2J1, tvSet3J1, tvSet4J1, tvSet5J1));
                toast(view);
                //Ajouter fenetre résumant le score et indiquant quel joueur a gagné avec bouton close pour réinitialiser l'appli
            }else {
                if (!tieBreak) {
                    onClickButtonFaute(tvScoreJ1, tvScoreJ2, tvSet1J1, tvSet2J1, tvSet3J1, tvSet4J1, tvSet5J1, tvSet1J2, tvSet2J2, tvSet3J2, tvSet4J2, tvSet5J2);
                    toast(view);
                } else {
                    onClickButtonScoreUpTieBreak(tvScoreJ1, tvScoreJ2, verifSetFinish(tvSet1J1, tvSet2J1, tvSet3J1, tvSet4J1, tvSet5J1));
                    toast(view);
                }
            }
        }
        if (view == buttonNetJ1){
            if (superTieBreak){
                superTieBreak(tvScoreJ2, tvScoreJ1, verifSetFinish(tvSet1J2, tvSet2J2, tvSet3J2, tvSet4J2, tvSet5J2));
                toast(view);
                //Ajouter fenetre résumant le score et indiquant quel joueur a gagné avec bouton close pour réinitialiser l'appli
            }else {
                if (!tieBreak) {
                    onClickButtonFaute(tvScoreJ2, tvScoreJ1, tvSet1J2, tvSet2J2, tvSet3J2, tvSet4J2, tvSet5J2, tvSet1J1, tvSet2J1, tvSet3J1, tvSet4J1, tvSet5J1);
                    toast(view);
                } else {
                    onClickButtonScoreUpTieBreak(tvScoreJ2, tvScoreJ1, verifSetFinish(tvSet1J2, tvSet2J2, tvSet3J2, tvSet4J2, tvSet5J2));
                    toast(view);
                }
            }
        }
        if (view == buttonNetJ2){
            if (superTieBreak){
                superTieBreak(tvScoreJ1, tvScoreJ2, verifSetFinish(tvSet1J1, tvSet2J1, tvSet3J1, tvSet4J1, tvSet5J1));
                toast(view);
                //Ajouter fenetre résumant le score et indiquant quel joueur a gagné avec bouton close pour réinitialiser l'appli
            }else {
                if (!tieBreak) {
                    onClickButtonFaute(tvScoreJ1, tvScoreJ2, tvSet1J1, tvSet2J1, tvSet3J1, tvSet4J1, tvSet5J1, tvSet1J2, tvSet2J2, tvSet3J2, tvSet4J2, tvSet5J2);
                    toast(view);
                } else {
                    onClickButtonScoreUpTieBreak(tvScoreJ1, tvScoreJ2, verifSetFinish(tvSet1J1, tvSet2J1, tvSet3J1, tvSet4J1, tvSet5J1));
                    toast(view);
                }
            }
        }
        if (view == button2emeServiceJ1){
            //Incrémenter dans la bdd la statistique 2eme service pour le joueur
            toast(view);
        }
        if (view == button2emeServiceJ2){
            //Incrémenter dans la bdd la statistique 2eme service pour le joueur
            toast(view);
        }
        if (view == buttonLetJ1){
            //Incrémenter dans la bdd la statistique Let pour le joueur
            toast(view);
        }
        if (view == buttonLetJ2){
            //Incrémenter dans la bdd la statistique Let pour le joueur
            toast(view);
        }
        if (view == buttonDownJ1){
            onClickButtonScoreDown(tvScoreJ1, tvScoreJ2, buttonDownJ1, buttonCancelDownJ1, tvChallengeJ2, tvChallengeJ1, buttonChallengeJ2, buttonChallengeJ1, tvPreviousScoreJ1, tvPreviousScoreJ2, verifSetFinish(tvSet1J1, tvSet2J1, tvSet3J1, tvSet4J1, tvSet5J1));
        }
        if (view == buttonDownJ2){
            onClickButtonScoreDown(tvScoreJ2, tvScoreJ1, buttonDownJ2, buttonCancelDownJ2, tvChallengeJ1, tvChallengeJ2, buttonChallengeJ1, buttonChallengeJ2, tvPreviousScoreJ2, tvPreviousScoreJ1, verifSetFinish(tvSet1J2, tvSet2J2, tvSet3J2, tvSet4J2, tvSet5J2));
        }
        if (view == buttonCancelDownJ1){
            onClickButtonScoreCancelDown(buttonCancelDownJ1, buttonDownJ1, tvChallengeJ2, tvChallengeJ1, buttonChallengeJ2, buttonChallengeJ1);
        }
        if (view == buttonCancelDownJ2){
            onClickButtonScoreCancelDown(buttonCancelDownJ2, buttonDownJ2, tvChallengeJ1, tvChallengeJ2, buttonChallengeJ1, buttonChallengeJ2);
        }
    }

    public void interactionButtonFalse(){
        buttonJ1.setEnabled(false);
        buttonJ2.setEnabled(false);
        buttonChallengeJ1.setEnabled(false);
        buttonChallengeJ2.setEnabled(false);
        buttonAceJ1.setEnabled(false);
        buttonAceJ2.setEnabled(false);
        button2emeServiceJ1.setEnabled(false);
        button2emeServiceJ2.setEnabled(false);
        buttonLetJ1.setEnabled(false);
        buttonLetJ2.setEnabled(false);
        buttonOutJ1.setEnabled(false);
        buttonOutJ2.setEnabled(false);
        buttonNetJ1.setEnabled(false);
        buttonNetJ2.setEnabled(false);
        buttonBreak.setEnabled(false);
        buttonAdvertissement.setEnabled(false);
        buttonClose.setEnabled(false);
    }

    public void interactionButtonTrue(){
        buttonJ1.setEnabled(true);
        buttonJ2.setEnabled(true);
        buttonAceJ1.setEnabled(true);
        buttonAceJ2.setEnabled(true);
        button2emeServiceJ1.setEnabled(true);
        button2emeServiceJ2.setEnabled(true);
        buttonLetJ1.setEnabled(true);
        buttonLetJ2.setEnabled(true);
        buttonOutJ1.setEnabled(true);
        buttonOutJ2.setEnabled(true);
        buttonNetJ1.setEnabled(true);
        buttonNetJ2.setEnabled(true);
        buttonBreak.setEnabled(true);
        buttonAdvertissement.setEnabled(true);
        buttonClose.setEnabled(true);
    }

    public void startChronometer(){
        interactionButtonTrue();
        timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            public void onChronometerTick(Chronometer cArg) {
                long t = SystemClock.elapsedRealtime() - cArg.getBase();
                cArg.setText(DateFormat.format("HH:mm:ss", t));
            }
        });
        timer.start(); //Démarre le chronomètre
        timer.setBase(SystemClock.elapsedRealtime());
        buttonStart.setVisibility(View.INVISIBLE); //Fais disparaitre le bouton start
    }

    public void onClickButtonScoreUp(TextView tvScore, TextView tvScoreAdv, TextView tvScoreSet1, TextView tvScoreSet2, TextView tvScoreSet3, TextView tvScoreSet4, TextView tvScoreSet5, TextView tvScoreSet1Adv, TextView tvScoreSet2Adv, TextView tvScoreSet3Adv, TextView tvScoreSet4Adv, TextView tvScoreSet5Adv){
        int presentIntVal;
        int presentIntValAdv;
        String presentValStr=tvScore.getText().toString();
        String presentValStrAdv=tvScoreAdv.getText().toString();
        if (!presentValStr.equals("AV")) {
            presentIntVal = Integer.parseInt(presentValStr);
            if (presentValStrAdv.equals("AV")){ //Condition permettant d'éviter que le string parsé en int juste à la sortie du if ne se fasse pas tel quel. AV en int n'est pas possible
                presentValStrAdv = "-1";
            }
            presentIntValAdv = Integer.parseInt(presentValStrAdv);
        }else{
            presentIntVal = -1;
            presentIntValAdv = -1;
        }

        for (int pos = 0; pos < 5; ++pos) {
            if (tabPoint[pos] == presentIntVal && pos < 3) {
                tvScore.setText(String.valueOf(tabPoint[pos + 1]));
                if (tvScoreJ1.getText().toString().equals("15") && tvSet1J1.getText().toString().equals("0")) {
                    buttonChallengeJ2.setEnabled(true);
                }
                if (tvScoreJ2.getText().toString().equals("15") && tvSet1J2.getText().toString().equals("0")){
                    buttonChallengeJ1.setEnabled(true);
                }
            }else if (tabPoint[pos] == presentIntVal && pos == 3){
                if (presentIntValAdv == -1){ //Le joueur met un point alors que l'adversaire avait un avantage -> remise à 40 pour les 2
                    tvScore.setText(String.valueOf(presentIntVal)); //Remise à 40 pour le joueur
                    tvScoreAdv.setText(String.valueOf(presentIntVal)); //Remise à 40 pour l'adversaire en utilisant la valeur du joueur
                }else if(presentIntValAdv == 40){ //Le joueur met un point alors qu'ils sont à égalité -> avantage pour lui
                    tvScore.setText(R.string.advantage); //Avantage pour le joueur
                    //Incrémenter dans la bdd la statistique avantage pour le joueur
                }else{ //Le joueur adverse n'a ni 40 ni avantage (0 ou 15 ou 30) donc le joueur gagne le point
                    tvPreviousScoreJ1 = tvScoreJ1.getText().toString(); //Garde en mémoire le score précédent
                    tvPreviousScoreJ2 = tvScoreJ2.getText().toString(); //Garde en mémoire le score précédent
                    tvScore.setText(R.string.startGame);
                    tvScoreAdv.setText(R.string.startGame);
                    tvTieBreak.setVisibility(View.INVISIBLE);
                    onClickButtonIncrementationSet(tvScore, tvScoreAdv, tvScoreSet1, tvScoreSet2, tvScoreSet3, tvScoreSet4, tvScoreSet5, tvScoreSet1Adv, tvScoreSet2Adv, tvScoreSet3Adv, tvScoreSet4Adv, tvScoreSet5Adv); //Incrémentation du nombre de jeu du set correspondant car le jeu est gagné
                }
            }else if (tabPoint[pos] == presentIntVal && pos == 4){
                tvPreviousScoreJ1 = tvScoreJ1.getText().toString(); //Garde en mémoire le score précédent
                tvPreviousScoreJ2 = tvScoreJ2.getText().toString(); //Garde en mémoire le score précédent
                tvScore.setText(R.string.startGame);
                tvScoreAdv.setText(R.string.startGame);
                onClickButtonIncrementationSet(tvScore, tvScoreAdv, tvScoreSet1, tvScoreSet2, tvScoreSet3, tvScoreSet4, tvScoreSet5, tvScoreSet1Adv, tvScoreSet2Adv, tvScoreSet3Adv, tvScoreSet4Adv, tvScoreSet5Adv); //Incrémentation du nombre de jeu du set correspondant car le jeu est gagné
            }
        }
    }

    public void onClickButtonAce(TextView tvScore, TextView tvScoreAdv, TextView tvScoreSet1, TextView tvScoreSet2, TextView tvScoreSet3, TextView tvScoreSet4, TextView tvScoreSet5, TextView tvScoreSet1Adv, TextView tvScoreSet2Adv, TextView tvScoreSet3Adv, TextView tvScoreSet4Adv, TextView tvScoreSet5Adv){ //Clique sur Ace pour ajouter des points sur le service sans touche de la part de l'adversaire
        onClickButtonScoreUp(tvScore, tvScoreAdv, tvScoreSet1, tvScoreSet2, tvScoreSet3, tvScoreSet4, tvScoreSet5, tvScoreSet1Adv, tvScoreSet2Adv, tvScoreSet3Adv, tvScoreSet4Adv, tvScoreSet5Adv);
        //Incrémentation des Ace dans la table Statistique de la base de données associé à l'Id du joueur correspondant
    }

    public void onClickButtonFaute(TextView tvScore, TextView tvScoreAdv, TextView tvScoreSet1, TextView tvScoreSet2, TextView tvScoreSet3, TextView tvScoreSet4, TextView tvScoreSet5, TextView tvScoreSet1Adv, TextView tvScoreSet2Adv, TextView tvScoreSet3Adv, TextView tvScoreSet4Adv, TextView tvScoreSet5Adv){ //Clique sur Ace pour ajouter des points sur le service sans touche de la part de l'adversaire
        onClickButtonScoreUp(tvScore, tvScoreAdv, tvScoreSet1, tvScoreSet2, tvScoreSet3, tvScoreSet4, tvScoreSet5, tvScoreSet1Adv, tvScoreSet2Adv, tvScoreSet3Adv, tvScoreSet4Adv, tvScoreSet5Adv);
        //Incrémentation des out dans la table Statistique de la base de données associé à l'Id du joueur correspondant
        //Incrémentation des filet dans la table Statistique de la base de données associé à l'Id du joueur correspondant
    }

    public void onClickButtonIncrementationChallenge(TextView tvChallenge, Button buttonDown, Button buttonCancelDown){ //Compte le nombre de challenge effectué par joueur. 3 challenge max par joueur
        String presentValStr = tvChallenge.getText().toString();
        int presentIntVal = Integer.parseInt(presentValStr);
        if (presentIntVal < 3) {
            tvChallenge.setText(String.valueOf(presentIntVal + 1)); //Incrémente le nombre de challenge (replay demandé par un joueur pour valider ou non le point)
            buttonDown.setVisibility(View.VISIBLE);
            buttonCancelDown.setVisibility(View.VISIBLE);
            interactionButtonFalse();
        }
    }

    public void onClickButtonIncrementationSet(TextView tvScore, TextView tvScoreAdv, TextView tvScoreSet1, TextView tvScoreSet2, TextView tvScoreSet3, TextView tvScoreSet4, TextView tvScoreSet5, TextView tvScoreSet1Adv, TextView tvScoreSet2Adv, TextView tvScoreSet3Adv, TextView tvScoreSet4Adv, TextView tvScoreSet5Adv) { //Compte le nombre de jeu gagné par joueur
        String valStrSet1 = tvScoreSet1.getText().toString();
        String valStrSet2 = tvScoreSet2.getText().toString();
        String valStrSet3 = tvScoreSet3.getText().toString();
        String valStrSet4 = tvScoreSet4.getText().toString();
        String valStrSet5 = tvScoreSet5.getText().toString();
        String valStrSet1Adv = tvScoreSet1Adv.getText().toString();
        String valStrSet2Adv = tvScoreSet2Adv.getText().toString();
        String valStrSet3Adv = tvScoreSet3Adv.getText().toString();
        String valStrSet4Adv = tvScoreSet4Adv.getText().toString();
        String valStrSet5Adv = tvScoreSet5Adv.getText().toString();
        int intValSet1 = Integer.parseInt(valStrSet1);
        int intValSet2 = Integer.parseInt(valStrSet2);
        int intValSet3 = Integer.parseInt(valStrSet3);
        int intValSet4 = Integer.parseInt(valStrSet4);
        int intValSet5 = Integer.parseInt(valStrSet5);
        int intValSet1Adv = Integer.parseInt(valStrSet1Adv);
        int intValSet2Adv = Integer.parseInt(valStrSet2Adv);
        int intValSet3Adv = Integer.parseInt(valStrSet3Adv);
        int intValSet4Adv = Integer.parseInt(valStrSet4Adv);
        int intValSet5Adv = Integer.parseInt(valStrSet5Adv);

        if ((twoSetWin && !finalTieBreak && numSet == 3) || (!twoSetWin && !finalTieBreak && numSet == 5)) {
            if (numSet == 5){
                tvScoreSet3 = tvScoreSet5;
                valStrSet5 = tvScoreSet3.getText().toString();
                intValSet3 = Integer.parseInt(valStrSet5);
                tvScoreSet3Adv = tvScoreSet5Adv;
                valStrSet5Adv = tvScoreSet3Adv.getText().toString();
                intValSet3Adv = Integer.parseInt(valStrSet5Adv);
            }
            if (intValSet3 < 5) {
                tvScoreSet3.setTextColor(-16777216); //Met le texte en noir
                tvScoreSet3Adv.setTextColor(-16777216); //Met le texte en noir
                tvScoreSet3.setText(String.valueOf(intValSet3 + 1)); //Incrémente le set 3
            }else if (intValSet3 > 4 && intValSet3Adv > 4) {
                if (intValSet3 == intValSet3Adv + 1) { //Si on a une différence de 2 points
                    tvScoreSet3.setText(String.valueOf(intValSet3 + 1));
                    tvPreviousScoreJ1 = tvScoreJ1.getText().toString(); //Garde en mémoire le score pécédent
                    tvPreviousScoreJ2 = tvScoreJ2.getText().toString(); //Garde en mémoire le score pécédent
                    tvScoreSet3.setTypeface(null, Typeface.BOLD);
                    incremementationSetTotal(tvScore);
                    gameOver();
                } else {
                    tvScoreSet3.setText(String.valueOf(intValSet3 + 1));
                }
            } else {
                tvScoreSet3.setText(String.valueOf(intValSet3 + 1));
                tvPreviousScoreJ1 = tvScoreJ1.getText().toString(); //Garde en mémoire le score pécédent
                tvPreviousScoreJ2 = tvScoreJ2.getText().toString(); //Garde en mémoire le score pécédent
                tvScoreSet3.setTypeface(null, Typeface.BOLD);
                incremementationSetTotal(tvScore);
                gameOver();
            }
        } else {
            if (intValSet1 < 7 && numSet == 1) {
                tvScoreSet1.setTextColor(-16777216); //Met le texte en noir
                tvScoreSet1Adv.setTextColor(-16777216); //Met le texte en noir
                tvScoreSet1.setText(String.valueOf(intValSet1 + 1)); //Incrémente le set 1
                intValSet1 += 1;
                if (intValSet1 == 6 && intValSet1Adv == 6) {
                    transformTieBreak(tvScore, tvScoreAdv);
                } else if (intValSet1 == 6 && intValSet1Adv < 5 || intValSet1 == 7 && intValSet1Adv == 5) {
                    tvScoreSet1.setTypeface(null, Typeface.BOLD);
                    incremementationSetTotal(tvScore);
                    numSet++;
                }
            } else if (intValSet2 < 7 && numSet == 2) {
                tvScoreSet2.setTextColor(-16777216); //Met le texte en noir
                tvScoreSet2Adv.setTextColor(-16777216); //Met le texte en noir
                tvScoreSet2.setText(String.valueOf(intValSet2 + 1)); //Incrémente le set 2
                intValSet2 += 1;
                if (intValSet2 == 6 && intValSet2Adv == 6) {
                    transformTieBreak(tvScore, tvScoreAdv);
                } else if (intValSet2 == 6 && intValSet2Adv < 5 || intValSet2 == 7 && intValSet2Adv == 5) {
                    tvScoreSet2.setTypeface(null, Typeface.BOLD);
                    incremementationSetTotal(tvScore);
                    numSet++;
                }
                if ((tvScoreSetTotalJ1.getText().toString().equals("1") && tvScoreSetTotalJ2.getText().toString().equals("1")) && rulesSuperTieBreak()) { //Si un set partout dans les doubles (sauf double messieurs) alors ça déclenche le super tie break
                    transformSuperTieBreak(tvScore, tvScoreAdv);
                }
            } else if (intValSet3 < 7 && numSet == 3) {
                tvScoreSet3.setTextColor(-16777216); //Met le texte en noir
                tvScoreSet3Adv.setTextColor(-16777216); //Met le texte en noir
                tvScoreSet3.setText(String.valueOf(intValSet3 + 1)); //Incrémente le set 3
                intValSet3 += 1;
                if (intValSet3 == 6 && intValSet3Adv == 6) {
                    transformTieBreak(tvScore, tvScoreAdv);
                } else if (intValSet3 == 6 && intValSet3Adv < 5 || intValSet3 == 7 && intValSet3Adv == 5) {
                    tvScoreSet3.setTypeface(null, Typeface.BOLD);
                    incremementationSetTotal(tvScore);
                    numSet++;
                }
            } else if (intValSet4 < 7 && numSet == 4) {
                tvScoreSet4.setTextColor(-16777216); //Met le texte en noir
                tvScoreSet4Adv.setTextColor(-16777216); //Met le texte en noir
                tvScoreSet4.setText(String.valueOf(intValSet4 + 1)); //Incrémente le set 4
                intValSet4 += 1;
                if (intValSet4 == 6 && intValSet4Adv == 6) {
                    transformTieBreak(tvScore, tvScoreAdv);
                } else if (intValSet4 == 6 && intValSet4Adv < 5 || intValSet4 == 7 && intValSet4Adv == 5) {
                    tvScoreSet4.setTypeface(null, Typeface.BOLD);
                    incremementationSetTotal(tvScore);
                    numSet++;
                }
            } else if (intValSet5 < 7 && numSet == 5) {
                tvScoreSet5.setTextColor(-16777216); //Met le texte en noir
                tvScoreSet5Adv.setTextColor(-16777216); //Met le texte en noir
                tvScoreSet5.setText(String.valueOf(intValSet5 + 1)); //Incrémente le set 5
                intValSet5 += 1;
                if (intValSet5 == 6 && intValSet5Adv == 6) {
                    transformTieBreak(tvScore, tvScoreAdv);
                } else if (intValSet5 == 6 && intValSet5Adv < 5 || intValSet5 == 7 && intValSet5Adv == 5) {
                    tvScoreSet5.setTypeface(null, Typeface.BOLD);
                    incremementationSetTotal(tvScore);
                    numSet++;
                }
            }
            countNbService++;
            serviceChange();
            breaker(tvScore);
        }
    }

    public void incremementationSetTotal(TextView tvScore){ //Incrémente le nombre de set gagnés du joueur
        String StrSetTotal;
        int intSetTotal;
        if (tvScore == tvScoreJ1){
            StrSetTotal=tvScoreSetTotalJ1.getText().toString();
            intSetTotal = Integer.parseInt(StrSetTotal);
            tvScoreSetTotalJ1.setText(String.valueOf(intSetTotal + 1));
        }else if (tvScore == tvScoreJ2){
            StrSetTotal=tvScoreSetTotalJ2.getText().toString();
            intSetTotal = Integer.parseInt(StrSetTotal);
            tvScoreSetTotalJ2.setText(String.valueOf(intSetTotal + 1));
        }
        tvChallengeJ1.setText(String.valueOf(0)); //A la fin de chaque set remise à 0 des challenges
        tvChallengeJ2.setText(String.valueOf(0)); //A la fin de chaque set remise à 0 des challenges
        buttonChallengeJ1.setEnabled(true);
        buttonChallengeJ2.setEnabled(true);
        gameOver();
    }

    public void decremementationSetTotal(Button buttonDown){ //Décrémente le nombre de set gagnés du joueur
        String StrSetTotal;
        int intSetTotal;
        if (buttonDown == buttonDownJ1){
            StrSetTotal=tvScoreSetTotalJ1.getText().toString();
            intSetTotal = Integer.parseInt(StrSetTotal);
            if (intSetTotal > 0) {
                tvScoreSetTotalJ1.setText(String.valueOf(intSetTotal - 1));
            }
        }else if (buttonDown == buttonDownJ2){
            StrSetTotal=tvScoreSetTotalJ2.getText().toString();
            intSetTotal = Integer.parseInt(StrSetTotal);
            if (intSetTotal > 0) {
                tvScoreSetTotalJ2.setText(String.valueOf(intSetTotal - 1));
            }
        }
    }

    public void transformTieBreak(TextView tvScore, TextView tvScoreAdv) {
        tvScore.setText("0");
        tvScoreAdv.setText("0");
        tvTieBreak.setVisibility(View.VISIBLE);
        tieBreak = true;
        previousTieBreak = false;
    }

    public void onClickButtonScoreUpTieBreak(TextView tvScore, TextView tvScoreAdv, TextView tvScoreSet) {
        String valStr=tvScore.getText().toString();
        String valStrAdv=tvScoreAdv.getText().toString();
        int intVal = Integer.parseInt(valStr);
        int intValAdv = Integer.parseInt(valStrAdv);

        if (intVal < 6){
            if (intVal == 0 && intValAdv == 0){
                countNbService = 0;
                serviceChange();
            }else {
                serviceChangeTieBreak();
                countNbService++;
            }
            tvScore.setText(String.valueOf(intVal + 1));
        }else if (intVal > 5 && intValAdv > 5){
            if (intVal == intValAdv + 1){ //Si on a une différence de 2 points
                tvScoreSet.setText(String.valueOf(7)); //Incrémente le set à 7 points
                tvPreviousScoreJ1 = tvScoreJ1.getText().toString(); //Garde en mémoire le score pécédent
                tvPreviousScoreJ2 = tvScoreJ2.getText().toString(); //Garde en mémoire le score pécédent
                tvScore.setText(String.valueOf(intVal + 1));
                tvScoreJ1.setText(R.string.startGame);
                tvScoreJ2.setText(R.string.startGame);
                tvTieBreak.setVisibility(View.INVISIBLE);
                tieBreak = false;
                previousTieBreak = true;
                tvScoreSet.setTypeface(null, Typeface.BOLD);
                incremementationSetTotal(tvScore);
                numSet++;
                countNbService++;
                if (firstServiceTieBreak.equals("J1")){
                    ballServiceJ1.setVisibility(View.VISIBLE);
                    serviceChange();
                }else if (firstServiceTieBreak.equals("J2")){
                    ballServiceJ2.setVisibility(View.VISIBLE);
                    serviceChange();
                }
            }else {
                tvScore.setText(String.valueOf(intVal + 1));
                serviceChangeTieBreak();
            }
        }else {
            tvScoreSet.setText(String.valueOf(7)); //Incrémente le set à 7 points
            tvPreviousScoreJ1 = tvScoreJ1.getText().toString(); //Garde en mémoire le score pécédent
            tvPreviousScoreJ2 = tvScoreJ2.getText().toString(); //Garde en mémoire le score pécédent
            tvScore.setText(String.valueOf(intVal + 1));
            tvScoreJ1.setText(R.string.startGame);
            tvScoreJ2.setText(R.string.startGame);
            tvTieBreak.setVisibility(View.INVISIBLE);
            tieBreak = false;
            previousTieBreak = true;
            tvScoreSet.setTypeface(null, Typeface.BOLD);
            incremementationSetTotal(tvScore);
            numSet++;
            countNbService++;
            serviceChangeTieBreak();
        }
        if (twoSetWin){
            gameOver();
        }
    }

    public TextView verifSetFinish(TextView tvScoreSet1, TextView tvScoreSet2, TextView tvScoreSet3, TextView tvScoreSet4, TextView tvScoreSet5){
        TextView tvScoreSet = tvScoreSet1;

        if (numSet == 1){
            tvScoreSet = tvScoreSet1;
        }else if (numSet == 2){
            tvScoreSet = tvScoreSet2;
        }else if (numSet == 3){
            tvScoreSet = tvScoreSet3;
        }else if (numSet == 4){
            tvScoreSet = tvScoreSet4;
        }else if (numSet == 5){
            tvScoreSet = tvScoreSet5;
        }
        return tvScoreSet;
    }

    public void toast(View view){ //Fais apparaitre une po-pup a chaque ace/out/filet inscrit
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.pop_ace,
                (ViewGroup) findViewById(R.id.custom_toast_container));

        TextView text = (TextView) layout.findViewById(R.id.textViewToastAce);
        if (view == buttonAceJ1 || view == buttonAceJ2){
            text.setText(R.string.toastAce);
        }else if (view == buttonOutJ1 || view == buttonOutJ2){
            text.setText(R.string.toastOut);
        }else if (view == buttonNetJ1 || view == buttonNetJ2){
            text.setText(R.string.toastNet);
            text.setTextSize(40);
            text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }else if (view == button2emeServiceJ1 || view == button2emeServiceJ2){
            text.setText(R.string.toast2ndService);
            text.setTextSize(40);
            text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }else if (view == buttonLetJ1 || view == buttonLetJ2) {
            text.setText(R.string.toastNet);
        }

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 60);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show(); //Notification sur la vue attestant bien que le Ace a été pris en compte
    }

    public void serviceChange(){ //Vérification pour changement de service et initialisation du service au joueur n'ayant pas servit au jeu precedent le tie-break
        if (ballServiceJ1.getVisibility() == View.VISIBLE){
            firstServiceTieBreak = "J2";
            ballServiceJ1.setVisibility(View.INVISIBLE);
            ballServiceJ2.setVisibility(View.VISIBLE);
            button2emeServiceJ1.setVisibility(View.INVISIBLE);
            button2emeServiceJ2.setVisibility(View.VISIBLE);
            buttonLetJ1.setVisibility(View.INVISIBLE);
            buttonLetJ2.setVisibility(View.VISIBLE);
        }else if (ballServiceJ1.getVisibility() == View.INVISIBLE){
            firstServiceTieBreak = "J1";
            ballServiceJ1.setVisibility(View.VISIBLE);
            ballServiceJ2.setVisibility(View.INVISIBLE);
            button2emeServiceJ1.setVisibility(View.VISIBLE);
            button2emeServiceJ2.setVisibility(View.INVISIBLE);
            buttonLetJ1.setVisibility(View.VISIBLE);
            buttonLetJ2.setVisibility(View.INVISIBLE);
        }
    }

    public void serviceChangeTieBreak(){ //Vérification pour changement de service
        if (countNbService % 2 != 0){ //Chaque 2 jeux, l'image et les boutons du service du joueur actuel disparait au profit du joueur adverse
            if (ballServiceJ1.getVisibility() == View.VISIBLE){
                ballServiceJ1.setVisibility(View.INVISIBLE);
                ballServiceJ2.setVisibility(View.VISIBLE);
                button2emeServiceJ1.setVisibility(View.INVISIBLE);
                button2emeServiceJ2.setVisibility(View.VISIBLE);
                buttonLetJ1.setVisibility(View.INVISIBLE);
                buttonLetJ2.setVisibility(View.VISIBLE);
            }else if (ballServiceJ1.getVisibility() == View.INVISIBLE){
                ballServiceJ1.setVisibility(View.VISIBLE);
                ballServiceJ2.setVisibility(View.INVISIBLE);
                button2emeServiceJ1.setVisibility(View.VISIBLE);
                button2emeServiceJ2.setVisibility(View.INVISIBLE);
                buttonLetJ1.setVisibility(View.VISIBLE);
                buttonLetJ2.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void serviceChangeDownTieBreak() { //Vérification pour changement de service
        if (countNbService % 2 != 0) {
            if (ballServiceJ1.getVisibility() == View.VISIBLE) { //Si on revient au point d'avant le changement de service ne compte pas
                ballServiceJ1.setVisibility(View.INVISIBLE);
                ballServiceJ2.setVisibility(View.VISIBLE);
                button2emeServiceJ1.setVisibility(View.INVISIBLE);
                button2emeServiceJ2.setVisibility(View.VISIBLE);
                buttonLetJ1.setVisibility(View.INVISIBLE);
                buttonLetJ2.setVisibility(View.VISIBLE);
            } else if (ballServiceJ1.getVisibility() == View.INVISIBLE) {
                ballServiceJ1.setVisibility(View.VISIBLE);
                ballServiceJ2.setVisibility(View.INVISIBLE);
                button2emeServiceJ1.setVisibility(View.VISIBLE);
                button2emeServiceJ2.setVisibility(View.INVISIBLE);
                buttonLetJ1.setVisibility(View.VISIBLE);
                buttonLetJ2.setVisibility(View.INVISIBLE);
            }
        }else if (countNbService % 2 == 0) {
            if (ballServiceJ1.getVisibility() == View.VISIBLE) { //Si on revient au point d'avant le changement de service ne compte pas
                ballServiceJ1.setVisibility(View.INVISIBLE);
                ballServiceJ2.setVisibility(View.VISIBLE);
                button2emeServiceJ1.setVisibility(View.INVISIBLE);
                button2emeServiceJ2.setVisibility(View.VISIBLE);
                buttonLetJ1.setVisibility(View.INVISIBLE);
                buttonLetJ2.setVisibility(View.VISIBLE);
            } else if (ballServiceJ1.getVisibility() == View.INVISIBLE) {
                ballServiceJ1.setVisibility(View.VISIBLE);
                ballServiceJ2.setVisibility(View.INVISIBLE);
                button2emeServiceJ1.setVisibility(View.VISIBLE);
                button2emeServiceJ2.setVisibility(View.INVISIBLE);
                buttonLetJ1.setVisibility(View.VISIBLE);
                buttonLetJ2.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void playerNationality(){ //Placement des joueurs et récupération du drapeau associé à l'id du joueur en bdd
        tvJ1.setText(ServiceActivity.sharedpreferencesService.getString(ServiceActivity.Player1, null));
        tvJ2.setText(ServiceActivity.sharedpreferencesService.getString(ServiceActivity.Player2, null));
        //Méthodes get récupérant le drapeau en fonction du nom des joueurs
        nationalityJ1.setImageResource(R.mipmap.france);
        nationalityJ2.setImageResource(R.mipmap.italy);
        //nationalityJ1.setImageDrawable(drapeau récupéré de la bdd);
        //nationalityJ2.setImageDrawable(drapeau récupéré de la bdd);
    }

    public void onClickButtonScoreDown(TextView tvScore, TextView tvScoreAdv, Button buttonDown, Button buttonCancel, TextView tvChallenge, TextView tvChallengeAdv, Button buttonChallenge, Button buttonChallengeAdv, String tvPreviousScore, String tvPreviousScoreAdv, TextView tvScoreSet){ //Decrementation du score adverse suite à une demande de challenge de la part d'un joueur s'il a raison
        String tvScoreStr = tvScore.getText().toString();
        String tvChallengeStr = tvChallenge.getText().toString();
        String tvChallengeAdvStr = tvChallengeAdv.getText().toString();
        String tvScoreSetStr = tvScoreSet.getText().toString();
        int intChallenge = Integer.parseInt(tvChallengeStr);
        int intChallengeAdv = Integer.parseInt(tvChallengeAdvStr);
        int intScoreSet = Integer.parseInt(tvScoreSetStr);
        if (tvScoreStr.equals("00") && !previousTieBreak){
            tvScore.setText(String.valueOf(tvPreviousScore));
            tvScoreAdv.setText(String.valueOf(tvPreviousScoreAdv));
            decremementationSetTotal(buttonDown);
            if ((numSet > 1 && intScoreSet == 0)) { //Permet de revenir au set précédent pour le décrémenter après qu'un jeu ait été gagné sans tie-break
                numSet -= 1;
            }
            if (buttonDown == buttonDownJ1){
                tvScoreSet = verifSetFinish(tvSet1J1, tvSet2J1, tvSet3J1, tvSet4J1, tvSet5J1);
            }else if (buttonDown == buttonDownJ2){
                tvScoreSet = verifSetFinish(tvSet1J2, tvSet2J2, tvSet3J2, tvSet4J2, tvSet5J2);
            }
            tvScoreSetStr = tvScoreSet.getText().toString();
            intScoreSet = Integer.parseInt(tvScoreSetStr);
            tvScoreSet.setText(String.valueOf(intScoreSet - 1)); //Décrémente le set
            countNbService--;
            serviceChangeDownTieBreak();
        }else if (tvScoreStr.equals("00") && previousTieBreak) {
            tvScore.setText(String.valueOf(tvPreviousScore));
            tvScoreAdv.setText(String.valueOf(tvPreviousScoreAdv));
            if (buttonDown == buttonDownJ1){
                decremementationSetTotal(buttonDown);
                numSet -= 1;
                tvScoreSet = verifSetFinish(tvSet1J1, tvSet2J1, tvSet3J1, tvSet4J1, tvSet5J1);
                tvScoreSetStr = tvScoreSet.getText().toString();
                intScoreSet = Integer.parseInt(tvScoreSetStr);
                tvScoreSet.setText(String.valueOf(intScoreSet - 1)); //Décrémente le set
            }else if (buttonDown == buttonDownJ2){
                decremementationSetTotal(buttonDown);
                numSet -= 1;
                tvScoreSet = verifSetFinish(tvSet1J2, tvSet2J2, tvSet3J2, tvSet4J2, tvSet5J2);
                tvScoreSetStr = tvScoreSet.getText().toString();
                intScoreSet = Integer.parseInt(tvScoreSetStr);
                tvScoreSet.setText(String.valueOf(intScoreSet - 1)); //Décrémente le set
            }
            countNbService--;
            serviceChangeDownTieBreak();
            tieBreak = true;
            previousTieBreak = false;
            tvTieBreak.setVisibility(View.VISIBLE);
        }else if (tvScoreStr.equals("15")){
            tvScore.setText(String.valueOf("00"));
        }else if (tvScoreStr.equals("30")){
            tvScore.setText(String.valueOf("15"));
        }else if (tvScoreStr.equals("40")){
            tvScore.setText(String.valueOf("30"));
        }else if (tvScoreStr.equals("AV")){
            tvScore.setText(String.valueOf("40"));
        }else {
            int intScore = Integer.parseInt(tvScoreStr);
            if (tvScoreStr.equals("0")){
                tvScore.setText(String.valueOf(tvPreviousScore));
                tvScoreAdv.setText(String.valueOf(tvPreviousScoreAdv));
                if (buttonDown == buttonDownJ1){
                    tvScoreSet = verifSetFinish(tvSet1J1, tvSet2J1, tvSet3J1, tvSet4J1, tvSet5J1);
                }else if (buttonDown == buttonDownJ2){
                    tvScoreSet = verifSetFinish(tvSet1J2, tvSet2J2, tvSet3J2, tvSet4J2, tvSet5J2);
                }
                tvScoreSetStr = tvScoreSet.getText().toString();
                intScoreSet = Integer.parseInt(tvScoreSetStr);
                tvScoreSet.setText(String.valueOf(intScoreSet - 1)); //Décrémente le set
                countNbService--;
                serviceChangeDownTieBreak();
                tieBreak = false;
                previousTieBreak = true;
                tvTieBreak.setVisibility(View.INVISIBLE);
            }else {
                tvScore.setText(String.valueOf(intScore - 1)); //Décrémente le score du tie break
            }
        }
        interactionButtonTrue();
        if (intChallenge < 3){
            buttonChallenge.setEnabled(true);
        }
        if (intChallengeAdv < 3){
            buttonChallengeAdv.setEnabled(true);
        }
        buttonDown.setVisibility(View.INVISIBLE);
        buttonCancel.setVisibility(View.INVISIBLE);
    }

    public void onClickButtonScoreCancelDown(Button buttonCancel, Button buttonDown, TextView tvChallenge, TextView tvChallengeAdv, Button buttonChallenge, Button buttonChallengeAdv){
        String tvChallengeStr = tvChallenge.getText().toString();
        String tvChallengeAdvStr = tvChallengeAdv.getText().toString();
        int intChallenge = Integer.parseInt(tvChallengeStr);
        int intChallengeAdv = Integer.parseInt(tvChallengeAdvStr);
        interactionButtonTrue();
        if (intChallenge < 3){
            buttonChallenge.setEnabled(true);
        }
        if (intChallengeAdv < 3){
            buttonChallengeAdv.setEnabled(true);
        }
        buttonCancel.setVisibility(View.INVISIBLE);
        buttonDown.setVisibility(View.INVISIBLE);
    }

    private void breaker(TextView tvScore){
        if (tvScore == tvScoreJ1 && ballServiceJ2.isShown()){
            isBreak = true;
            //Incrémentation du break dans la table Joueur de la base de données associé à l'Id du joueur correspondant
        }else if (tvScore == tvScoreJ2 && ballServiceJ1.isShown() && isBreak){
            //Incrémentation du debreak dans la table Joueur de la base de données associé à l'Id du joueur correspondant
            isBreak = false;
        }
        if (tvScore == tvScoreJ2 && ballServiceJ1.isShown()){
            isBreak = true;
            //Incrémentation du break dans la table Joueur de la base de données associé à l'Id du joueur correspondant
        }else if (tvScore == tvScoreJ1 && ballServiceJ2.isShown() && isBreak){
            //Incrémentation du debreak dans la table Joueur de la base de données associé à l'Id du joueur correspondant
            isBreak = false;
        }
    }

    private boolean rulesLastSetTournament(){ //En fonction du tournoi, si Grand Chelem pas de tie break dans le dernier set
        if (Tournament.getTournamentByName(tournament).grandChelem()){ //Si tournoi du Grand Chelem
            if (tournament.equals(Tournament.US_OPEN.toString())) {
                finalTieBreak = true;
            }else{
                finalTieBreak = false;
            }
        }else { //Tournoi en dehors du Grand Chelem
            if (tournament.equals(Tournament.DAVIS_CUP.toString())) { //Coupe Davis n'existe qu'en simple messieurs et double messieurs, et toujours tie break final au cinquieme set
                finalTieBreak = true;
            }else {
                finalTieBreak = false;
            }
        }
        return finalTieBreak;
    }

    private boolean rulesSetWinTournament(){ //En fonction du tournoi, si Grand Chelem 3 set gagnants, sinon 2
        if (Tournament.getTournamentByName(tournament).grandChelem()){ //Si tournoi du Grand Chelem
            if (category.equals(Category.SIMPLE_MESSIEURS.toString()) || (category.equals(Category.DOUBLE_MESSIEURS.toString()) && tournament.equals(Tournament.WIMBELDON.toString()))) {  //Si Simple messieurs ou (double messieurs et Wimbledon)
                twoSetWin = false; //3 set gagnants (5 set max) renvoi false
            }else{
                twoSetWin = true; //2 set gagnants (3 set max) renvoi true
            }
        }else { //Tournoi en dehors du Grand Chelem
            if (tournament.equals(Tournament.DAVIS_CUP.toString()) && (category.equals(Category.SIMPLE_MESSIEURS.toString()) || category.equals(Category.DOUBLE_MESSIEURS.toString()))) {
                twoSetWin = false; //3 set gagnants (5 set max) renvoi false
            }else {
                twoSetWin = true; // 2 set gagnants (3 set max) renvoi true
            }
        }
        return twoSetWin;
    }

    private boolean rulesSuperTieBreak(){
        if ((Tournament.getTournamentByName(tournament).grandChelem() && category.equals(Category.DOUBLE_MIXTE.toString())) || (!Tournament.getTournamentByName(tournament).grandChelem() && Category.getTypeByName(category).getType() == 2)){ //Super tie break dans le dernier set pour double mixte en Grand Chelem ou tous les doubles hors Grand Chelem
            if (tournament.equals(Tournament.DAVIS_CUP.toString()) && category.equals(Category.DOUBLE_MESSIEURS.toString())) { //sauf la Coupe Davis pour le double messieurs
                superTieBreak = false;
            }else {
                superTieBreak = true;
            }
        }
        return superTieBreak;
    }

    public void superTieBreak(TextView tvScore, TextView tvScoreAdv, TextView tvScoreSet) {
        String valStr=tvScore.getText().toString();
        String valStrAdv=tvScoreAdv.getText().toString();
        String valStrSet=tvScoreSet.getText().toString();
        int intVal = Integer.parseInt(valStr);
        int intValAdv = Integer.parseInt(valStrAdv);
        int intValSet = Integer.parseInt(valStrSet);

        if (intVal < 9){
            if (intVal == 0 && intValAdv == 0){
                countNbService = 0;
                serviceChange();
                tvSet3J1.setTextColor(-16777216); //Met le texte en noir
                tvSet3J2.setTextColor(-16777216); //Met le texte en noir
            }else {
                serviceChangeTieBreak();
                countNbService++;
            }
            tvScore.setText(String.valueOf(intVal + 1));
            tvScoreSet.setText(String.valueOf(intValSet + 1));
        }else if (intVal > 8 && intValAdv > 8){
            if (intVal == intValAdv + 1){ //Si on a une différence de 2 points
                tvScoreSet.setText(String.valueOf(intValSet + 1));
                tvPreviousScoreJ1 = tvScoreJ1.getText().toString(); //Garde en mémoire le score pécédent
                tvPreviousScoreJ2 = tvScoreJ2.getText().toString(); //Garde en mémoire le score pécédent
                tvScore.setText(String.valueOf(intVal + 1));
                tvTieBreak.setVisibility(View.INVISIBLE);
                superTieBreak = false;
                tvScoreSet.setTypeface(null, Typeface.BOLD);
                incremementationSetTotal(tvScore);
            }else {
                tvScoreSet.setText(String.valueOf(intValSet + 1));
                tvScore.setText(String.valueOf(intVal + 1));
                serviceChangeTieBreak();
            }
        }else {
            tvScoreSet.setText(String.valueOf(intValSet + 1));
            tvPreviousScoreJ1 = tvScoreJ1.getText().toString(); //Garde en mémoire le score pécédent
            tvPreviousScoreJ2 = tvScoreJ2.getText().toString(); //Garde en mémoire le score pécédent
            tvScore.setText(String.valueOf(intVal + 1));
            tvTieBreak.setVisibility(View.INVISIBLE);
            superTieBreak = false;
            tvScoreSet.setTypeface(null, Typeface.BOLD);
            incremementationSetTotal(tvScore);
        }
    }

    public void transformSuperTieBreak(TextView tvScore, TextView tvScoreAdv) {
        tvScore.setText("0");
        tvScoreAdv.setText("0");
        tvTieBreak.setVisibility(View.VISIBLE);
        tvTieBreak.setText(R.string.fantasticTieBreak);
        tvTieBreak.setTextSize(37); //Taille du texte du super tie-break
        superTieBreak = true;
        previousTieBreak = false;
    }

    private void gameOver(){
        if (twoSetWin && (tvScoreSetTotalJ1.getText().equals("2") || tvScoreSetTotalJ2.getText().equals("2"))){ //Si set en 2 manches gagnantes le match s'arrete si un joueur gagne les 2 premieres manches
            tvScoreJ1.setText("--");
            tvScoreJ2.setText("--");
            interactionButtonFalse();
            endMatch();
        }else if (!twoSetWin && (tvScoreSetTotalJ1.getText().equals("3") || tvScoreSetTotalJ2.getText().equals("3"))){ //Si set en 3 manches gagnantes le match s'arrete si un joueur gagne 3 manches
            tvScoreJ1.setText("--");
            tvScoreJ2.setText("--");
            interactionButtonFalse();
            endMatch();
        }
    }

    public void endMatch() { //Récupérarion des joueurs gagnant et perdant pour l'activity de fin de match
        SharedPreferences.Editor editor = sharedpreferencesMainActivity.edit();
        String strJ1=tvJ1.getText().toString();
        String strJ2=tvJ2.getText().toString();
        String strSetTotalJ1=tvScoreSetTotalJ1.getText().toString();
        int intSetTotalJ1 = Integer.parseInt(strSetTotalJ1);
        String strSetTotalJ2=tvScoreSetTotalJ2.getText().toString();
        int intSetTotalJ2 = Integer.parseInt(strSetTotalJ2);

        if (intSetTotalJ1 > intSetTotalJ2){
            editor.putString(PlayerWin, strJ1);
            editor.putString(ScoreWin, strSetTotalJ1);
            editor.putString(ScoreLost, strSetTotalJ2);
        }else {
            editor.putString(PlayerWin, strJ2);
            editor.putString(ScoreWin, strSetTotalJ2);
            editor.putString(ScoreLost, strSetTotalJ1);
        }
        editor.commit();

        Intent intent = new Intent(MainActivity.this, EndMatchActivity.class);
        startActivity(intent);
    }

    public void incrementationGameSanction() {
        String sanction = sharedpreferencesSanction.getString(SanctionActivity.SanctionGame, null);
        String playerSanction = sharedpreferencesSanction.getString(SanctionActivity.PlayerSanction, null);
        String resumeSanction = sharedpreferencesSanction.getString(SanctionActivity.Resume, null);
        String idSanction = sharedpreferencesSanction.getString(SanctionActivity.IdSanction, null);
        if (!resumeSanction.equals("true") && idSanction.equals("sanction2")) {
            if (sanction.equals("true") && playerSanction.equals(tvJ1.getText().toString())) {
                onClickButtonIncrementationSet(tvScoreJ2, tvScoreJ1, tvSet1J2, tvSet2J2, tvSet3J2, tvSet4J2, tvSet5J2, tvSet1J1, tvSet2J1, tvSet3J1, tvSet4J1, tvSet5J1);
                //Incrementer la sanction jeu dans la BDD pour le playerSanction
                tvScoreJ1.setText(R.string.startGame);
                tvScoreJ2.setText(R.string.startGame);
            } else if (sanction.equals("true") && playerSanction.equals(tvJ2.getText().toString())) {
                onClickButtonIncrementationSet(tvScoreJ1, tvScoreJ2, tvSet1J1, tvSet2J1, tvSet3J1, tvSet4J1, tvSet5J1, tvSet1J2, tvSet2J2, tvSet3J2, tvSet4J2, tvSet5J2);
                //Incrementer la sanction jeu dans la BDD pour le playerSanction
                tvScoreJ1.setText(R.string.startGame);
                tvScoreJ2.setText(R.string.startGame);
            }

            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.pop_ace,
                    (ViewGroup) findViewById(R.id.custom_toast_container));

            TextView text = (TextView) layout.findViewById(R.id.textViewToastAce);
            CharSequence textSanction = "LA SANCTION A ETE APPLIQUEE A : " + playerSanction;
            text.setText(textSanction);
            text.setBackgroundResource(R.drawable.flat_rectangle_red);
            text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            text.setTextSize(40);

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.BOTTOM, 0, 60);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show(); //Notification sur la vue attestant bien que le Ace a été pris en compte
        }
    }
}
