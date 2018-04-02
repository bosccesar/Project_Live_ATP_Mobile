package com.atp.live_atp_mobile;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class MatchBDD {
    public String date;
    public String heureDebut;
    public int idJoueur1;
    public int idJoueur2;
    public int idTableau;
    public int idTour;
    public boolean matchFini;

    public MatchBDD() {

    }

    public MatchBDD(String date, String heureDebut, int idJoueur1, int idJoueur2, int idTableau, int idTour, boolean matchFini) {
        this.date = date;
        this.heureDebut = heureDebut;
        this.idJoueur1 = idJoueur1;
        this.idJoueur2 = idJoueur2;
        this.idTableau = idTableau;
        this.idTour = idTour;
        this.matchFini = matchFini;
    }
}
