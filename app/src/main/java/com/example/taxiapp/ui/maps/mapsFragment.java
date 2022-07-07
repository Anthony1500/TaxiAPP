package com.example.taxiapp.ui.maps;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.taxiapp.R;

import com.example.taxiapp.databinding.FragmentHomeBinding;
import com.example.taxiapp.databinding.FragmentMapsBinding;
import com.example.taxiapp.subir_foto;
import com.example.taxiapp.ui.home.HomeViewModel;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class mapsFragment  extends SupportMapFragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        getMapAsync(this);

        return rootView;
    }



    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        try {
            if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);



            }

            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.setTrafficEnabled(true);
            mMap.setIndoorEnabled(true);
            mMap.setBuildingsEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(false);
            // Posicionar el mapa en una localización y con un nivel de zoom
            LatLng latLng = new LatLng(-1.254827, -78.631575);
            // Un zoom mayor que 13 hace que el emulador falle, pero un valor deseado para
            // callejero es 17 aprox.
            float zoom = 13;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

            // Colocar un marcador en la misma posición
            mMap.addMarker(new MarkerOptions().position(latLng));
            // Más opciones para el marcador en:
            // https://developers.google.com/maps/documentation/android/marker

            // Otras configuraciones pueden realizarse a través de UiSettings
            // UiSettings settings = getMap().getUiSettings();
        } catch (Exception e){
            e.printStackTrace();
        }







    }

}


