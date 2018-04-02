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
    private static List<UserBDD> userBDDList;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private Date dateDebut;
    private Date dateFin;
    private double latitudeDebut;
    private double latitudeFin;
    private double longitudeDebut;
    private double longitudeFin;
    private Date currentDate;
    private static double latitude;
    private static double longitude;
    private static MyCallback mMyCallback;
    private static String resultNameTournamentBdd;
    private static String resultDateTournamentBdd;


    ConfigBDD(Context context) {
        this.context = context;
    }

    void setMyCallback(MyCallback callback) {
        mMyCallback = callback;
    }

    private void loadModelTournamentFromFirebase() { //Appel get du tournoi en fonction du jour et de la latitude/longitude de l'endroit de la tablette
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        //int day = calendar.get(Calendar.DAY_OF_MONTH);
        //int month = calendar.get(Calendar.MONTH) + 1; //Les mois vont de 0 à 11 (janvier à decembre) donc on incremente le mois de 1
        int year = calendar.get(Calendar.YEAR);

        //Donnees de test
        int day = 29;
        int month = 8;
        //Epsi Apothicaire
        //latitudeStart = 43.641620;
        //latitudeEnd = 43.643950;
        //longitudeStart = 3.836715;
        //longitudeEnd = 3.839001;
        //Epsi Croix verte
        //latitudeStart = 43.641594;
        //latitudeEnd = 43.643549;
        //longitudeStart = 3.842924;
        //longitudeEnd = 3.844684;
        //

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
                        latitudeDebut = Double.parseDouble(tournament.latitudeDebut);
                        latitudeFin = Double.parseDouble(tournament.latitudeFin);
                        longitudeDebut = Double.parseDouble(tournament.longitudeDebut);
                        longitudeFin = Double.parseDouble(tournament.longitudeFin);
                        if (((currentDate.equals(dateDebut) || currentDate.after(dateDebut)) && (currentDate.equals(dateFin) || currentDate.before(dateFin))) && ((latitude >= latitudeDebut && latitude <= latitudeFin) && (longitude >= longitudeDebut && longitude <= longitudeFin))){ //Si la date du device est située entre les dates du tournoi et à l'endroit du gps alors on affiche le tournoi
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

    public void loadModelUserFromFirebase() { //Appel get de l'arbitre pour comparer avec le login et password renseigne sur l'authentification
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("arbitre"); //Selectionne la table arbitre
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Recuperation de l'ensemble des tournois
                if (iterable != null) {
                    if (!(AuthenticationActivity.login.equals("admin") && AuthenticationActivity.login.equals("admin"))) {
                        UserBDD user;
                        userBDDList = new ArrayList<>();
                        for (DataSnapshot getSnapshot : iterable) {
                            user = getSnapshot.getValue(UserBDD.class);
                            userBDDList.add(user);
                            if (AuthenticationActivity.login.equals(user.username) && AuthenticationActivity.password.equals(user.password)) { //Si le login et password renseignes sont les bons en BDD
                                mMyCallback.onCallbackUser(user.idRencontre, user.username, user.password);
                            }else {
                                    AuthenticationActivity.editLogin.setText("");
                                    AuthenticationActivity.editPassword.setText("");
                                    Toast.makeText(context, "Authentication failed. Try again", Toast.LENGTH_LONG).show();
                            }
                        }
                    }else { //Si on se renseigne en mode admin
                        mMyCallback.onCallbackUser(0, "admin", "admin");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void loadModelStateTournamentFromFirebase(int id) { //Appel get du tour du tournoi en fonction du user et de sa rencontre (recupération de l'idTour) en comparant son heureDebut avec l'heure du device
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference stateTournamentRef = database.getReference("tour").child(String.valueOf(id)); //Selectionne la table rencontre pour récuperer le tour en fonction de l'idRencontre récupéré
        stateTournamentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                StateBDD stateBDD = dataSnapshot.getValue(StateBDD.class);
                if (stateBDD != null) {
                    mMyCallback.onCallbackStateTournament(stateBDD.nom);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void loadModelMatchFromFirebase() { //Appel get du tour du tournoi en fonction du user et de sa rencontre (recupération de l'idTour) en comparant son heureDebut avec l'heure du device
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String idRencontre = AuthenticationActivity.sharedpreferencesAuthentication.getString(AuthenticationActivity.IdRencontre, null);
        DatabaseReference matchRef = database.getReference("rencontre").child(idRencontre); //Selectionne la table rencontre pour récuperer le tour en fonction de l'idRencontre récupéré
        matchRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MatchBDD matchBDD = dataSnapshot.getValue(MatchBDD.class);
                if (matchBDD != null) {
                    mMyCallback.onCallbackMatch(matchBDD.idTableau, matchBDD.idTour);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void loadModelBoardFromFirebase(int id) { //Appel get du tableau du tournoi en fonction du tableau de la rencontre
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference boardRef = database.getReference("tableau").child(String.valueOf(id)); //Selectionne la table tableau pour récuperer l'idCategorie en fonction de l'idTableau récupéré
        boardRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                BoardBDD boardBDD = dataSnapshot.getValue(BoardBDD.class);
                if (boardBDD != null) {
                    mMyCallback.onCallbackBoard(boardBDD.idCategorie);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void loadModelCategoryFromFirebase(int id) { //Appel get de la categorie en fonction de la categorie du tableau
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference categoryRef = database.getReference("categorie").child(String.valueOf(id)); //Selectionne la table tableau pour récuperer l'idCategorie en fonction de l'idTableau récupéré
        categoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CategoryBDD categoryBDD = dataSnapshot.getValue(CategoryBDD.class);
                if (categoryBDD != null) {
                    mMyCallback.onCallbackCategory(categoryBDD.nom);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void reactGps(Observable observable) {
        latitude = ((Gps) observable).getLatitude();
        longitude = ((Gps) observable).getLongitude();
        loadModelTournamentFromFirebase();
    }
}

