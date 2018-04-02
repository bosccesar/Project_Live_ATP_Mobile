package com.atp.live_atp_mobile;

/**
 * Created by cesar on 22/03/2018.
 */

public interface MyCallback {
    void onCallbackTournament(String nameTournament, String dateTournament);
    void onCallbackUser(int idRencontre, String user, String passwordUser);
    void onCallbackMatch(int idTableau, int idTour);
    void onCallbackStateTournament(String nameTour);
    void onCallbackBoard(int idCategory);
    void onCallbackCategory(String nameCategory);
}
