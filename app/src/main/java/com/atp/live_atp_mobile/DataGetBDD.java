package com.atp.live_atp_mobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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

public class DataGetBDD implements Observer{

    private Context context;
    private List<TournamentBDD> tournamentBDDList;
    private List<CountryBDD> countryBDDList;
    private static List<UserBDD> userBDDList;
    private SimpleDateFormat formatterDate = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat formatterHour = new SimpleDateFormat("HH:mm");
    private Date dateDebut;
    private Date dateFin;
    private Date dateMatch;
    private Date hourStart;
    private boolean aroundHourStart;
    private boolean matchValid;
    private double latitudeDebut;
    private double latitudeFin;
    private double longitudeDebut;
    private double longitudeFin;
    private Date currentDate;
    private Date currentHour;
    private static double latitude;
    private static double longitude;
    private static MyCallback mMyCallback;
    private static String resultNameTournamentBdd;
    private static String resultDateTournamentBdd;


    DataGetBDD(Context context) {
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

        String stringDate = day + "/" + month + "/" + year;
        try {
            currentDate = formatterDate.parse(stringDate);
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
                    int cptTournament = 0;
                    TournamentBDD tournament;
                    tournamentBDDList = new ArrayList<>();
                    for (DataSnapshot getSnapshot: iterable) {
                        tournament = getSnapshot.getValue(TournamentBDD.class);
                        tournamentBDDList.add(tournament);
                        try {
                            dateDebut = formatterDate.parse(tournament.dateDebut);
                            dateFin = formatterDate.parse(tournament.dateFin);
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
                            cptTournament = 1;
                        }
                    }
                    if (cptTournament == 0) {
                        mMyCallback.onCallbackTournament("No Tournament", "");
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
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Recuperation de l'ensemble des arbitres
                if (iterable != null) {
                    if (!(AuthenticationActivity.login.equals("admin") && AuthenticationActivity.login.equals("admin"))) {
                        UserBDD user;
                        userBDDList = new ArrayList<>();
                        for (DataSnapshot getSnapshot : iterable) {
                            String key = getSnapshot.getKey();
                            user = getSnapshot.getValue(UserBDD.class);
                            userBDDList.add(user);
                            if (AuthenticationActivity.login.equals(user.username) && AuthenticationActivity.password.equals(user.password)) { //Si le login et password renseignes sont les bons en BDD
                                Iterable<DataSnapshot> iterableRencontre = dataSnapshot.child(key).child("rencontre").getChildren(); //Recuperation de l'ensemble des rencontre
                                UserBDD matchUser;
                                for (DataSnapshot getSnapshotRencontre : iterableRencontre) {
                                    matchUser = getSnapshotRencontre.getValue(UserBDD.class);
                                    mMyCallback.onCallbackUser(matchUser.idRencontre, user.username, user.password);
                                }
//                                if (AuthenticationActivity.cptMatch == 0){
//                                    AuthenticationActivity.editLogin.setText("");
//                                    AuthenticationActivity.editPassword.setText("");
//                                    Toast.makeText(context, "Vous n'avez aucune rencontre de prévu aujourd'hui " + AuthenticationActivity.login + ". Veuillez réessayer 10 minutes avant la prochaine rencontre qui vous a été attribuée pour vous re-logger", Toast.LENGTH_LONG).show();
//                                }
                            }else {
                                    AuthenticationActivity.editLogin.setText("");
                                    AuthenticationActivity.editPassword.setText("");
                                    Toast.makeText(context, "Authentication failed. Try again", Toast.LENGTH_LONG).show();
                            }
                        }
                    }else { //Si on se renseigne en mode admin
                        mMyCallback.onCallbackUserAdmin();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void loadModelMatchFromFirebase(String idRencontre) { //Appel get de la rencontre
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference matchRef = database.getReference("rencontre").child(idRencontre); //Selectionne la table rencontre pour récuperer le tour en fonction de l'idRencontre récupéré
        matchRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MatchBDD matchBDD = dataSnapshot.getValue(MatchBDD.class);
                if (matchBDD != null) {
                    mMyCallback.onCallbackMatch(matchBDD.equipe, matchBDD.idTableau, matchBDD.idTour, matchBDD.idJoueur1, matchBDD.idJoueur2, matchBDD.idEquipe1, matchBDD.idEquipe2);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void loadVerifyMatchFromFirebase(final String idRencontre, final String user) { //Vérifie si un match attribué à un arbitre est disponible en fonction de la date, de l'heure et si le match n'est pas fini (pas encore joué). On retourne alors l'idRencontre
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        //int hour = calendar.get(Calendar.HOUR_OF_DAY);
        //int minute = calendar.get(Calendar.MINUTE);
        //int day = calendar.get(Calendar.DAY_OF_MONTH);
        //int month = calendar.get(Calendar.MONTH) + 1; //Les mois vont de 0 à 11 (janvier à decembre) donc on incremente le mois de 1
        int year = calendar.get(Calendar.YEAR);

        //Donnees de test
        int hour = 17;
        int minute = 55;
        int day = 9;
        int month = 9;

        final String stringDate = day + "/" + month + "/" + year;
        final String stringHour = hour + ":" + minute;
        try {
            currentDate = formatterDate.parse(stringDate);
            currentHour = formatterHour.parse(stringHour);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DatabaseReference matchRef = database.getReference("rencontre").child(idRencontre); //Selectionne la table rencontre en fonction de l'idRencontre récupéré pour vérifier si c'est le bon match
        matchRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MatchBDD matchBDD = dataSnapshot.getValue(MatchBDD.class);
                if (matchBDD != null) {
                    try {
                        dateMatch = formatterDate.parse(matchBDD.date);
                        hourStart = formatterHour.parse(matchBDD.heureDebut);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long diffHour = hourStart.getTime() - currentHour.getTime();
                    if(diffHour >= 0 && diffHour/(1000*60) <= 10 || diffHour <= 0 && diffHour/(1000*60) >= -10) {
                        aroundHourStart = true;
                    }else {
                        aroundHourStart = false;
                    }
                    if (!matchBDD.matchFini && (dateMatch.equals(currentDate) && aroundHourStart)) { //Si le match n'est pas fini, que le jour est bon et que l'on soit au alentour (10mn) de l'heure du début du match
                        matchValid = true;
                    }else {
                        matchValid = false;
                    }
                    mMyCallback.onCallbackVerifyMatch(matchValid, idRencontre, user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void loadModelStateTournamentFromFirebase(int id) { //Appel get du tour du tournoi en fonction de l'idTour
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference stateTournamentRef = database.getReference("tour").child(String.valueOf(id)); //Selectionne la table tour pour récuperer le nom du tour en fonction de l'idTour récupéré
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
        DatabaseReference categoryRef = database.getReference("categorie").child(String.valueOf(id)); //Selectionne la table category pour récuperer le nom de la categorie en fonction de l'idCategorie récupéré
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

    public void loadModelPlayersFromFirebase(final int idPlayer1, final int idPlayer2, final String category) { //Appel get des joueurs en fonction de la categorie de la rencontre
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference player1Ref = database.getReference();
        DatabaseReference player2Ref = database.getReference();
        if (category.equals("Simple messieurs")) {
            player1Ref = database.getReference("joueurHomme").child(String.valueOf(idPlayer1)); //Selectionne la table joueurHomme en fonction de l'idPlayer1 récupéré
            player2Ref = database.getReference("joueurHomme").child(String.valueOf(idPlayer2)); //Selectionne la table joueurHomme en fonction de l'idPlayer2 récupéré
        }else if (category.equals("Simple dames")) {
            player1Ref = database.getReference("joueurFemme").child(String.valueOf(idPlayer1)); //Selectionne la table joueurFemme en fonction de l'idPlayer1 récupéré
            player2Ref = database.getReference("joueurFemme").child(String.valueOf(idPlayer2)); //Selectionne la table joueurFemme en fonction de l'idPlayer2 récupéré
        }
        player1Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (category.equals("Simple messieurs")) {
                    PlayerMenBDD playerMenBDD = dataSnapshot.getValue(PlayerMenBDD.class);
                    if (playerMenBDD != null) {
                        mMyCallback.onCallbackPlayer1(idPlayer1, playerMenBDD.prenom, playerMenBDD.nom, playerMenBDD.codeNationalite);
                    }
                }else if (category.equals("Simple dames")){
                    PlayerWomenBDD playerWomenBDD = dataSnapshot.getValue(PlayerWomenBDD.class);
                    if (playerWomenBDD != null) {
                        mMyCallback.onCallbackPlayer1(idPlayer1, playerWomenBDD.prenom, playerWomenBDD.nom, playerWomenBDD.codeNationalite);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        player2Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (category.equals("Simple messieurs")) {
                    PlayerMenBDD playerMenBDD = dataSnapshot.getValue(PlayerMenBDD.class);
                    if (playerMenBDD != null) {
                        mMyCallback.onCallbackPlayer2(idPlayer2, playerMenBDD.prenom, playerMenBDD.nom, playerMenBDD.codeNationalite);
                    }
                }else if (category.equals("Simple dames")){
                    PlayerWomenBDD playerWomenBDD = dataSnapshot.getValue(PlayerWomenBDD.class);
                    if (playerWomenBDD != null) {
                        mMyCallback.onCallbackPlayer2(idPlayer2, playerWomenBDD.prenom, playerWomenBDD.nom, playerWomenBDD.codeNationalite);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void loadModelTeamFromFirebase(final int idTeam1, final int idTeam2, String category) { //Appel get des équipes en fonction de la categorie de la rencontre
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        if (!category.equals("Double mixte")) {
            final DatabaseReference team1Ref = database.getReference("equipe").child(String.valueOf(idTeam1)); //Selectionne la table equipe avec l'idTeam correspondant
            DatabaseReference team2Ref = database.getReference("equipe").child(String.valueOf(idTeam2)); //Selectionne la table equipe avec l'idTeam correspondant
            team1Ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    TeamBDD teamBDD = dataSnapshot.getValue(TeamBDD.class);
                    if (teamBDD != null) {
                        mMyCallback.onCallbackTeam1(teamBDD.codeNationalite, teamBDD.idJoueur1, teamBDD.idJoueur2, teamBDD.nom);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            team2Ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    TeamBDD teamBDD = dataSnapshot.getValue(TeamBDD.class);
                    if (teamBDD != null) {
                        mMyCallback.onCallbackTeam2(teamBDD.codeNationalite, teamBDD.idJoueur1, teamBDD.idJoueur2, teamBDD.nom);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else {
            final DatabaseReference team1Ref = database.getReference("equipe").child(String.valueOf(idTeam1)); //Selectionne la table equipe avec l'idTeam correspondant
            DatabaseReference team2Ref = database.getReference("equipe").child(String.valueOf(idTeam2)); //Selectionne la table equipe avec l'idTeam correspondant
            team1Ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    TeamBDD teamBDD = dataSnapshot.getValue(TeamBDD.class);
                    if (teamBDD != null) {
                        mMyCallback.onCallbackTeam1(teamBDD.codeNationalite, teamBDD.idJoueur1, teamBDD.idJoueur2, teamBDD.nom); //Comme c'est mixte le joueur 1 est un homme et le joueur 2 est une femme
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            team2Ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    TeamBDD teamBDD = dataSnapshot.getValue(TeamBDD.class);
                    if (teamBDD != null) {
                        mMyCallback.onCallbackTeam2(teamBDD.codeNationalite, teamBDD.idJoueur1, teamBDD.idJoueur2, teamBDD.nom); //Comme c'est mixte le joueur 1 est un homme et le joueur 2 est une femme
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public void loadCodeCountryFromFirebase(final String codeJ1, final String codeJ2) { //Appel get des drapeaux des pays
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference countryRef = database.getReference("pays"); //Selectionne la table cible
        countryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Recuperation de l'ensemble des pays
                if (iterable != null) {
                    CountryBDD country;
                    countryBDDList = new ArrayList<>();
                    for (DataSnapshot getSnapshot: iterable) {
                        country = getSnapshot.getValue(CountryBDD.class);
                        countryBDDList.add(country);
                        String code = country.code;
                        if (codeJ1.equals(code)){ //Si le codeJ1 est le meme qu'un code d'un pays
                            String libelle = country.libelle;
                            if (libelle.contains(" ")){
                                libelle = libelle.replaceAll(" ", "_");
                            }
                            mMyCallback.onCallbackCodeCountryJ1(libelle);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        countryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Recuperation de l'ensemble des pays
                if (iterable != null) {
                    CountryBDD country;
                    countryBDDList = new ArrayList<>();
                    for (DataSnapshot getSnapshot: iterable) {
                        country = getSnapshot.getValue(CountryBDD.class);
                        countryBDDList.add(country);
                        String code = country.code;
                        if (codeJ2.equals(code)){ //Si le codeJ1 est le meme qu'un code d'un pays
                            String libelle = country.libelle;
                            if (libelle.contains(" ")){
                                libelle = libelle.replaceAll(" ", "_");
                            }
                            mMyCallback.onCallbackCodeCountryJ2(libelle);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void loadNationalityFlagFromFirebase(String libelle, int idImage) { //Appel des drapeaux des pays en fonction du libelle de chaque joueur
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference nationalityRef = storage.getReference(libelle + ".png"); //Selectionne le drapeau en fonction du libelleNationalite du joueur
        if (idImage == 1){
            mMyCallback.onCallbackNationalityJ1(nationalityRef);
        }else if (idImage == 2){
            mMyCallback.onCallbackNationalityJ2(nationalityRef);
        }
    }

    @Override
    public void reactGps(Observable observable) {
        latitude = ((Gps) observable).getLatitude();
        longitude = ((Gps) observable).getLongitude();
        loadModelTournamentFromFirebase();
    }
}

