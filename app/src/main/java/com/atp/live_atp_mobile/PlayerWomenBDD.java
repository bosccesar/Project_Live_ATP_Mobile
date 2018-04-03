package com.atp.live_atp_mobile;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by cebosc on 03/04/2018.
 */

@IgnoreExtraProperties
public class PlayerWomenBDD {
    public String codeNationalite;
    public int dateNaissance;
    public int idPlayerScore;
    public String main;
    public String nom;
    public String prenom;

    public PlayerWomenBDD() {

    }

    public PlayerWomenBDD(String codeNationalite, int dateNaissance, int idPlayerScore, String main, String nom, String prenom) {
        this.codeNationalite = codeNationalite;
        this.dateNaissance = dateNaissance;
        this.idPlayerScore = idPlayerScore;
        this.main = main;
        this.nom = nom;
        this.prenom = prenom;
    }
}
