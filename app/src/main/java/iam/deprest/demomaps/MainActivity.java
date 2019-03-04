package iam.deprest.demomaps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.security.Permission;

import iam.deprest.demomaps.model.Cordal;
import iam.deprest.demomaps.model.CordalDAO;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnPolygonClickListener, GoogleMap.OnMarkerClickListener {

    //constante voor bij permission
    private final int REQUEST_LOCATION = 1;

    private final LatLng BRUSSEL = new LatLng(50.848712, 4.347446);
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment supportMapFragment = SupportMapFragment.newInstance();
        supportMapFragment.getMapAsync(this);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_fragment_container, supportMapFragment).commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnPolygonClickListener(this);
        map.setOnMarkerClickListener(this);
        setUpCamera();
        addMarkers();
        startLocationUpdates();
    }

    private void setUpCamera() {
        //update 1, show map
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(BRUSSEL, 8);
        map.animateCamera(update);
        //update 2, show map, zoom in to see 3D buildings, change angle
        CameraPosition.Builder positionBuilder = new CameraPosition.Builder();
        CameraPosition position = positionBuilder.target(BRUSSEL).zoom(18).tilt(60).build(); //.bearing(180) kan je toevoegen om je kijkrichting te veranderen. Default is dat naar het noorden
        CameraUpdate update2 = CameraUpdateFactory.newCameraPosition(position);
        map.animateCamera(update2);
    }

    private void addMarkers() {
        map.addMarker(new MarkerOptions()
                .position(BRUSSEL)
                .title("Bruxelles, ma belle")
                .snippet("Brussel stinkt")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        map.addMarker(new MarkerOptions()
                .position(new LatLng(50.848665, 4.349755))
                .title("Cementsculptuur")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_art))
                .snippet("Isaac Cordal"));

        map.addPolygon(new PolygonOptions().add(new LatLng(50.810094, 4.934319), new LatLng(50.993622, 4.834812), new LatLng(50.986376, 5.050556)).strokeColor(0xff00000).fillColor(0xff75315A).clickable(true));

        //vanuit datasource
        for (Cordal kunstwerk : CordalDAO.getInstance().getKunstwerkenList()){
            float kleur = 0; //default kleur = rood
            switch (kunstwerk.getReview()){
                case MOOIST:kleur=BitmapDescriptorFactory.HUE_GREEN; break;
                case MOOI:kleur=BitmapDescriptorFactory.HUE_CYAN; break;
                case GEWOON:kleur=BitmapDescriptorFactory.HUE_YELLOW; break;
                case MINDER_MOOI:kleur=BitmapDescriptorFactory.HUE_ROSE; break;
                case NIET_MOOI:kleur=BitmapDescriptorFactory.HUE_VIOLET; break;
                case LELIJK:kleur=BitmapDescriptorFactory.HUE_MAGENTA; break;
            }
            map.addMarker(new MarkerOptions().title(kunstwerk.getTitel()).snippet(kunstwerk.getSubtitel()).icon(BitmapDescriptorFactory.defaultMarker(kleur)).position(kunstwerk.getCoördinaten()));
        }
    }

    private void startLocationUpdates() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){ //ouder dan M hoefden we geen extra code gebruiken, was aanpassing in manifest genoeg. M of jongere versie heeft nog code nodig hier, om permissie te controleren:
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

                //Geen toestemming om locatie te gebruiken. => nog eens toestemming vragen
                String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION}; //je kan ook meerdere permissies tegelijk opvragen, door te scheiden met komma
                requestPermissions(permissions, REQUEST_LOCATION);

            }else{
                map.setMyLocationEnabled(true);
            }
        }else{
            map.setMyLocationEnabled(true); //DOOR FOUTMELDING VAN HIERONDER
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION){
            for (int result : grantResults)
            if (result == PackageManager.PERMISSION_GRANTED)
                map.setMyLocationEnabled(true); //foutmelding gewoon SUPPRESSEN => want we hebben net toestemming gevraagd......
        }

    }

    @Override
    public void onPolygonClick(Polygon polygon) {
        Toast.makeText(getApplicationContext(), "Marginale driehoek", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_LONG).show();
        return false;
    }
}
