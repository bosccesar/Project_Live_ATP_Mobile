package com.atp.live_atp_mobile;

/**
 * Created by cebosc on 07/03/2018.
 */

public enum Tournament {
    AUSTRALIAN_OPEN("Australian Open", true),
    ROLAND_GARROS("Roland Garros", true),
    WIMBELDON("Wimbledon", true),
    US_OPEN("US Open", true),
    DAVIS_CUP("Davis Cup", false),
    FED_CUP("Fed Cup", false),
    OPEN_FRANCE("Open Sud de France", false);

    private final String nom;
    private final boolean grandChelem;

    Tournament(String nom, boolean grandChelem) {
        this.nom = nom;
        this.grandChelem = grandChelem;
    }

    @Override
    public String toString() {
        return this.nom;
    }

    public boolean grandChelem() {
        return this.grandChelem;
    }

    public static Tournament getTournamentByName(String nom) { //Parcours tous les tournois
        Tournament tournament = null;
        int i = 0;
        while (i < values().length && tournament == null) {
            if (values()[i].nom.equals(nom)) {
                tournament = values()[i];
            }
            i++;
        }
        return tournament;
    }
}
