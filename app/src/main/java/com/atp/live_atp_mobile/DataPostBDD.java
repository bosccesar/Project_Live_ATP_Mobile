package com.atp.live_atp_mobile;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class DataPostBDD {

    private Context context;

    DataPostBDD(Context context) {
        this.context = context;
    }

    public void postEndMatch(final String idMatch) { //Transforme matchFini en true
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference MatchPostRef = database.getReference("rencontre").child(idMatch); //Selectionne l'id dans la table rencontre
        MatchBDD postMatch = new MatchBDD(true);
        Map<String, Object> postValueMatch = postMatch.toMap();
        MatchPostRef.updateChildren(postValueMatch);
    }

    public void postStatsEndMatch(final String idMatch, final String idWinner, final String idLooser) { //Ajoute le vainqueur et le perdant de la rencontre, incrémente les stats des 2 joueurs (défaite et victoires)
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Ajoute le vainqueur et le perdant de la rencontre dans la table statsRencontre
        final DatabaseReference statsMatchPostRef = database.getReference("statsRencontre");
        statsMatchPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Récupération de l'ensemble des stats
                if (iterable != null) {
                    StatMatchBDD statMatch;
                    for (DataSnapshot getSnapshot : iterable) {
                        String key = getSnapshot.getKey();
                        statMatch = getSnapshot.getValue(StatMatchBDD.class);
                        if (idMatch.equals(String.valueOf(statMatch.idRencontre))) {
                            StatMatchBDD postStat = new StatMatchBDD(idWinner, idLooser);
                            Map<String, Object> postValuesStat = postStat.toMapWinAndLost();
                            statsMatchPostRef.child(key).updateChildren(postValuesStat); //Ajoute le vainqueur et le perdant sans supprimer les autres données
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Incrémente stats des 2 joueurs dans la table statJoueur
        final DatabaseReference statsPlayerPostRef = database.getReference("statsJoueur");
        statsPlayerPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Récupération de l'ensemble des stats
                if (iterable != null) {
                    StatPlayerBDD statPlayer;
                    for (DataSnapshot getSnapshot : iterable) {
                        String key = getSnapshot.getKey();
                        statPlayer = getSnapshot.getValue(StatPlayerBDD.class);
                        if (idWinner.equals(String.valueOf(statPlayer.idJoueur))) {
                            statPlayer.victoire++;
                            Map<String, Object> postValuesStat = statPlayer.toMapInteger();
                            statsPlayerPostRef.child(key).updateChildren(postValuesStat);
                        }
                        if (idLooser.equals(String.valueOf(statPlayer.idJoueur))) {
                            statPlayer.defaite++;
                            Map<String, Object> postValuesStat = statPlayer.toMapInteger();
                            statsPlayerPostRef.child(key).updateChildren(postValuesStat);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void postExceptionnalBreakMatch(final String idMatch) { //Inscrit pauseExceptionnelle dans les stats de la rencontre
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference statsMatchPostRef = database.getReference("statsRencontre");
        statsMatchPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Récupération de l'ensemble des stats
                if (iterable != null) {
                    StatMatchBDD statMatch;
                    for (DataSnapshot getSnapshot : iterable) {
                        String key = getSnapshot.getKey();
                        statMatch = getSnapshot.getValue(StatMatchBDD.class);
                        if (idMatch.equals(String.valueOf(statMatch.idRencontre))) {
                            StatMatchBDD postStat = new StatMatchBDD(true);
                            Map<String, Object> postValuesStat = postStat.toMapExceptionnalBreak();
                            statsMatchPostRef.child(key).updateChildren(postValuesStat); //Ajoute pauseExceptionnelle sans supprimer les autres données
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void postTimeMatch(final String idMatch, final String timeMatch) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference statsMatchPostRef = database.getReference("statsRencontre");
        statsMatchPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Récupération de l'ensemble des stats
                if (iterable != null) {
                    StatMatchBDD statMatch;
                    for (DataSnapshot getSnapshot : iterable) {
                        String key = getSnapshot.getKey();
                        statMatch = getSnapshot.getValue(StatMatchBDD.class);
                        if (idMatch.equals(String.valueOf(statMatch.idRencontre))) {
                            StatMatchBDD postStat = new StatMatchBDD(timeMatch);
                            Map<String, Object> postValuesStat = postStat.toMapChronometer();
                            statsMatchPostRef.child(key).updateChildren(postValuesStat); //Ajoute pauseExceptionnelle sans supprimer les autres données
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void postStatsAbortMatch(final String idMatch, final String idAbort, final String idOtherPlayer) { //La stat abandon a true et ajoute le joueur qui abandonne, incrémente les stats des 2 joueurs (abandon, victoire et victoires)
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //La stat abandon a true et ajoute le joueur qui abandonne dans la table statsRencontre
        final DatabaseReference statsMatchPostRef = database.getReference("statsRencontre");
        statsMatchPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Récupération de l'ensemble des stats
                if (iterable != null) {
                    StatMatchBDD statMatch;
                    for (DataSnapshot getSnapshot : iterable) {
                        String key = getSnapshot.getKey();
                        statMatch = getSnapshot.getValue(StatMatchBDD.class);
                        if (idMatch.equals(String.valueOf(statMatch.idRencontre))) {
                            StatMatchBDD postStat = new StatMatchBDD(false, true, Integer.parseInt(idAbort));
                            Map<String, Object> postValuesStat = postStat.toMapAbort();
                            statsMatchPostRef.child(key).updateChildren(postValuesStat);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Incrémente stats des 2 joueurs dans la table statsJoueur
        final DatabaseReference statsPlayerPostRef = database.getReference("statsJoueur");
        statsPlayerPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Récupération de l'ensemble des stats
                if (iterable != null) {
                    StatPlayerBDD statPlayer;
                    for (DataSnapshot getSnapshot : iterable) {
                        String key = getSnapshot.getKey();
                        statPlayer = getSnapshot.getValue(StatPlayerBDD.class);
                        if (idAbort.equals(String.valueOf(statPlayer.idJoueur))) {
                            statPlayer.abandon++;
                            statPlayer.defaite++;
                            Map<String, Object> postValuesStat = statPlayer.toMapInteger();
                            statsPlayerPostRef.child(key).updateChildren(postValuesStat);
                        }
                        if (idOtherPlayer.equals(String.valueOf(statPlayer.idJoueur))) {
                            statPlayer.victoire++;
                            Map<String, Object> postValuesStat = statPlayer.toMapInteger();
                            statsPlayerPostRef.child(key).updateChildren(postValuesStat);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void postStatsPauseMatch(final String idMatch, final String natureOfPause) { //Incrémentation de la stat toilettes ou soigneurs en fonction de la nature de la pause reçue
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference statsMatchPostRef = database.getReference("statsRencontre");
        statsMatchPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Récupération de l'ensemble des stats
                if (iterable != null) {
                    StatMatchBDD statMatch;
                    for (DataSnapshot getSnapshot : iterable) {
                        String key = getSnapshot.getKey();
                        statMatch = getSnapshot.getValue(StatMatchBDD.class);
                        if (idMatch.equals(String.valueOf(statMatch.idRencontre))) {
                            if (natureOfPause.equals("Toilettes")) {
                                statMatch.pauseToilettes++;
                            }else if (natureOfPause.equals("Soigneurs")) {
                                statMatch.pauseSoigneurs++;
                            }
                            Map<String, Object> postValuesStat = statMatch.toMapInteger();
                            statsMatchPostRef.child(key).updateChildren(postValuesStat);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void postSanctionOrderMatch(final String idMatch, final String idPlayer) { //Inscrit dans la statsRencontre à sanctionOrdre l'id du joueur et incrémente sanctionOrdre du joueur
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference statsMatchPostRef = database.getReference("statsRencontre");
        statsMatchPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Récupération de l'ensemble des stats
                if (iterable != null) {
                    StatMatchBDD statMatch;
                    for (DataSnapshot getSnapshot : iterable) {
                        String key = getSnapshot.getKey();
                        statMatch = getSnapshot.getValue(StatMatchBDD.class);
                        if (idMatch.equals(String.valueOf(statMatch.idRencontre))) {
                            Iterable<DataSnapshot> iterableSanction = dataSnapshot.child(key).child("sanctionOrdre").getChildren();
                            long countKey = 0;
                            for (DataSnapshot getSnapshotSanction : iterableSanction) {
                                countKey = countKey + 1;
                            }
                            DatabaseReference sanctionOrdrePostRef = statsMatchPostRef.child(key).child("sanctionOrdre"); //Récupération de l'ensemble des stats
                            StatMatchBDD postStat = new StatMatchBDD(Integer.parseInt(idPlayer));
                            Map<String, Object> postValuesStat = postStat.toMapPlayer();
                            sanctionOrdrePostRef.child(String.valueOf(countKey)).updateChildren(postValuesStat); //Ajoute idPlayer sans supprimer les autres données
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Incrémente sanctionOrdre du joueur
        final DatabaseReference statsPlayerPostRef = database.getReference("statsJoueur");
        statsPlayerPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Récupération de l'ensemble des stats
                if (iterable != null) {
                    StatPlayerBDD statPlayer;
                    for (DataSnapshot getSnapshot : iterable) {
                        String key = getSnapshot.getKey();
                        statPlayer = getSnapshot.getValue(StatPlayerBDD.class);
                        if (idPlayer.equals(String.valueOf(statPlayer.idJoueur))) {
                            statPlayer.sanctionOrdre++;
                            Map<String, Object> postValuesStat = statPlayer.toMapInteger();
                            statsPlayerPostRef.child(key).updateChildren(postValuesStat);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void postSanctionGameMatch(final String idMatch, final String idPlayer) { //Inscrit dans la statsRencontre à sanctionJeu l'id du joueur et incrémente sanctionJeu du joueur
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference statsMatchPostRef = database.getReference("statsRencontre");
        statsMatchPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Récupération de l'ensemble des stats
                if (iterable != null) {
                    StatMatchBDD statMatch;
                    for (DataSnapshot getSnapshot : iterable) {
                        String key = getSnapshot.getKey();
                        statMatch = getSnapshot.getValue(StatMatchBDD.class);
                        if (idMatch.equals(String.valueOf(statMatch.idRencontre))) {
                            Iterable<DataSnapshot> iterableSanction = dataSnapshot.child(key).child("sanctionJeu").getChildren();
                            long countKey = 0;
                            for (DataSnapshot getSnapshotSanction : iterableSanction) {
                                countKey = countKey + 1;
                            }
                            DatabaseReference sanctionOrderPostRef = statsMatchPostRef.child(key).child("sanctionJeu"); //Récupération de l'ensemble des stats
                            StatMatchBDD postStat = new StatMatchBDD(Integer.parseInt(idPlayer));
                            Map<String, Object> postValuesStat = postStat.toMapPlayer();
                            sanctionOrderPostRef.child(String.valueOf(countKey)).updateChildren(postValuesStat); //Ajoute idPlayer sans supprimer les autres données
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Incrémente sanctionJeu du joueur
        final DatabaseReference statsPlayerPostRef = database.getReference("statsJoueur");
        statsPlayerPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Récupération de l'ensemble des stats
                if (iterable != null) {
                    StatPlayerBDD statPlayer;
                    for (DataSnapshot getSnapshot : iterable) {
                        String key = getSnapshot.getKey();
                        statPlayer = getSnapshot.getValue(StatPlayerBDD.class);
                        if (idPlayer.equals(String.valueOf(statPlayer.idJoueur))) {
                            statPlayer.sanctionJeu++;
                            Map<String, Object> postValuesStat = statPlayer.toMapInteger();
                            statsPlayerPostRef.child(key).updateChildren(postValuesStat);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void postSanctionExclusionMatch(final String idMatch, final String idExcluded, final String idOtherPlayer) { //Inscrit dans la statsRencontre à sanctionExclusion l'id du joueur et incrémente exclusion du joueur
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //La stat abandon a true et ajoute le joueur qui abandonne dans la table statsRencontre
        final DatabaseReference statsMatchPostRef = database.getReference("statsRencontre");
        statsMatchPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Récupération de l'ensemble des stats
                if (iterable != null) {
                    StatMatchBDD statMatch;
                    for (DataSnapshot getSnapshot : iterable) {
                        String key = getSnapshot.getKey();
                        statMatch = getSnapshot.getValue(StatMatchBDD.class);
                        if (idMatch.equals(String.valueOf(statMatch.idRencontre))) {
                            StatMatchBDD postStat = new StatMatchBDD(true,false, Integer.parseInt(idExcluded));
                            Map<String, Object> postValuesStat = postStat.toMapExclusion();
                            statsMatchPostRef.child(key).updateChildren(postValuesStat);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Incrémente stats des 2 joueurs dans la table statsJoueur
        final DatabaseReference statsPlayerPostRef = database.getReference("statsJoueur");
        statsPlayerPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Récupération de l'ensemble des stats
                if (iterable != null) {
                    StatPlayerBDD statPlayer;
                    for (DataSnapshot getSnapshot : iterable) {
                        String key = getSnapshot.getKey();
                        statPlayer = getSnapshot.getValue(StatPlayerBDD.class);
                        if (idExcluded.equals(String.valueOf(statPlayer.idJoueur))) {
                            statPlayer.exclusion++;
                            statPlayer.defaite++;
                            Map<String, Object> postValuesStat = statPlayer.toMapInteger();
                            statsPlayerPostRef.child(key).updateChildren(postValuesStat);
                        }
                        if (idOtherPlayer.equals(String.valueOf(statPlayer.idJoueur))) {
                            statPlayer.victoire++;
                            Map<String, Object> postValuesStat = statPlayer.toMapInteger();
                            statsPlayerPostRef.child(key).updateChildren(postValuesStat);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void postChallengeMatch(final String idMatch, final String idPlayer) { //Inscrit dans la statsRencontre à challenge l'id du joueur
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference statsMatchPostRef = database.getReference("statsRencontre");
        statsMatchPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Récupération de l'ensemble des stats
                if (iterable != null) {
                    StatMatchBDD statMatch;
                    for (DataSnapshot getSnapshot : iterable) {
                        String key = getSnapshot.getKey();
                        statMatch = getSnapshot.getValue(StatMatchBDD.class);
                        if (idMatch.equals(String.valueOf(statMatch.idRencontre))) {
                            Iterable<DataSnapshot> iterableChallenge = dataSnapshot.child(key).child("challenge").getChildren();
                            long countKey = 0;
                            for (DataSnapshot getSnapshotChallenge : iterableChallenge) {
                                countKey = countKey + 1;
                            }
                            DatabaseReference challengePostRef = statsMatchPostRef.child(key).child("challenge");
                            StatMatchBDD postStat = new StatMatchBDD(Integer.parseInt(idPlayer));
                            Map<String, Object> postValuesStat = postStat.toMapPlayer();
                            challengePostRef.child(String.valueOf(countKey)).updateChildren(postValuesStat);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void postLetMatch(final String idMatch, final String idPlayer) { //Inscrit dans la statsRencontre à let l'id du joueur
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference statsMatchPostRef = database.getReference("statsRencontre");
        statsMatchPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Récupération de l'ensemble des stats
                if (iterable != null) {
                    StatMatchBDD statMatch;
                    for (DataSnapshot getSnapshot : iterable) {
                        String key = getSnapshot.getKey();
                        statMatch = getSnapshot.getValue(StatMatchBDD.class);
                        if (idMatch.equals(String.valueOf(statMatch.idRencontre))) {
                            Iterable<DataSnapshot> iterableLet = dataSnapshot.child(key).child("let").getChildren();
                            long countKey = 0;
                            for (DataSnapshot getSnapshotLet : iterableLet) {
                                countKey = countKey + 1;
                            }
                            DatabaseReference LetPostRef = statsMatchPostRef.child(key).child("let");
                            StatMatchBDD postStat = new StatMatchBDD(Integer.parseInt(idPlayer));
                            Map<String, Object> postValuesStat = postStat.toMapPlayer();
                            LetPostRef.child(String.valueOf(countKey)).updateChildren(postValuesStat);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void postServiceMatch(final String idMatch, final String idPlayer, final boolean twoService) { //Incrémente service du joueur
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference statsPlayerPostRef = database.getReference("statsJoueur");
        statsPlayerPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Récupération de l'ensemble des stats
                if (iterable != null) {
                    StatPlayerBDD statPlayer;
                    for (DataSnapshot getSnapshot : iterable) {
                        String key = getSnapshot.getKey();
                        statPlayer = getSnapshot.getValue(StatPlayerBDD.class);
                        if (idPlayer.equals(String.valueOf(statPlayer.idJoueur))) {
                            statPlayer.service++;
                            if (twoService) {
                                statPlayer.secondService++;
                            }
                            Map<String, Object> postValuesStat = statPlayer.toMapInteger();
                            statsPlayerPostRef.child(key).updateChildren(postValuesStat);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final DatabaseReference statsMatchPostRef = database.getReference("statsRencontre");
        statsMatchPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Récupération de l'ensemble des stats
                if (iterable != null) {
                    StatMatchBDD statMatch;
                    for (DataSnapshot getSnapshot : iterable) {
                        String key = getSnapshot.getKey();
                        statMatch = getSnapshot.getValue(StatMatchBDD.class);
                        if (idMatch.equals(String.valueOf(statMatch.idRencontre))) {
                            statMatch.nombreService++;
                            Map<String, Object> postValuesStat = statMatch.toMapInteger();
                            statsMatchPostRef.child(key).updateChildren(postValuesStat);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void postTwoServiceMatch(final String idMatch, final String idPlayer, final int idSet, final int idGame, final int idPoint) { //Inscrit dans la statsRencontre à secondService l'idJoueur/idSet/idGame/idPoint
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference statsMatchPostRef = database.getReference("statsRencontre");
        statsMatchPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Récupération de l'ensemble des stats
                if (iterable != null) {
                    StatMatchBDD statMatch;
                    for (DataSnapshot getSnapshot : iterable) {
                        String key = getSnapshot.getKey();
                        statMatch = getSnapshot.getValue(StatMatchBDD.class);
                        if (idMatch.equals(String.valueOf(statMatch.idRencontre))) {
                            Iterable<DataSnapshot> iterableTwoService = dataSnapshot.child(key).child("secondService").getChildren();
                            long countKey = 0;
                            for (DataSnapshot getSnapshotTwoService : iterableTwoService) {
                                countKey = countKey + 1;
                            }
                            DatabaseReference twoServicePostRef = statsMatchPostRef.child(key).child("secondService");
                            StatMatchBDD postStat = new StatMatchBDD(Integer.parseInt(idPlayer), idSet, idGame, idPoint);
                            Map<String, Object> postValuesStat = postStat.toMapDetailsOfPoint();
                            twoServicePostRef.child(String.valueOf(countKey)).updateChildren(postValuesStat);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void postScoreMatch(final String idMatch, final String idPlayerService, final String idSet, final String idGame, final String libelleJ1, final String libelleJ2) { //Crée un point (set/jeu/point) dans la statsRencontre et dans ce point la mettre ace/service a true et incrémente les stats ace/service dans la statsJoueur
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference statsMatchPostRef = database.getReference("statsRencontre");
        statsMatchPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Récupération de l'ensemble des stats
                if (iterable != null) {
                    StatMatchBDD statMatch;
                    for (DataSnapshot getSnapshot : iterable) {
                        String keyMatch = getSnapshot.getKey();
                        statMatch = getSnapshot.getValue(StatMatchBDD.class);
                        if (idMatch.equals(String.valueOf(statMatch.idRencontre))) {
                            Iterable<DataSnapshot> iterableSet = dataSnapshot.child(keyMatch).child("set").getChildren(); //Recuperation de l'ensemble des set
                            int verifForSet = 0; //Permet de déterminer si on est rentré dans le if de la boucle for pour déterminer si on ajoute un nouveau set
                            for (DataSnapshot getSnapshotSet : iterableSet) {
                                String keySet = getSnapshotSet.getKey();
                                if (idSet.equals(keySet)) {
                                    verifForSet = verifForSet + 1;
                                    Iterable<DataSnapshot> iterableGame = dataSnapshot.child(keyMatch).child("set").child(keySet).child("jeu").getChildren(); //Recuperation de l'ensemble des jeux
                                    int verifForGame = 0;
                                    for (DataSnapshot getSnapshotGame : iterableGame) {
                                        String keyGame = getSnapshotGame.getKey();
                                        if (idGame.equals(keyGame)) {
                                            verifForGame = verifForGame + 1;
                                            Iterable<DataSnapshot> iterablePoint = dataSnapshot.child(keyMatch).child("set").child(keySet).child("jeu").child(keyGame).child("point").getChildren(); //Recuperation de l'ensemble des points
                                            long countKey = 0;
                                            for (DataSnapshot getSnapshotPoint : iterablePoint) {
                                                countKey = countKey + 1;
                                            }
                                            DatabaseReference pointPostRef = statsMatchPostRef.child(keyMatch).child("set").child(keySet).child("jeu").child(keyGame).child("point");
                                            StatMatchBDD postStat = new StatMatchBDD(idPlayerService, libelleJ1, libelleJ2);
                                            Map<String, Object> postValuesStat = postStat.toMapScore();
                                            pointPostRef.child(String.valueOf(countKey)).updateChildren(postValuesStat);
                                        }
                                    }
                                    if (verifForGame == 0) {
                                        Iterable<DataSnapshot> iterableNewGame = dataSnapshot.child(keyMatch).child("set").child(keySet).child("jeu").getChildren(); //Recuperation de l'ensemble des jeux
                                        long countKey = 0;
                                        for (DataSnapshot getSnapshotNewGame : iterableNewGame) {
                                            countKey = countKey + 1;
                                        }
                                        //Insert le point dans l'arborescence du jeu nouvellement généré
                                        DatabaseReference PointPostRef = statsMatchPostRef.child(keyMatch).child("set").child(keySet).child("jeu").child(String.valueOf(countKey)).child("point");
                                        StatMatchBDD postStat = new StatMatchBDD(idPlayerService, libelleJ1, libelleJ2);
                                        Map<String, Object> postValuesStat = postStat.toMapScore();
                                        PointPostRef.child(String.valueOf(0)).updateChildren(postValuesStat);
                                    }
                                }
                            }
                            if (verifForSet == 0) {
                                Iterable<DataSnapshot> iterableNewSet = dataSnapshot.child(keyMatch).child("set").getChildren(); //Recuperation de l'ensemble des set
                                long countKey = 0;
                                for (DataSnapshot getSnapshotNewSet : iterableNewSet) {
                                    countKey = countKey + 1;
                                }
                                //Insert le point dans l'arborescence du set nouvellement généré
                                DatabaseReference PointPostRef = statsMatchPostRef.child(keyMatch).child("set").child(String.valueOf(countKey)).child("jeu").child(String.valueOf(0)).child("point");
                                StatMatchBDD postStat = new StatMatchBDD(idPlayerService, libelleJ1, libelleJ2);
                                Map<String, Object> postValuesStat = postStat.toMapScore();
                                PointPostRef.child(String.valueOf(0)).updateChildren(postValuesStat);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void postBreakOrDebreakMatch(final String idMatch, final String idPlayer, final int idSet, final int idGame, final String natureOfStat) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference statsMatchPostRef = database.getReference("statsRencontre");
        statsMatchPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Récupération de l'ensemble des stats
                if (iterable != null) {
                    StatMatchBDD statMatch;
                    for (DataSnapshot getSnapshot : iterable) {
                        String key = getSnapshot.getKey();
                        statMatch = getSnapshot.getValue(StatMatchBDD.class);
                        if (idMatch.equals(String.valueOf(statMatch.idRencontre))) {
                            Iterable<DataSnapshot> iterableSanction = dataSnapshot.child(key).child(natureOfStat).getChildren();
                            long countKey = 0;
                            for (DataSnapshot getSnapshotSanction : iterableSanction) {
                                countKey = countKey + 1;
                            }
                            DatabaseReference sanctionStatOfPointPostRef = statsMatchPostRef.child(key).child(natureOfStat);
                            StatMatchBDD postStat = new StatMatchBDD(Integer.parseInt(idPlayer), idSet, idGame);
                            Map<String, Object> postValuesStat = postStat.toMapBreakOrDebreak();
                            sanctionStatOfPointPostRef.child(String.valueOf(countKey)).updateChildren(postValuesStat);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final DatabaseReference statsPlayerPostRef = database.getReference("statsJoueur");
        statsPlayerPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Récupération de l'ensemble des stats
                if (iterable != null) {
                    StatPlayerBDD statPlayer;
                    for (DataSnapshot getSnapshot : iterable) {
                        String key = getSnapshot.getKey();
                        statPlayer = getSnapshot.getValue(StatPlayerBDD.class);
                        if (idPlayer.equals(String.valueOf(statPlayer.idJoueur))) {
                            if (natureOfStat.equals("breack")) {
                                statPlayer.breack++;
                            }
                            if (natureOfStat.equals("debreak")) {
                                statPlayer.debreak++;
                            }
                            Map<String, Object> postValuesStat = statPlayer.toMapInteger();
                            statsPlayerPostRef.child(key).updateChildren(postValuesStat);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void postAvantageOrAceMatch(final String idMatch, final String idPlayer, final int idSet, final int idGame, final int idPoint, final String avantageOrAce) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference statsMatchPostRef = database.getReference("statsRencontre");
        statsMatchPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Récupération de l'ensemble des stats
                if (iterable != null) {
                    StatMatchBDD statMatch;
                    for (DataSnapshot getSnapshot : iterable) {
                        String key = getSnapshot.getKey();
                        statMatch = getSnapshot.getValue(StatMatchBDD.class);
                        if (idMatch.equals(String.valueOf(statMatch.idRencontre))) {
                            Iterable<DataSnapshot> iterableSanction = dataSnapshot.child(key).child(avantageOrAce).getChildren();
                            long countKey = 0;
                            for (DataSnapshot getSnapshotSanction : iterableSanction) {
                                countKey = countKey + 1;
                            }
                            DatabaseReference sanctionStatOfPointPostRef = statsMatchPostRef.child(key).child(avantageOrAce);
                            StatMatchBDD postStat = new StatMatchBDD(Integer.parseInt(idPlayer), idSet, idGame, idPoint);
                            Map<String, Object> postValuesStat = postStat.toMapDetailsOfPoint();
                            sanctionStatOfPointPostRef.child(String.valueOf(countKey)).updateChildren(postValuesStat);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final DatabaseReference statsPlayerPostRef = database.getReference("statsJoueur");
        statsPlayerPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Récupération de l'ensemble des stats
                if (iterable != null) {
                    StatPlayerBDD statPlayer;
                    for (DataSnapshot getSnapshot : iterable) {
                        String key = getSnapshot.getKey();
                        statPlayer = getSnapshot.getValue(StatPlayerBDD.class);
                        if (idPlayer.equals(String.valueOf(statPlayer.idJoueur))) {
                            if (avantageOrAce.equals("avantage")) {
                                statPlayer.avantage++;
                            }
                            if (avantageOrAce.equals("ace")) {
                                statPlayer.ace++;
                            }
                            Map<String, Object> postValuesStat = statPlayer.toMapInteger();
                            statsPlayerPostRef.child(key).updateChildren(postValuesStat);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void postMistakeMatch(final String idMatch, final String idPlayer, final int idSet, final int idGame, final int idPoint, final String natureOfMistake) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference statsMatchPostRef = database.getReference("statsRencontre");
        statsMatchPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Récupération de l'ensemble des stats
                if (iterable != null) {
                    StatMatchBDD statMatch;
                    for (DataSnapshot getSnapshot : iterable) {
                        String key = getSnapshot.getKey();
                        statMatch = getSnapshot.getValue(StatMatchBDD.class);
                        if (idMatch.equals(String.valueOf(statMatch.idRencontre))) {
                            Iterable<DataSnapshot> iterableSanction = dataSnapshot.child(key).child("faute").getChildren();
                            long countKey = 0;
                            for (DataSnapshot getSnapshotSanction : iterableSanction) {
                                countKey = countKey + 1;
                            }
                            DatabaseReference sanctionStatOfPointPostRef = statsMatchPostRef.child(key).child("faute");
                            if (natureOfMistake.equals("filet")) {
                                StatMatchBDD postStat = new StatMatchBDD(Integer.parseInt(idPlayer), idSet, idGame, idPoint, true, false);
                                Map<String, Object> postValuesStat = postStat.toMapNet();
                                sanctionStatOfPointPostRef.child(String.valueOf(countKey)).updateChildren(postValuesStat);
                            }else if (natureOfMistake.equals("out")) {
                                StatMatchBDD postStat = new StatMatchBDD(Integer.parseInt(idPlayer), idSet, idGame, idPoint, false, true);
                                Map<String, Object> postValuesStat = postStat.toMapOut();
                                sanctionStatOfPointPostRef.child(String.valueOf(countKey)).updateChildren(postValuesStat);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final DatabaseReference statsPlayerPostRef = database.getReference("statsJoueur");
        statsPlayerPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Récupération de l'ensemble des stats
                if (iterable != null) {
                    StatPlayerBDD statPlayer;
                    for (DataSnapshot getSnapshot : iterable) {
                        String key = getSnapshot.getKey();
                        statPlayer = getSnapshot.getValue(StatPlayerBDD.class);
                        if (idPlayer.equals(String.valueOf(statPlayer.idJoueur))) {
                            statPlayer.faute++;
                            Map<String, Object> postValuesStat = statPlayer.toMapInteger();
                            statsPlayerPostRef.child(key).updateChildren(postValuesStat);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
