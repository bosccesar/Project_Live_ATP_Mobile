package com.atp.live_atp_mobile;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class BoardBDD {
    public int idCategorie;

    public BoardBDD() {

    }

    public BoardBDD(int idCategorie) {
        this.idCategorie = idCategorie;
    }
}
