package com.atp.live_atp_mobile;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by cesar on 23/03/2018.
 */

public class Gps implements LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static MyLocationListener myLocationListener;

    private double latitude;
    private double longitude;
    private Context context;
    private Activity activity;
    private GoogleApiClient googleApiClient;
    private Location myLocation;

    public static final int ERROR_GMS_API = 100;
    public static final int RESOLE_GMS_API = 200;
    private static final String LOG_NAME = Gps.class.getName();

    public Gps(Context context, Activity activity)
    {
        this.context = context;
        this.activity = activity;

        googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        myLocation = null;
    }

    public void setMyLocationListener(MyLocationListener locationListener) {
        myLocationListener = locationListener;
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
        }
        else
        {
            myLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if(myLocation != null)
            {
               // Toast.makeText(context, "Permission Location is granted", Toast.LENGTH_SHORT).show();
                latitude = myLocation.getLatitude();
                longitude = myLocation.getLongitude();
                myLocationListener.onReceiveLocation(latitude, longitude);
            }
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

    public void resolveError(ConnectionResult connectionResult,int error)
    {
        if(!connectionResult.hasResolution())
        {
            GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
            googleApiAvailability.getErrorDialog(activity,error, ERROR_GMS_API).show();
        }
        else
        {
            try
            {
                connectionResult.startResolutionForResult(activity,RESOLE_GMS_API);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    public void stopGMS()
    {
        googleApiClient.disconnect();
    }

    public void startGMS()
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

    public double getLatitude() {
        if(myLocation != null){
            latitude = myLocation.getLatitude();
        }
        return latitude;
    }

    public double getLongitude() {
        if(myLocation != null){
            longitude = myLocation.getLongitude();
        }
        return longitude;
    }
}
