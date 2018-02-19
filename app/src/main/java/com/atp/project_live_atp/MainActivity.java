package com.atp.project_live_atp;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.StaticLayout;
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
    private TextView tvScore;
    private TextView tvScoreAdv;
    private Button buttonChallengeJ1;
    private Button buttonChallengeJ2;
    private TextView tvChallengeJ1;
    private TextView tvChallengeJ2;
    private Button buttonAceJ1;
    private Button buttonAceJ2;
    private TextView tvSet1J1;
    private TextView tvSet2J1;
    private TextView tvSet3J1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialisation des éléments
        //Chronomètre
        this.buttonStart = findViewById(R.id.imageButtonStart);
        this.timer = findViewById(R.id.chronometerMain);

        //Score
        this.buttonJ1 = findViewById(R.id.imageButtonModifPointJ1);
        this.buttonJ2 = findViewById(R.id.imageButtonModifPointJ2);
        this.tvScore=findViewById(R.id.textScoreJ1);
        this.tvScoreAdv=findViewById(R.id.textScoreJ2);

        //Challenge
        this.buttonChallengeJ1 = findViewById(R.id.buttonChallengeJ1);
        this.buttonChallengeJ2 = findViewById(R.id.buttonChallengeJ2);
        this.tvChallengeJ1=findViewById(R.id.textViewNbChallengeJ1);
        this.tvChallengeJ2=findViewById(R.id.textViewNbChallengeJ2);

        //Ace
        this.buttonAceJ1 = findViewById(R.id.buttonAceJ1);
        this.buttonAceJ2 = findViewById(R.id.buttonAceJ2);

        //Jeu
        this.tvSet1J1 = findViewById(R.id.textViewScore1SetJ1);
        this.tvSet2J1 = findViewById(R.id.textViewScore2SetJ1);
        this.tvSet3J1 = findViewById(R.id.textViewScore3SetJ1);

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
            startChronometer(buttonStart, timer, buttonJ1, buttonJ2, buttonChallengeJ1, buttonChallengeJ2, buttonAceJ1, buttonAceJ2);
        }
        //Score
        if (view == buttonJ1){
            onClickButtonScoreUp(tvScore, tvScoreAdv);
        }
        if (view == buttonJ2){
            onClickButtonScoreUp(tvScoreAdv, tvScore);
        }
        if (view == buttonAceJ1){
            onClickButtonAce(tvScore, tvScoreAdv);
        }
        if (view == buttonAceJ2){
            onClickButtonAce(tvScoreAdv, tvScore);
        }
        //Challenge
        if (view == buttonChallengeJ1){
            onClickButtonIncrementationChallenge(buttonChallengeJ1, tvChallengeJ1);
        }
        if (view == buttonChallengeJ2){
            onClickButtonIncrementationChallenge(buttonChallengeJ2, tvChallengeJ2);
        }
    }

    public void startChronometer(ImageButton buttonStart, Chronometer timer, ImageButton buttonJ1, ImageButton buttonJ2, Button buttonChallengeJ1, Button buttonChallengeJ2, Button buttonAceJ1, Button buttonAceJ2){
        buttonJ1.setEnabled(true);
        buttonJ2.setEnabled(true);
        buttonChallengeJ1.setEnabled(true);
        buttonChallengeJ2.setEnabled(true);
        buttonAceJ1.setEnabled(true);
        buttonAceJ2.setEnabled(true);

        timer.start(); //Démarre le chronomètre
        buttonStart.setVisibility(View.INVISIBLE);
    }

    public void onClickButtonScoreUp(TextView tvScore, TextView tvScoreAdv){
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
                    //Incrémenter le nombre de jeu du set correspondant car le jeu est gagné
                    onClickButtonIncrementationSet();
                    tvScoreAdv.setText("00");
                }
            }else if (tabPoint[pos] == presentIntVal && pos == 4){
                tvScore.setText("00");
                //Incrémenter le nombre de jeu du set correspondant car le jeu est gagné
                onClickButtonIncrementationSet();
                tvScoreAdv.setText("00");
            }
        }
    }

    public void onClickButtonAce(TextView tvScore, TextView tvScoreAdv){ //Clique sur Ace pour ajouter des points sur le service sans touche de la part de l'adversaire
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.pop_ace,
                (ViewGroup) findViewById(R.id.custom_toast_container));

        TextView text = layout.findViewById(R.id.textViewComptabilise);
        text.setText("LE ACE A ETE COMPTABILISE");

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);

        onClickButtonScoreUp(tvScore, tvScoreAdv);
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

    public void onClickButtonIncrementationSet(){ //Compte le nombre de jeu gagné par joueur
        int IntValSet1;
        int IntValSet2;
        int IntValSet3;
        String ValStrSet1=tvSet1J1.getText().toString();
        String ValStrSet2=tvSet2J1.getText().toString();
        String ValStrSet3=tvSet3J1.getText().toString();
        IntValSet1 = Integer.parseInt(ValStrSet1);
        IntValSet2 = Integer.parseInt(ValStrSet2);
        IntValSet3 = Integer.parseInt(ValStrSet3);
        if (IntValSet1 < 6) {
            tvSet1J1.setText(String.valueOf(IntValSet1 + 1)); //Incrémente le set 1
        }else if (IntValSet2 < 6){
            tvSet2J1.setText(String.valueOf(IntValSet2 + 1)); //Incrémente le set 2
        }else if (IntValSet3 < 6){
            tvSet3J1.setText(String.valueOf(IntValSet3 + 1)); //Incrémente le set 3
        }
    }
}
