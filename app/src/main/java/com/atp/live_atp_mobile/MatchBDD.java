package com.atp.live_atp_mobile;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class MatchBDD {
    public String date;
    public boolean equipe;
    public String heureDebut;
    public int idJoueur1;
    public int idJoueur2;
    public int idEquipe1;
    public int idEquipe2;
    public int idTableau;
    public int idTour;
    public boolean matchFini;

    public MatchBDD() {

    }

    public MatchBDD(String date, boolean equipe, String heureDebut, int idJoueur1, int idJoueur2, int idEquipe1, int idEquipe2, int idTableau, int idTour, boolean matchFini) {
        this.date = date;
        this.equipe = equipe;
        this.heureDebut = heureDebut;
        this.idJoueur1 = idJoueur1;
        this.idJoueur2 = idJoueur2;
        this.idEquipe1 = idEquipe1;
        this.idEquipe2 = idEquipe2;
        this.idTableau = idTableau;
        this.idTour = idTour;
        this.matchFini = matchFini;
    }
}
