package com.atp.live_atp_mobile;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by cesar on 22/03/2018.
 */

public class ConfigBDD {

    private static double latitude;
    private static double longitude;
    private static MyCallback mMyCallback;
    private static String resultNameTournamentBdd;
    private static String resultDateTournamentBdd;
    public static TournamentBDD tournamentBDD;

    public void setMyCallback(MyCallback callback) {
        mMyCallback = callback;
    }

    public static void loadModelTournamentFromFirebase(Context context) { //Appel get du tournoi en fonction du jour et de la latitude/longitude de l'endroit de la tablette
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        latitude = AuthenticationActivity.resultLongitude;
        longitude = AuthenticationActivity.resultLongitude;
        Toast.makeText(context, latitude + " / " + longitude, Toast.LENGTH_LONG).show(); //A voir
        DatabaseReference tournamentRef = database.getReference("tournoi").child("0"); //Pour choisir le tournoi pour les tests

        tournamentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tournamentBDD = dataSnapshot.getValue(TournamentBDD.class); //Recuperation des elements du tournoi
                if (tournamentBDD != null) {
                    resultNameTournamentBdd = tournamentBDD.nom; //Nom du tournoi
                    resultDateTournamentBdd = tournamentBDD.dateDebut + " - " + tournamentBDD.dateFin; //Date du tournoi

                    mMyCallback.onCallbackTournament(resultNameTournamentBdd, resultDateTournamentBdd);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

