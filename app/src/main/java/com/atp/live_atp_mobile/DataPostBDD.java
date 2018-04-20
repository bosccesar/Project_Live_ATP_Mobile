package com.atp.live_atp_mobile;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.Map;

public class DataPostBDD {

    private Context context;

    DataPostBDD(Context context) {
        this.context = context;
    }

    public void postEndMatch(final String idRencontre) { //Transforme matchFini en true
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference MatchPostRef = database.getReference("rencontre").child(idRencontre); //Selectionne l'id dans la table rencontre
        final MatchBDD postMatch = new MatchBDD(true);
        final Map<String, Object> postValueMatch = postMatch.toMap();
        MatchPostRef.updateChildren(postValueMatch);
    }

    public void postStatsEndMatch(final String idRencontre, final String idWinner, final String idLooser) { //Ajoute le vainqueur et le perdant de la rencontre, incrémente les stats des 2 joueurs (défaite et victoires)
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
                        if (idRencontre.equals(String.valueOf(statMatch.idRencontre))) {
                            StatMatchBDD postStat = new StatMatchBDD(Integer.parseInt(idWinner), Integer.parseInt(idLooser));
                            Map<String, Object> postValuesStat = postStat.toMap();
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
                            Map<String, Object> postValuesStat = statPlayer.toMap();
                            statsPlayerPostRef.child(key).updateChildren(postValuesStat);
                        }
                        if (idLooser.equals(String.valueOf(statPlayer.idJoueur))) {
                            statPlayer.defaite++;
                            Map<String, Object> postValuesStat = statPlayer.toMap();
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

    public void postExceptionnalBreakMatch(final String idRencontre) { //Inscrit pauseExceptionnelle dans les stats de la rencontre
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
                        if (idRencontre.equals(String.valueOf(statMatch.idRencontre))) {
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

    public void postTimeMatch(final String idRencontre, final String timeMatch) {
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
                        if (idRencontre.equals(String.valueOf(statMatch.idRencontre))) {
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
}
