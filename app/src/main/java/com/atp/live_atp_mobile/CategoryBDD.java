package com.atp.live_atp_mobile;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class CategoryBDD {
    public String nom;

    public CategoryBDD() {

    }

    public CategoryBDD(String nom) {
        this.nom = nom;
    }
}
