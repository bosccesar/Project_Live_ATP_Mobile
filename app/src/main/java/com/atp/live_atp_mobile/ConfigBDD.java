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

    public static TournamentBDD tournamentBDD;
    private static String resultTournoiBdd;

    public static void loadModelTournamentFromFirebase(final TextView tvTournament) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tournamentRef = database.getReference("tournoi").child("0");

        tournamentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tournamentBDD = dataSnapshot.getValue(TournamentBDD.class);
                if (tournamentBDD != null) {
                    resultTournoiBdd = tournamentBDD.nom;
                    tvTournament.setText(resultTournoiBdd);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

