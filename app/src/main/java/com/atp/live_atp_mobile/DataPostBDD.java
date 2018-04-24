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
                            DatabaseReference sanctionOrdrePostRef = statsMatchPostRef.child(key).child("sanctionJeu"); //Récupération de l'ensemble des stats
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

        //Incrémente sanctionGame du joueur
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

    public void postSanctionExclusionMatch(final String idMatch, final String idExcluded, final String idOtherPlayer) { //Inscrit dans la statsRencontre à sanctionJeu l'id du joueur et incrémente sanctionJeu du joueur
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
}
