package com.atp.live_atp_mobile;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by cesar on 22/03/2018.
 */

public class ConfigBDD implements Observer{

    private Context context;
    private TournamentBDD tournamentBDD;
    private static double latitude;
    private static double longitude;
    private static MyCallback mMyCallback;
    private static String resultNameTournamentBdd;
    private static String resultDateTournamentBdd;


    ConfigBDD(AuthenticationActivity authenticationActivity) {
        this.context = authenticationActivity;
    }

    void setMyCallback(MyCallback callback) {
        mMyCallback = callback;
    }

    private void loadModelTournamentFromFirebase() { //Appel get du tournoi en fonction du jour et de la latitude/longitude de l'endroit de la tablette
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        //int day = calendar.get(Calendar.DAY_OF_MONTH);
        int day = 10; //Donnee de test
        //int month = calendar.get(Calendar.MONTH) + 1; //Les mois vont de 0 à 11 (janvier à decembre) donc on incremente le mois de 1
        int month = 5 + 1; //Donnee de test
        int year = calendar.get(Calendar.YEAR);
        String date = day + "/0" + month +"/" + year;
        Toast.makeText(context, latitude + " | " + longitude + " | " + date, Toast.LENGTH_LONG).show(); //Verification de la bonne recuperation des coordonnees gps t du jour actuel
        //Parcourir la table tournoi et en fonction du gps et de la date recuperee avec le gps et la date dans la bdd alors on affiche le tournoi correspondant
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

    @Override
    public void react(Observable observable) {
        latitude = ((Gps) observable).getLatitude();
        longitude = ((Gps) observable).getLongitude();
        loadModelTournamentFromFirebase();
    }
}

