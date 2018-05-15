package com.atp.live_atp_mobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class PermissionGps extends AppCompatActivity {

    public static boolean permissionGps;

    private void createGpsDisabledAlert() {
        final AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder
                .setMessage("Le GPS est inactif, voulez-vous l'activer ?")
                .setCancelable(false)
                .setPositiveButton("Activer GPS ",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                PermissionGps.this.showGpsOptions();
                                permissionGps = true;
                            }
                        }
                );
        localBuilder.setNegativeButton("Ne pas l'activer ",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        paramDialogInterface.cancel();
                        permissionGps = true;
                        PermissionGps.this.finish();
                    }
                }
        );
        localBuilder.create().show();
    }

    private void showGpsOptions() {
        startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS").addFlags(FLAG_ACTIVITY_NEW_TASK));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        permissionGps = false;

        createGpsDisabledAlert();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (permissionGps) {
            Intent intent = new Intent(PermissionGps.this, AuthenticationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
            permissionGps = false;
        }
    }
}
