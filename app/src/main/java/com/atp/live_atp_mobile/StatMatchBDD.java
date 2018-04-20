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
    public boolean serviceReussi;
    public int idVainqueur;
    public int idPerdant;
    public boolean pauseExceptionnelle;
    public String chronometre;

    public StatMatchBDD() {

    }

    public StatMatchBDD(int idJeu, int idJoueur, int idSet, boolean filet, int idPoint, boolean out, int idRencontre, int nombreService, boolean ace, String libelle, boolean secondService, boolean serviceReussi, boolean pauseExceptionnelle, String chronometre) {
        this.idJeu = idJeu;
        this.idJoueur = idJoueur;
        this.idSet = idSet;
        this.filet = filet;
        this.idPoint = idPoint;
        this.out = out;
        this.idRencontre = idRencontre;
        this.nombreService = nombreService;
        this.ace = ace;
        this.libelle = libelle;
        this.secondService = secondService;
        this.serviceReussi = serviceReussi;
        this.pauseExceptionnelle = pauseExceptionnelle;
        this.chronometre = chronometre;
    }

    public StatMatchBDD(boolean pauseExceptionnelle) {
        this.pauseExceptionnelle = pauseExceptionnelle;
    }

    public StatMatchBDD(String chronometre) {
        this.chronometre = chronometre;
    }

    public StatMatchBDD(int idVainqueur, int idPerdant) {
        this.idVainqueur = idVainqueur;
        this.idPerdant = idPerdant;
    }

    @Exclude
    public Map<String, Object> toMap() {
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
}
