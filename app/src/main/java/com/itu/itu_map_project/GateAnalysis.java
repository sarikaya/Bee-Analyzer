package com.itu.itu_map_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;


public class GateAnalysis extends FragmentActivity implements DBTransaction.AsyncArrayResponse {
    private GoogleMap mMap;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gate_maps);

        setUpMapIfNeeded();
        this.mMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.gateMap)).getMap();

        gateChoosing();
    }
    private void setUpMapIfNeeded()
    {
        if (this.mMap == null)
        {
            this.mMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.gateMap)).getMap();
            if (this.mMap != null)
                setUpMap();
        }
    }

    private void setUpMap()
    {
        LatLng localLatLng = new LatLng(41.104889999999997D, 29.027756D);
        this.mMap.moveCamera(CameraUpdateFactory.newLatLng(localLatLng));
        this.mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0F));
        if (this.mMap == null)
            this.mMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.gateMap)).getMap();
        if (this.mMap != null)
        {
            PolylineOptions localPolylineOptions = new PolylineOptions().add(new LatLng(41.106655000000003D, 29.014520999999998D)).add(new LatLng(41.108691999999998D, 29.021429999999999D)).add(new LatLng(41.108967D, 29.029983999999999D)).add(new LatLng(41.111206000000003D, 29.037043000000001D)).add(new LatLng(41.099578999999999D, 29.038195999999999D)).add(new LatLng(41.097768000000002D, 29.022210000000001D)).add(new LatLng(41.106655000000003D, 29.014520999999998D)).width(5.0F).color(-16776961).geodesic(true);
            this.mMap.addPolyline(localPolylineOptions);
        }
    }
    public void gateChoosing(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog, null);
        final Spinner dropdown = (Spinner) alertLayout.findViewById(R.id.ituGate);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.itu_gate_names));
        dropdown.setAdapter(adapter);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Gates");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(true);
        /*alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });*/
        final DBTransaction.AsyncGetRoutes tryGetRoutes = new DBTransaction.AsyncGetRoutes(mMap);
        tryGetRoutes.delegate = this;
        alert.setPositiveButton("Search", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedLat = getResources().getStringArray(R.array.itu_gate_latitudes)[dropdown.getSelectedItemPosition()];
                String selectedLong = getResources().getStringArray(R.array.itu_gate_longitudes)[dropdown.getSelectedItemPosition()];
                Button gateButton = (Button)findViewById(R.id.gateButton);
                gateButton.setText(dropdown.getSelectedItem().toString());
                tryGetRoutes.execute(dropdown.getSelectedItemPosition());
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }
    public void gateButtonClick(View view){
        gateChoosing();
    }
    @Override
    public String[] gateNameArray() {
        return getResources().getStringArray(R.array.itu_gate_names);
    }
    public String[] gateLatArray() {
        return getResources().getStringArray(R.array.itu_gate_latitudes);
    }
    public String[] gateLongArray() {
        return getResources().getStringArray(R.array.itu_gate_longitudes);
    }
}
