package com.atp.live_atp_mobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * Created by cesar on 23/03/2018.
 */

public class Gps extends Observable implements LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private double latitude;
    private double longitude;
    private long minute;
    private float metre;
    private Context context;
    private Activity activity;
    private GoogleApiClient googleApiClient;
    private Location myLocation;
    private FusedLocationProviderClient mFusedLocationClient;

    private static final String LOG_NAME = Gps.class.getName();

    Gps(Context context, Activity activity)
    {
        super();
        this.context = context;
        this.activity = activity;

        googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mFusedLocationClient = null;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(context, "Permission Location is not granted. Please go in android parameters -> Apps -> Project_Live_ATP -> Permissions -> Turn on Location", Toast.LENGTH_LONG).show();
        }else {
            // Récupère le locationManager qui gère la localisation
            LocationManager locManager;
            locManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            // Test si le gps est activé ou non
            if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                // on lance notre activity (qui est une dialog)
                Intent localIntent = new Intent(context, PermissionGps.class);
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(localIntent);
            }

            //Ensuite on demande a ecouter la localisation (dans la classe qui implémente le LocationListener
            this.minute = 1;
            this.metre = 1;
            if (locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, this.minute, this.metre, this);
            } else {
                locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, this.minute, this.metre, this);
            }
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context); //myLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            Toast.makeText(context, "Permission Location is granted", Toast.LENGTH_SHORT).show();
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            notification();
                        }
                    }
                });
        }
    }

    @Override
    public void onConnectionSuspended(int i)
    {
        Toast.makeText(context, "The Service of Geolocation is disable ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        int error = connectionResult.getErrorCode();
        switch (error)
        {
            case ConnectionResult.API_UNAVAILABLE :
            {
                Log.i(LOG_NAME,"Error code GMS it is : "+error);
                resolveError(connectionResult,error);
                break;
            }
            case ConnectionResult.SERVICE_DISABLED :
            {
                Log.i(LOG_NAME,"Error code GMS it is : "+error);
                resolveError(connectionResult,error);
                break;
            }
            case ConnectionResult.SERVICE_MISSING :
            {
                Log.i(LOG_NAME,"Error code GMS it is : "+error);
                resolveError(connectionResult,error);
                break;
            }
            case ConnectionResult.DEVELOPER_ERROR :
            {
                Log.i(LOG_NAME,"Error code GMS it is : "+error);
                resolveError(connectionResult,error);
                break;
            }
            case ConnectionResult.NETWORK_ERROR :
            {
                Log.i(LOG_NAME,"Error code GMS it is : "+error);
                resolveError(connectionResult,error);
                break;
            }
        }
    }

    private void resolveError(ConnectionResult connectionResult, int error)
    {
        if(!connectionResult.hasResolution())
        {
            GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
            int ERROR_GMS_API = 100;
            googleApiAvailability.getErrorDialog(activity,error, ERROR_GMS_API).show();
        }
        else
        {
            try
            {
                int RESOLE_GMS_API = 200;
                connectionResult.startResolutionForResult(activity, RESOLE_GMS_API);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    void stopGMS()
    {
        googleApiClient.disconnect();
    }

    void startGMS()
    {
        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        myLocation = location;
        if (myLocation != null) {
            latitude = myLocation.getLatitude();
            longitude = myLocation.getLongitude();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    double getLatitude() {
        if(myLocation != null){
            latitude = myLocation.getLatitude();
        }
        return latitude;
    }

    double getLongitude() {
        if(myLocation != null){
            longitude = myLocation.getLongitude();
        }
        return longitude;
    }
}
