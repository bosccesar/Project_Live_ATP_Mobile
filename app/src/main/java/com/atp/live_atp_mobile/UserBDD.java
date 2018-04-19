package com.atp.live_atp_mobile;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by cebosc on 30/03/2018.
 */

@IgnoreExtraProperties
public class UserBDD {
    public String username;
    public String password;
    public int idRencontre;

    public UserBDD() {

    }

    public UserBDD(String username, String password, int idRencontre) {
        this.username = username;
        this.password = password;
        this.idRencontre = idRencontre;
    }

    public UserBDD(int idRencontre) {
        this.idRencontre = idRencontre;
    }
}
