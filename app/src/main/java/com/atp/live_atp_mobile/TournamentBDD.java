package com.atp.live_atp_mobile;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by cesar on 16/03/2018.
 */

@IgnoreExtraProperties
public class TournamentBDD {
    public String nom;
    public String dateDebut;
    public String dateFin;
    public String latitudeDebut;
    public String latitudeFin;
    public String longitudeDebut;
    public String longitudeFin;
    public int idPays;

    public TournamentBDD() {

    }

    public TournamentBDD(String nom, String dateDebut, String dateFin, String latitudeDebut, String latitudeFin, String longitudeDebut, String longitudeFin, int pays) {
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.latitudeDebut = latitudeDebut;
        this.latitudeFin = latitudeFin;
        this.longitudeDebut = longitudeDebut;
        this.longitudeFin = longitudeFin;
        this.idPays = pays;
    }
}
