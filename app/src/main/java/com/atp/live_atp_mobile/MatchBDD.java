package com.atp.live_atp_mobile;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class MatchBDD {
    public String date;
    public boolean equipe;
    public String heureDebut;
    public int idCategorie;
    public int idJoueur1;
    public int idJoueur2;
    public int idEquipe1;
    public int idEquipe2;
    public int idTour;
    public boolean matchFini;

    public MatchBDD() {

    }

    public MatchBDD(String date, boolean equipe, String heureDebut, int idCategorie, int idJoueur1, int idJoueur2, int idEquipe1, int idEquipe2, int idTour, boolean matchFini) {
        this.date = date;
        this.equipe = equipe;
        this.heureDebut = heureDebut;
        this.idCategorie = idCategorie;
        this.idJoueur1 = idJoueur1;
        this.idJoueur2 = idJoueur2;
        this.idEquipe1 = idEquipe1;
        this.idEquipe2 = idEquipe2;
        this.idTour = idTour;
        this.matchFini = matchFini;
    }

    public MatchBDD(boolean matchFini) {
        this.matchFini = matchFini;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("matchFini", matchFini);

        return result;
    }
}
