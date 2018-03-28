package com.atp.live_atp_mobile;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by cesar on 22/03/2018.
 */

public class ConfigBDD implements Observer{

    private Context context;
    private List<TournamentBDD> tournamentBDDList;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private Date dateDebut;
    private Date dateFin;
    private Date currentDate;
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
        int day = 4; //Donnee de test
        //int month = calendar.get(Calendar.MONTH) + 1; //Les mois vont de 0 à 11 (janvier à decembre) donc on incremente le mois de 1
        int month = 7; //Donnee de test
        int year = calendar.get(Calendar.YEAR);
        String stringDate = day + "/" + month +"/" + year;
        try {
            currentDate = formatter.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Toast.makeText(context, latitude + " | " + longitude + " | " + currentDate, Toast.LENGTH_LONG).show(); //Verification de la bonne recuperation des coordonnees gps du jour actuel

        DatabaseReference tournamentRef = database.getReference("tournoi"); //Selectionne la table cible
        tournamentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Recuperation de l'ensemble des tournois
                if (iterable != null) {
                    TournamentBDD tournament;
                    tournamentBDDList = new ArrayList<>();
                    for (DataSnapshot getSnapshot: iterable) {
                        tournament = getSnapshot.getValue(TournamentBDD.class);
                        tournamentBDDList.add(tournament);
                        try {
                            dateDebut = formatter.parse(tournament.dateDebut);
                            dateFin = formatter.parse(tournament.dateFin);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if ((currentDate.equals(dateDebut) || currentDate.after(dateDebut)) && (currentDate.equals(dateFin) || currentDate.before(dateFin))){ //Si la date du device est située entre les dates du tournoi et à l'endroit du gps alors on affiche le tournoi
                            resultNameTournamentBdd = tournament.nom; //Nom du tournoi
                            resultDateTournamentBdd = tournament.dateDebut + " - " + tournament.dateFin; //Date du tournoi

                            mMyCallback.onCallbackTournament(resultNameTournamentBdd, resultDateTournamentBdd);
                        }
                    }
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

