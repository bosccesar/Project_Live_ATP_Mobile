package com.atp.live_atp_mobile;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by cebosc on 30/03/2018.
 */

@IgnoreExtraProperties
public class UserBDD {
    public String username;
    public String password;

    public UserBDD() {

    }

    public UserBDD(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
