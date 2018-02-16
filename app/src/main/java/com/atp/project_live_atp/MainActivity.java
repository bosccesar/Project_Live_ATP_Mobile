package com.atp.project_live_atp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.StaticLayout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static int[] tabPoint = {0, 15, 30, 40, -1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageButton button = findViewById(R.id.imageButtonModifPointJ1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int presentIntVal;
                TextView tvScore=findViewById(R.id.textScoreJ1);
                TextView tvScoreAdv=findViewById(R.id.textScoreJ2);
                String presentValStr=tvScore.getText().toString();
                String presentValStrJ2=tvScoreAdv.getText().toString();
                if (presentValStr != "AV") {
                    presentIntVal = Integer.parseInt(presentValStr);
                }else{
                    presentIntVal = -1;
                }
                for (int pos = 0; pos < 5; ++pos) {
                    if (tabPoint[pos] == presentIntVal && pos < 3) {
                        tvScore.setText(String.valueOf(tabPoint[pos + 1]));
                    }else if (tabPoint[pos] == presentIntVal && pos == 3){
                        if (presentValStrJ2 == "AV"){ //Le joueur met un point alors que l'adrversaire avait un avantage -> remise à 40 pour les 2
                            tvScore.setText(String.valueOf(presentIntVal)); //Remise à 40 pour le joueur
                            tvScoreAdv.setText(String.valueOf(presentIntVal)); //Remise à 40 pour l'adversaire
                        }else if(presentValStrJ2 != "40"){ //Le joueur met un point alors qu'ils sont à égalité -> avantage pour lui
                            tvScore.setText("AV"); //Avantage pour le joueur
                            //Incrémenter dans la bdd la statistique avantage pour le joueur
                        }else{ //Le joueur adverse n'a ni 40 ni avantage (0 ou 15 ou 30) donc le joueur gagne le point
                            tvScore.setText("00");
                            //Incrémenter le jeu du set correspondant car le jeu est gagné
                        }
                    }else if (tabPoint[pos] == presentIntVal && pos == 4){
                        tvScore.setText("00");
                        //Incrémenter le jeu du set correspondant car le jeu est gagné
                    }
                }
            }
        });
    }
}
