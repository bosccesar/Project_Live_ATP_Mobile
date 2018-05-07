package com.atp.live_atp_mobile;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class StatMatchBDD {
    public int idJeu;
    public int idJoueur;
    public int idSet;
    public boolean filet;
    public int idPoint;
    public boolean out;
    public int idRencontre;
    public int nombreService;
    public String libelleJ1;
    public String libelleJ2;
    public int idVainqueur;
    public int idPerdant;
    public boolean pauseExceptionnelle;
    public String chronometre;
    public boolean abandon;
    public boolean exclusion;
    public int idJoueurforfait;
    public int idJoueurExclusion;
    public int pauseToilettes;
    public int pauseSoigneurs;
    public int idJoueurService;
    public boolean valide;

    public StatMatchBDD() {

    }

    public StatMatchBDD(int idJeu, int idJoueur, int idSet, boolean filet, int idPoint, boolean out, int idRencontre, int nombreService, String libelleJ1, String libelleJ2, boolean pauseExceptionnelle, String chronometre, boolean abandon, boolean exclusion, int idJoueurforfait, int idJoueurExclusion, int pauseToilettes, int pauseSoigneurs, int idJoueurService, boolean valide) {
        this.idJeu = idJeu;
        this.idJoueur = idJoueur;
        this.idSet = idSet;
        this.filet = filet;
        this.idPoint = idPoint;
        this.out = out;
        this.idRencontre = idRencontre;
        this.nombreService = nombreService;
        this.libelleJ1 = libelleJ1;
        this.libelleJ2 = libelleJ2;
        this.pauseExceptionnelle = pauseExceptionnelle;
        this.chronometre = chronometre;
        this.abandon = abandon;
        this.exclusion = exclusion;
        this.idJoueurforfait = idJoueurforfait;
        this.idJoueurExclusion = idJoueurExclusion;
        this.pauseToilettes = pauseToilettes;
        this.pauseSoigneurs = pauseSoigneurs;
        this.idJoueurService = idJoueurService;
        this.valide = valide;
    }

    public StatMatchBDD(boolean pauseExceptionnelle) {
        this.pauseExceptionnelle = pauseExceptionnelle;
    }

    public StatMatchBDD(String chronometre) {
        this.chronometre = chronometre;
    }

    public StatMatchBDD(int idJoueur) {
        this.idJoueur = idJoueur;
    }

    public StatMatchBDD(int idJoueur, boolean valide) {
        this.idJoueur = idJoueur;
        this.valide = valide;
    }

    public StatMatchBDD(String idJoueurService, String libelleJ1, String libelleJ2) {
        this.idJoueurService = Integer.parseInt(idJoueurService);
        this.libelleJ1 = libelleJ1;
        this.libelleJ2 = libelleJ2;
    }

    public StatMatchBDD(boolean exclusion, boolean abandon, int idJoueur) {
        if (exclusion) {
            this.exclusion = exclusion;
            this.idJoueurExclusion = idJoueur;
        }else if (abandon) {
            this.abandon = abandon;
            this.idJoueurforfait = idJoueur;
        }
    }

    public StatMatchBDD(int idJoueur, int idSet, int idGame, int idPoint, boolean filet, boolean out) {
        if (filet) {
            this.filet = filet;
            this.idJoueur = idJoueur;
            this.idSet = idSet;
            this.idJeu = idGame;
            this.idPoint = idPoint;
        }else if (out) {
            this.out = out;
            this.idJoueur = idJoueur;
            this.idSet = idSet;
            this.idJeu = idGame;
            this.idPoint = idPoint;
        }
    }

    public StatMatchBDD(int idJoueur, int idSet, int idGame) {
        this.idJoueur = idJoueur;
        this.idSet = idSet;
        this.idJeu = idGame;
    }

    public StatMatchBDD(int idJoueur, int idSet, int idGame, int idPoint) {
        this.idJoueur = idJoueur;
        this.idSet = idSet;
        this.idJeu = idGame;
        this.idPoint = idPoint;
    }

    public StatMatchBDD(String idVainqueur, String idPerdant) {
        this.idVainqueur = Integer.parseInt(idVainqueur);
        this.idPerdant = Integer.parseInt(idPerdant);
    }

    @Exclude
    public Map<String, Object> toMapInteger() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("pauseToilettes", pauseToilettes);
        result.put("pauseSoigneurs", pauseSoigneurs);
        result.put("nombreService", nombreService);

        return result;
    }

    @Exclude
    public Map<String, Object> toMapWinAndLost() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("vainqueur", idVainqueur);
        result.put("perdant", idPerdant);

        return result;
    }

    @Exclude
    public Map<String, Object> toMapExceptionnalBreak() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("pauseExceptionnelle", pauseExceptionnelle);

        return result;
    }

    @Exclude
    public Map<String, Object> toMapChronometer() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("chronometre", chronometre);

        return result;
    }

    @Exclude
    public Map<String, Object> toMapAbort() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("abandon", abandon);
        result.put("idJoueurforfait", idJoueurforfait);

        return result;
    }

    @Exclude
    public Map<String, Object> toMapExclusion() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("exclusion", exclusion);
        result.put("idJoueurExclusion", idJoueurExclusion);

        return result;
    }

    @Exclude
    public Map<String, Object> toMapPlayer() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("idJoueur", idJoueur);

        return result;
    }

    @Exclude
    public Map<String, Object> toMapChallenge() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("idJoueur", idJoueur);
        result.put("valide", valide);

        return result;
    }

    @Exclude
    public Map<String, Object> toMapBreakOrDebreak() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("idJoueur", idJoueur);
        result.put("idSet", idSet);
        result.put("idJeu", idJeu);

        return result;
    }

    @Exclude
    public Map<String, Object> toMapDetailsOfPoint() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("idJoueur", idJoueur);
        result.put("idSet", idSet);
        result.put("idJeu", idJeu);
        result.put("idPoint", idPoint);

        return result;
    }

    @Exclude
    public Map<String, Object> toMapScore() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("libelleJ1", libelleJ1);
        result.put("libelleJ2", libelleJ2);
        result.put("idJoueurService", idJoueurService);

        return result;
    }

    @Exclude
    public Map<String, Object> toMapNet() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("filet", filet);
        result.put("idJoueur", idJoueur);
        result.put("idSet", idSet);
        result.put("idJeu", idJeu);
        result.put("idPoint", idPoint);

        return result;
    }

    @Exclude
    public Map<String, Object> toMapOut() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("out", out);
        result.put("idJoueur", idJoueur);
        result.put("idSet", idSet);
        result.put("idJeu", idJeu);
        result.put("idPoint", idPoint);

        return result;
    }
}
