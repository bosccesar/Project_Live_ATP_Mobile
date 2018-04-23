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
    public boolean abandon;
    public int idJoueurforfait;
    public int pauseToilettes;
    public int pauseSoigneurs;
    public int sanctionOrdre;
    public int sanctionJeu;

    public StatMatchBDD() {

    }

    public StatMatchBDD(int idJeu, int idJoueur, int idSet, boolean filet, int idPoint, boolean out, int idRencontre, int nombreService, int valeur, boolean ace, String libelle, boolean secondService, boolean serviceReussi, boolean pauseExceptionnelle, String chronometre, boolean abandon, int idJoueurforfait, int pauseToilettes, int pauseSoigneurs, int sanctionOrdre, int sanctionJeu) {
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
        this.serviceReussi = serviceReussi;
        this.pauseExceptionnelle = pauseExceptionnelle;
        this.chronometre = chronometre;
        this.abandon = abandon;
        this.idJoueurforfait = idJoueurforfait;
        this.pauseToilettes = pauseToilettes;
        this.pauseSoigneurs = pauseSoigneurs;
        this.sanctionOrdre = sanctionOrdre;
        this.sanctionJeu = sanctionJeu;
    }

    public StatMatchBDD(boolean pauseExceptionnelle) {
        this.pauseExceptionnelle = pauseExceptionnelle;
    }

    public StatMatchBDD(String chronometre) {
        this.chronometre = chronometre;
    }

    public StatMatchBDD(int sanctionOrdre) {
        this.sanctionOrdre = sanctionOrdre;
    }

    public StatMatchBDD(boolean abandon, int idJoueurforfait) {
        this.abandon = abandon;
        this.idJoueurforfait = idJoueurforfait;
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
}
