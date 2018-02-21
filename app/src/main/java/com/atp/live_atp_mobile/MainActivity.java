package com.atp.live_atp_mobile;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static int[] tabPoint = {0, 15, 30, 40, -1};

    private ImageButton buttonStart;
    private Chronometer timer;
    private ImageButton buttonJ1;
    private ImageButton buttonJ2;
    private TextView tvScoreJ1;
    private TextView tvScoreJ2;
    private Button buttonChallengeJ1;
    private Button buttonChallengeJ2;
    private TextView tvChallengeJ1;
    private TextView tvChallengeJ2;
    private Button buttonAceJ1;
    private Button buttonAceJ2;
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
    private boolean tieBreakFalse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialisation des éléments
        //Chronomètre
        this.buttonStart = (ImageButton) findViewById(R.id.imageButtonStart);
        this.timer = (Chronometer) findViewById(R.id.chronometerMain);

        //Score
        this.buttonJ1 = (ImageButton) findViewById(R.id.imageButtonModifPointJ1);
        this.buttonJ2 = (ImageButton) findViewById(R.id.imageButtonModifPointJ2);
        this.tvScoreJ1= (TextView) findViewById(R.id.textScoreJ1);
        this.tvScoreJ2= (TextView) findViewById(R.id.textScoreJ2);

        //Challenge
        this.buttonChallengeJ1 = (Button) findViewById(R.id.buttonChallengeJ1);
        this.buttonChallengeJ2 = (Button) findViewById(R.id.buttonChallengeJ2);
        this.tvChallengeJ1= (TextView) findViewById(R.id.textViewNbChallengeJ1);
        this.tvChallengeJ2= (TextView) findViewById(R.id.textViewNbChallengeJ2);

        //Ace
        this.buttonAceJ1 = (Button) findViewById(R.id.buttonAceJ1);
        this.buttonAceJ2 = (Button) findViewById(R.id.buttonAceJ2);

        //Jeu
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

        //Tie-Break
        this.tieBreakFalse = false;

        //Interaction impossible sur les boutons
        buttonJ1.setEnabled(false);
        buttonJ2.setEnabled(false);
        buttonChallengeJ1.setEnabled(false);
        buttonChallengeJ2.setEnabled(false);
        buttonAceJ1.setEnabled(false);
        buttonAceJ2.setEnabled(false);

        buttonStart.setOnClickListener(this);
        buttonJ1.setOnClickListener(this);
        buttonJ2.setOnClickListener(this);
        buttonAceJ1.setOnClickListener(this);
        buttonAceJ2.setOnClickListener(this);
        buttonChallengeJ1.setOnClickListener(this);
        buttonChallengeJ2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //Démarrage du match
        if (view == buttonStart){
            startChronometer();
        }
        //Score
        if (view == buttonJ1){
            if (!tieBreakFalse){
                onClickButtonScoreUp(tvScoreJ1, tvScoreJ2, tvSet1J1, tvSet2J1, tvSet3J1, tvSet4J1, tvSet5J1, tvSet1J2, tvSet2J2, tvSet3J2, tvSet4J2, tvSet5J2);
            }else {
                onClickButtonScoreUpTieBreak(tvScoreJ1, tvScoreJ2, verifSetFinish(tvSet1J1, tvSet2J1, tvSet3J1, tvSet4J1, tvSet5J1));
            }
        }
        if (view == buttonJ2){
            if (!tieBreakFalse){
                onClickButtonScoreUp(tvScoreJ2, tvScoreJ1, tvSet1J2, tvSet2J2, tvSet3J2, tvSet4J2, tvSet5J2, tvSet1J1, tvSet2J1, tvSet3J1, tvSet4J1, tvSet5J1);
            }else {
                onClickButtonScoreUpTieBreak(tvScoreJ2, tvScoreJ1, verifSetFinish(tvSet1J2, tvSet2J2, tvSet3J2, tvSet4J2, tvSet5J2));
            }
        }
        if (view == buttonAceJ1){
            onClickButtonAce(tvScoreJ1, tvScoreJ2, tvSet1J1, tvSet2J1, tvSet3J1, tvSet4J1, tvSet5J1, tvSet1J2, tvSet2J2, tvSet3J2, tvSet4J2, tvSet5J2);
        }
        if (view == buttonAceJ2){
            onClickButtonAce(tvScoreJ2, tvScoreJ1, tvSet1J2, tvSet2J2, tvSet3J2, tvSet4J2, tvSet5J2, tvSet1J1, tvSet2J1, tvSet3J1, tvSet4J1, tvSet5J1);
        }
        //Challenge
        if (view == buttonChallengeJ1){
            onClickButtonIncrementationChallenge(buttonChallengeJ1, tvChallengeJ1);
        }
        if (view == buttonChallengeJ2){
            onClickButtonIncrementationChallenge(buttonChallengeJ2, tvChallengeJ2);
        }
    }

    public void startChronometer(){
        buttonJ1.setEnabled(true);
        buttonJ2.setEnabled(true);
        buttonChallengeJ1.setEnabled(true);
        buttonChallengeJ2.setEnabled(true);
        buttonAceJ1.setEnabled(true);
        buttonAceJ2.setEnabled(true);

        timer.setBase(SystemClock.elapsedRealtime()); //Intialisation du chronomètre
        timer.start(); //Démarre le chronomètre
        buttonStart.setVisibility(View.INVISIBLE); //Fais disparaitre le bouton start
    }

    public void onClickButtonScoreUp(TextView tvScore, TextView tvScoreAdv, TextView tvScoreSet1, TextView tvScoreSet2, TextView tvScoreSet3, TextView tvScoreSet4, TextView tvScoreSet5, TextView tvScoreSet1Adv, TextView tvScoreSet2Adv, TextView tvScoreSet3Adv, TextView tvScoreSet4Adv, TextView tvScoreSet5Adv){
        int presentIntVal;
        int presentIntValAdv;
        String presentValStr=tvScore.getText().toString();
        String presentValStrAdv=tvScoreAdv.getText().toString();
        if (presentValStr != "AV") {
            presentIntVal = Integer.parseInt(presentValStr);
            if (presentValStrAdv == "AV"){ //Condition permettant d'éviter que le string parsé en int juste à la sortie du if ne se fasse pas tel quel. AV en int n'est pas possible
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
            }else if (tabPoint[pos] == presentIntVal && pos == 3){
                if (presentIntValAdv == -1){ //Le joueur met un point alors que l'adversaire avait un avantage -> remise à 40 pour les 2
                    tvScore.setText(String.valueOf(presentIntVal)); //Remise à 40 pour le joueur
                    tvScoreAdv.setText(String.valueOf(presentIntVal)); //Remise à 40 pour l'adversaire en utilisant la valeur du joueur
                }else if(presentIntValAdv == 40){ //Le joueur met un point alors qu'ils sont à égalité -> avantage pour lui
                    tvScore.setText("AV"); //Avantage pour le joueur
                    //Incrémenter dans la bdd la statistique avantage pour le joueur
                }else{ //Le joueur adverse n'a ni 40 ni avantage (0 ou 15 ou 30) donc le joueur gagne le point
                    tvScore.setText("00");
                    tvScoreAdv.setText("00");
                    onClickButtonIncrementationSet(tvScore, tvScoreAdv, tvScoreSet1, tvScoreSet2, tvScoreSet3, tvScoreSet4, tvScoreSet5, tvScoreSet1Adv, tvScoreSet2Adv, tvScoreSet3Adv, tvScoreSet4Adv, tvScoreSet5Adv); //Incrémentation du nombre de jeu du set correspondant car le jeu est gagné
                }
            }else if (tabPoint[pos] == presentIntVal && pos == 4){
                tvScore.setText("00");
                tvScoreAdv.setText("00");
                onClickButtonIncrementationSet(tvScore, tvScoreAdv, tvScoreSet1, tvScoreSet2, tvScoreSet3, tvScoreSet4, tvScoreSet5, tvScoreSet1Adv, tvScoreSet2Adv, tvScoreSet3Adv, tvScoreSet4Adv, tvScoreSet5Adv); //Incrémentation du nombre de jeu du set correspondant car le jeu est gagné
            }
        }
    }

    public void onClickButtonAce(TextView tvScore, TextView tvScoreAdv, TextView tvScoreSet1, TextView tvScoreSet2, TextView tvScoreSet3, TextView tvScoreSet4, TextView tvScoreSet5, TextView tvScoreSet1Adv, TextView tvScoreSet2Adv, TextView tvScoreSet3Adv, TextView tvScoreSet4Adv, TextView tvScoreSet5Adv){ //Clique sur Ace pour ajouter des points sur le service sans touche de la part de l'adversaire
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.pop_ace,
                (ViewGroup) findViewById(R.id.custom_toast_container));

        TextView text = (TextView) layout.findViewById(R.id.textViewComptabilise);
        text.setText("LE ACE A ETE COMPTABILISE");

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 60);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);

        onClickButtonScoreUp(tvScore, tvScoreAdv, tvScoreSet1, tvScoreSet2, tvScoreSet3, tvScoreSet4, tvScoreSet5, tvScoreSet1Adv, tvScoreSet2Adv, tvScoreSet3Adv, tvScoreSet4Adv, tvScoreSet5Adv);
        //Incrémentation des Ace dans la table Statistique de la base de données associé à l'Id du joueur correspondant
        toast.show(); //Notification sur la vue attestant bien que le Ace a été pris en compte
    }

    public void onClickButtonIncrementationChallenge(Button button, TextView tvChallenge){ //Compte le nombre de challenge effectué par joueur. 3 challenge max par joueur
        int presentIntVal;
        String presentValStr=tvChallenge.getText().toString();
        presentIntVal = Integer.parseInt(presentValStr);
        if (presentIntVal < 3) {
            tvChallenge.setText(String.valueOf(presentIntVal + 1)); //Incrémente le nombre de challenge (replay demandé par un joueur pour valider ou non le point)
            if ((presentIntVal == 2)){ //A partir du 3eme click le bouton se grise pour éviter de compter des challenge en trop
                button.setEnabled(false); //Bouton devient incliquable
            }
        }
    }

    public void onClickButtonIncrementationSet(TextView tvScore, TextView tvScoreAdv, TextView tvScoreSet1, TextView tvScoreSet2, TextView tvScoreSet3, TextView tvScoreSet4, TextView tvScoreSet5, TextView tvScoreSet1Adv, TextView tvScoreSet2Adv, TextView tvScoreSet3Adv, TextView tvScoreSet4Adv, TextView tvScoreSet5Adv){ //Compte le nombre de jeu gagné par joueur
        String valStrSet1=tvScoreSet1.getText().toString();
        String valStrSet2=tvScoreSet2.getText().toString();
        String valStrSet3=tvScoreSet3.getText().toString();
        String valStrSet4=tvScoreSet4.getText().toString();
        String valStrSet5=tvScoreSet5.getText().toString();
        String valStrSet1Adv=tvScoreSet1Adv.getText().toString();
        String valStrSet2Adv=tvScoreSet2Adv.getText().toString();
        String valStrSet3Adv=tvScoreSet3Adv.getText().toString();
        String valStrSet4Adv=tvScoreSet4Adv.getText().toString();
        String valStrSet5Adv=tvScoreSet5Adv.getText().toString();
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

        if (intValSet1 < 6) {
            tvScoreSet1.setText(String.valueOf(intValSet1 + 1)); //Incrémente le set 1
            intValSet1 += 1;
            if (intValSet1 == 6 && intValSet1Adv == 6){
                transformTieBreak(tvScore, tvScoreAdv);
            }
        }else if (intValSet2 < 6){
            tvScoreSet2.setText(String.valueOf(intValSet2 + 1)); //Incrémente le set 2
            intValSet2 += 1;
            if (intValSet2 == 6 && intValSet2Adv == 6){
                transformTieBreak(tvScore, tvScoreAdv);
            }
        }else if (intValSet3 < 6){
            tvScoreSet3.setText(String.valueOf(intValSet3 + 1)); //Incrémente le set 3
            intValSet3 += 1;
            if (intValSet3 == 6 && intValSet3Adv == 6){
                transformTieBreak(tvScore, tvScoreAdv);
            }
        }else if (intValSet4 < 6){
            tvScoreSet4.setText(String.valueOf(intValSet4 + 1)); //Incrémente le set 3
            intValSet4 += 1;
            if (intValSet4 == 6 && intValSet4Adv == 6){
                transformTieBreak(tvScore, tvScoreAdv);
            }
        }else if (intValSet5 < 6){
            tvScoreSet5.setText(String.valueOf(intValSet5 + 1)); //Incrémente le set 3
            intValSet5 += 1;
            if (intValSet5 == 6 && intValSet5Adv == 6){
                transformTieBreak(tvScore, tvScoreAdv);
            }
        }
    }

    public void transformTieBreak(TextView tvScore, TextView tvScoreAdv) {
        tvScore.setText("0");
        tvScoreAdv.setText("0");
        //idImageTieBreak.enabled(true)
        tieBreakFalse = true;
    }

    public void onClickButtonScoreUpTieBreak(TextView tvScore, TextView tvScoreAdv, TextView tvScoreSet) {
        String valStr=tvScore.getText().toString();
        String valStrAdv=tvScoreAdv.getText().toString();
        int intVal = Integer.parseInt(valStr);
        int intValAdv = Integer.parseInt(valStrAdv);

        if (intVal < 6){
            tvScore.setText(String.valueOf(intVal + 1));
        }else if (intVal > 5 && intValAdv > 5){
            if (intVal == intValAdv + 1){ //Si on a une différence de 2 points
                tvScore.setText(String.valueOf(intVal + 1));
                tvScoreSet.setText(String.valueOf(7)); //Incrémente le set à 7 points
                tvScoreJ1.setText("00");
                tvScoreJ2.setText("00");
                //idImageTieBreak.enabled(false)
                tieBreakFalse = false;
            }else {
                tvScore.setText(String.valueOf(intVal + 1));
            }
        }else {
            tvScore.setText(String.valueOf(intVal + 1));
            tvScoreSet.setText(String.valueOf(intVal + 1)); //Incrémente le set à 7 points
            tvScoreJ1.setText("00");
            tvScoreJ2.setText("00");
            //idImageTieBreak.enabled(false)
            tieBreakFalse = false;
        }
    }

    public TextView verifSetFinish(TextView tvScoreSet1, TextView tvScoreSet2, TextView tvScoreSet3, TextView tvScoreSet4, TextView tvScoreSet5){
        String valStrSet1=tvScoreSet1.getText().toString();
        String valStrSet2=tvScoreSet2.getText().toString();
        String valStrSet3=tvScoreSet3.getText().toString();
        String valStrSet4=tvScoreSet4.getText().toString();
        String valStrSet5=tvScoreSet5.getText().toString();
        int intValSet1 = Integer.parseInt(valStrSet1);
        int intValSet2 = Integer.parseInt(valStrSet2);
        int intValSet3 = Integer.parseInt(valStrSet3);
        int intValSet4 = Integer.parseInt(valStrSet4);
        int intValSet5 = Integer.parseInt(valStrSet5);
        TextView tvScoreSet = tvScoreSet1;

        if (intValSet1 < 7){
            tvScoreSet = tvScoreSet1;
        }else if ((intValSet1 == 6 || intValSet1 == 7) && intValSet2 < 7){
            tvScoreSet = tvScoreSet2;
        }else if ((intValSet2 == 6 || intValSet2== 7) && intValSet3 < 7){
            tvScoreSet = tvScoreSet3;
        }else if ((intValSet3 == 6 || intValSet3 == 7) && intValSet4 < 7){
            tvScoreSet = tvScoreSet4;
        }else if ((intValSet4 == 6 || intValSet4 == 7) && intValSet5 < 7){
            tvScoreSet = tvScoreSet5;
        }
        return tvScoreSet;
    }
}
