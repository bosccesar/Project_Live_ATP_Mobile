package com.atp.live_atp_mobile;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by cebosc on 03/04/2018.
 */

@IgnoreExtraProperties
public class TeamBDD {
    public String codeNationalite;
    public int idJoueur1;
    public int idJoueur2;
    public String nom;
    public String type;

    public TeamBDD() {

    }

    public TeamBDD(String codeNationalite, int idJoueur1, int idJoueur2, String nom, String type) {
        this.codeNationalite = codeNationalite;
        this.idJoueur1 = idJoueur1;
        this.idJoueur2 = idJoueur2;
        this.nom = nom;
        this.type = type;
    }
}
