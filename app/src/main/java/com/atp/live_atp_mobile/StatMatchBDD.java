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
    public int valeur;
    public boolean ace;
    public String libelle;
    public boolean secondService;
    public boolean service;
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

    public StatMatchBDD() {

    }

    public StatMatchBDD(int idJeu, int idJoueur, int idSet, boolean filet, int idPoint, boolean out, int idRencontre, int nombreService, int valeur, boolean ace, String libelle, boolean secondService, boolean service, boolean pauseExceptionnelle, String chronometre, boolean abandon, boolean exclusion, int idJoueurforfait, int idJoueurExclusion, int pauseToilettes, int pauseSoigneurs) {
        this.idJeu = idJeu;
        this.idJoueur = idJoueur;
        this.idSet = idSet;
        this.filet = filet;
        this.idPoint = idPoint;
        this.out = out;
        this.idRencontre = idRencontre;
        this.nombreService = nombreService;
        this.valeur = valeur;
        this.ace = ace;
        this.libelle = libelle;
        this.secondService = secondService;
        this.service = service;
        this.pauseExceptionnelle = pauseExceptionnelle;
        this.chronometre = chronometre;
        this.abandon = abandon;
        this.exclusion = exclusion;
        this.idJoueurforfait = idJoueurforfait;
        this.idJoueurExclusion = idJoueurExclusion;
        this.pauseToilettes = pauseToilettes;
        this.pauseSoigneurs = pauseSoigneurs;
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

    public StatMatchBDD(boolean ace, String libelle, boolean service) {
        this.ace = ace;
        this.libelle = libelle;
        this.service = service;
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
    public Map<String, Object> toMapTwoService() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("idJoueur", idJoueur);

        return result;
    }

    @Exclude
    public Map<String, Object> toMapAce() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ace", ace);
        result.put("libelle", libelle);
        result.put("service", service);

        return result;
    }
}
