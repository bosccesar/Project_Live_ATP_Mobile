package com.atp.live_atp_mobile;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by cebosc on 04/04/2018.
 */

@IgnoreExtraProperties
public class CountryBDD {
    public String code;
    public String libelle;

    public CountryBDD() {

    }

    public CountryBDD(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }
}
