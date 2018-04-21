package wpos.com.br.googlemapsv7;


import android.location.Location;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.model.CameraPosition;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationServices;




public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;

    EditText txtLongitude, txtLatitude;
    LatLng target;
    double LatTarget, LongTarget;



    //Importado do LocationDemo
    private static final String APP_ID = "80e4eede56844462ef3cdc721208c31f";
    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 1;
    private GoogleApiClient googleApiClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        txtLatitude = (EditText) findViewById(R.id.txtLatitude);
        txtLongitude = (EditText) findViewById(R.id.txtLongitude);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION },
                    PERMISSION_ACCESS_COARSE_LOCATION);
        }

        googleApiClient = new GoogleApiClient.Builder(this, this, this).addApi(LocationServices.API).build();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }











    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }


    // Declaração da função disparada pelo clique do botão.
    public void localizar(View v) {
        // Chama setUpMap(), passando as coordenadas das caixas de texto.


        setUpMap(Double.parseDouble(txtLatitude.getText().toString()),
                Double.parseDouble(txtLongitude.getText().toString()));


    }


    private void setUpMap(double dlat, double dlong) {

        if ((dlat == 0.0) || (dlong == 0.0)) {

            target = new LatLng(LatTarget, LongTarget);


        } else {


            // Marca a latitude e longitude informada pelo usuário
            target = new LatLng(dlat, dlong);



        }


        // Posiciona o ângulo da “Câmera”
        mMap.addMarker(new MarkerOptions().position(target).title("AQUI!!"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(target));

   }




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                        double LatTarget = lastLocation.getLatitude(), LongTarget = lastLocation.getLongitude();
                        setUpMap(LatTarget, LongTarget);
                    }




                } else {
                    Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }





    @Override
    public void onConnected(Bundle bundle) {
        Log.i(MapsActivity.class.getSimpleName(), "Connected to Google Play Services!");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);


            double LatTarget = lastLocation.getLatitude(), LongTarget = lastLocation.getLongitude();

            //txtLongitude.setText(String.valueOf(LongTarget));
            //txtLatitude.setText(String.valueOf(LatTarget));

           setUpMap(LatTarget, LongTarget);



        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(MapsActivity.class.getSimpleName(), "Can't connect to Google Play Services!");
    }



}