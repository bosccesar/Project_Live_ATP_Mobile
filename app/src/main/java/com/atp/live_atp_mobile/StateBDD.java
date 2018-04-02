package com.atp.live_atp_mobile;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class StateBDD {
    public String nom;

    public StateBDD() {

    }

    public StateBDD(String nom) {
        this.nom = nom;
    }
}
