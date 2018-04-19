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

    public void postEndMatch(final String idRencontre, final String idWinner, final String idLooser) { //Transforme matchFini en true, ajoute le vainqueur et le perdant de la rencontre, incrémente les stats des 2 joueurs (défaite et victoires)
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //MatchFini = true
        DatabaseReference MatchPostRef = database.getReference("rencontre").child(idRencontre); //Selectionne l'id dans la table rencontre
        final MatchBDD postMatch = new MatchBDD(true);
        final Map<String, Object> postValuesMatch = postMatch.toMap();
        MatchPostRef.updateChildren(postValuesMatch);

        //Ajoute le vainqueur et le perdant de la rencontre dans la table statsRencontre
        final DatabaseReference statsMatchPostRef = database.getReference("statsRencontre");
        statsMatchPostRef.addValueEventListener(new ValueEventListener() {
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
        statsPlayerPostRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren(); //Récupération de l'ensemble des stats
                if (iterable != null) {
                    StatPlayerBDD statPlayer;
                    for (DataSnapshot getSnapshot : iterable) {
                        String key = getSnapshot.getKey();
                        statPlayer = getSnapshot.getValue(StatPlayerBDD.class);
                        if (idWinner.equals(String.valueOf(statPlayer.idJoueur))) {
                            int win = statPlayer.victoire + 1; //Récupère la valeur de victoire et l'incrémente
                            int loose = statPlayer.defaite; //Récupère la valeur de défaite et l'incrémente
                            StatPlayerBDD postStat = new StatPlayerBDD(win, loose);
                            Map<String, Object> postValuesStat = postStat.toMap();
                            statsPlayerPostRef.child(key).updateChildren(postValuesStat);
                        }
                        if (idLooser.equals(String.valueOf(statPlayer.idJoueur))) {
                            int loose = statPlayer.defaite + 1; //Récupère la valeur de défaite et l'incrémente
                            int win = statPlayer.victoire; //Récupère la valeur de victoire et l'incrémente
                            StatPlayerBDD postStat = new StatPlayerBDD(win, loose);
                            Map<String, Object> postValuesStat = postStat.toMap();
                            statsPlayerPostRef.child(key).updateChildren(postValuesStat);

                            //Problème de crash de l'émulateur : incrémente sans s'arréter la bdd, A voir
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
