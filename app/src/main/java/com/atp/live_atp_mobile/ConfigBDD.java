package com.atp.live_atp_mobile;

import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by cesar on 22/03/2018.
 */

public class ConfigBDD {

    private static MyCallback mMyCallback;

    public static TournamentBDD tournamentBDD;
    private static String resultNameTournamentBdd;
    private static String resultDateTournamentBdd;

    public void setMyCallback(MyCallback callback) {
        mMyCallback = callback;
    }

    public static void loadModelTournamentFromFirebase() { //Appel get du tournoi en fonction du jour et de l'horaire de la rencontre
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tournamentRef = database.getReference("tournoi").child("0");

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

