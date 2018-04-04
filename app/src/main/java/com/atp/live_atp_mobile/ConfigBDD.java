package com.atp.live_atp_mobile;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class ConfigBDD implements Observer{

    private Context context;
    private List<TournamentBDD> tournamentBDDList;
    private List<CountryBDD> countryBDDList;
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

    public void loadModelMatchFromFirebase() { //Appel get du tour du tournoi en fonction du user et de sa rencontre (recupération de l'idTour) en comparant son heureDebut avec l'heure du device
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String idRencontre = AuthenticationActivity.sharedpreferencesAuthentication.getString(AuthenticationActivity.IdRencontre, null);
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

    public void loadModelPlayersFromFirebase(final int idPlayer1, final int idPlayer2, final String category) { //Appel get des joueurs en fonction de la categorie de la rencontre
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String table = "";
        if (category.equals("Simple messieurs")) {
            table = "joueurHomme";
        }else if (category.equals("Simple dames")) {
            table = "joueurFemme";
        }
        DatabaseReference player1Ref = database.getReference(table).child(String.valueOf(idPlayer1)); //Selectionne la table joueurHomme en fonction de l'idPlayer1 récupéré
        DatabaseReference player2Ref = database.getReference(table).child(String.valueOf(idPlayer2)); //Selectionne la table joueurHomme en fonction de l'idPlayer2 récupéré
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
                            mMyCallback.onCallbackCodeCountryJ1(country.libelle);
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
                            mMyCallback.onCallbackCodeCountryJ2(country.libelle);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void loadNationalityFlagFromFirebase(String libelleJ1, String libelleJ2) { //Appel get des drapeaux des pays en fonction du libelle de chaque joueur
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference NationalityRef1 = storage.getReference(libelleJ1 + ".png"); //Selectionne le drapeau en fonction du libelleNationalite du J1
        StorageReference NationalityRef2 = storage.getReference(libelleJ2 + ".png"); //Selectionne le drapeau en fonction du libelleNationalite du J2
        NationalityRef1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                mMyCallback.onCallbackNationalityJ1(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(context, "403 au secours", Toast.LENGTH_LONG).show();
            }
        });
        NationalityRef2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                mMyCallback.onCallbackNationalityJ2(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(context, "403 au secours", Toast.LENGTH_LONG).show();
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

