package com.atp.live_atp_mobile;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by cesar on 09/04/2018.
 */

@IgnoreExtraProperties
public class UserTabMatchBDD {
    public int idRencontre;

    public UserTabMatchBDD() {

    }

    public UserTabMatchBDD(int idRencontre) {
        this.idRencontre = idRencontre;
    }
}
