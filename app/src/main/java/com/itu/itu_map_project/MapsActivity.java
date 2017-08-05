package com.itu.itu_map_project;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.util.Pair;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.PolyUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Timer;

public class MapsActivity extends FragmentActivity implements LocationListener
{
    private GoogleMap mMap;
    PolygonOptions ituArea;
    SQLiteDatabase localdb;
    EditText editTextFromDate;
    Button search;
    Timer timer;
    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_maps);

        localdb = openOrCreateDatabase("localdb",MODE_PRIVATE,null);
        localdb.execSQL("CREATE TABLE IF NOT EXISTS position(imei NVARCHAR(100), latitude NVARCHAR(100),longitude NVARCHAR(100), timestamp DATETIME, out NVARCHAR(1));");
        localdb.execSQL("DELETE FROM position WHERE timestamp <= date('now','-2 day');");   // to keep data in last day on user phone

        setUpMapIfNeeded();
        this.mMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        this.mMap.setMyLocationEnabled(true);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);

        locationManager.requestLocationUpdates(provider, 20000, 0, this);

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String myImei = tm.getDeviceId();

        timer = new Timer();
        timer.schedule(new RunMarker(this.mMap, myImei,this), 0, 20000);

        editTextFromDate = (EditText) findViewById(R.id.editText);
        search = (Button) findViewById(R.id.button);
        DateField fromDate = new DateField(editTextFromDate,search, this, mMap, timer);
    }



    private void setUpMapIfNeeded()
    {
        if (this.mMap == null)
        {
            this.mMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
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
            this.mMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        if (this.mMap != null)
        {
            this.ituArea = new PolygonOptions().add(new LatLng(41.106655000000003D, 29.014520999999998D)).add(new LatLng(41.108691999999998D, 29.021429999999999D)).add(new LatLng(41.108967D, 29.029983999999999D)).add(new LatLng(41.111206000000003D, 29.037043000000001D)).add(new LatLng(41.099578999999999D, 29.038195999999999D)).add(new LatLng(41.097768000000002D, 29.022210000000001D)).add(new LatLng(41.106655000000003D, 29.014520999999998D)).strokeWidth(5.0F).strokeColor(-16776961).geodesic(true);
            this.mMap.addPolygon(ituArea);

            final LinkedHashMap<PolygonOptions,Pair> densityMap = new LinkedHashMap<PolygonOptions,Pair>();

            densityMap.put( new PolygonOptions().add(new LatLng(41.104940, 29.017533)).add(new LatLng(41.105251, 29.019770)).add(new LatLng(41.104369, 29.019925)).add(new LatLng(41.104232, 29.019303)).add(new LatLng(41.103755, 29.019378)).add(new LatLng(41.103545, 29.017833)).add(new LatLng(41.104940, 29.017533)).strokeWidth(3.0F).strokeColor(Color.BLACK).geodesic(true).clickable(true), Pair.create("İnşaat Fakültesi",0));
            densityMap.put( new PolygonOptions().add(new LatLng(41.104290, 29.020028)).add(new LatLng(41.104482, 29.021505)).add(new LatLng(41.103404, 29.021770)).add(new LatLng(41.103198, 29.020281)).add(new LatLng(41.104290, 29.020028)).strokeWidth(3.0F).strokeColor(Color.BLACK).geodesic(true).clickable(true), Pair.create("Kütüphane",0));
            densityMap.put( new PolygonOptions().add(new LatLng(41.104492, 29.021625)).add(new LatLng(41.104617, 29.022429)).add(new LatLng(41.103101, 29.022772)).add(new LatLng(41.102972, 29.021989)).add(new LatLng(41.104492, 29.021625)).strokeWidth(3.0F).strokeColor(Color.BLACK).geodesic(true).clickable(true), Pair.create("Yemekhane",0));
            densityMap.put( new PolygonOptions().add(new LatLng(41.102416, 29.020708)).add(new LatLng(41.102525, 29.021996)).add(new LatLng(41.101854, 29.022152)).add(new LatLng(41.101708, 29.021009)).add(new LatLng(41.101833, 29.020564)).add(new LatLng(41.102266, 29.020559)).add(new LatLng(41.102416, 29.020708)).strokeWidth(3.0F).strokeColor(Color.BLACK).geodesic(true).clickable(true), Pair.create("Uçak Uzay Bilimleri Fakültesi",0));
            densityMap.put( new PolygonOptions().add(new LatLng(41.102101, 29.022205)).add(new LatLng(41.102150, 29.022392)).add(new LatLng(41.102461, 29.023051)).add(new LatLng(41.102352, 29.023931)).add(new LatLng(41.100989, 29.024323)).add(new LatLng(41.100447, 29.023774)).add(new LatLng(41.100500, 29.022583)).add(new LatLng(41.102101, 29.022205)).strokeWidth(3.0F).strokeColor(Color.BLACK).geodesic(true).clickable(true), Pair.create("Stadyum",0));
            densityMap.put( new PolygonOptions().add(new LatLng(41.104662, 29.023917)).add(new LatLng(41.104812, 29.025129)).add(new LatLng(41.104311, 29.025258)).add(new LatLng(41.104174, 29.024094)).add(new LatLng(41.104662, 29.023917)).strokeWidth(3.0F).strokeColor(Color.BLACK).geodesic(true).clickable(true), Pair.create("Elektrik Elektronik Fakültesi",0));
            densityMap.put( new PolygonOptions().add(new LatLng(41.104128, 29.024062)).add(new LatLng(41.104279, 29.025306)).add(new LatLng(41.103572, 29.025451)).add(new LatLng(41.103394, 29.024217)).add(new LatLng(41.104128, 29.024062)).strokeWidth(3.0F).strokeColor(Color.BLACK).geodesic(true).clickable(true), Pair.create("Bilgisayar ve Bilişim Fakültesi",0));
            densityMap.put( new PolygonOptions().add(new LatLng(41.104848, 29.025299)).add(new LatLng(41.105025, 29.026757)).add(new LatLng(41.104269, 29.026891)).add(new LatLng(41.104172, 29.026542)).add(new LatLng(41.103586, 29.026698)).add(new LatLng(41.103513, 29.026381)).add(new LatLng(41.104144, 29.026209)).add(new LatLng(41.104087, 29.025533)).add(new LatLng(41.104848, 29.025299)).strokeWidth(3.0F).strokeColor(Color.BLACK).geodesic(true).clickable(true), Pair.create("Maden Fakültesi",0));
            densityMap.put( new PolygonOptions().add(new LatLng(41.104521, 29.026871)).add(new LatLng(41.104606, 29.027906)).add(new LatLng(41.103377, 29.028174)).add(new LatLng(41.103231, 29.027133)).add(new LatLng(41.104521, 29.026871)).strokeWidth(3.0F).strokeColor(Color.BLACK).geodesic(true).clickable(true), Pair.create("Kimya Metalurji Fakültesi",0));
            densityMap.put( new PolygonOptions().add(new LatLng(41.105643, 29.022831)).add(new LatLng(41.105805, 29.024298)).add(new LatLng(41.104976, 29.024491)).add(new LatLng(41.104794, 29.023013)).add(new LatLng(41.105643, 29.022831)).strokeWidth(3.0F).strokeColor(Color.BLACK).geodesic(true).clickable(true), Pair.create("Merkezi Derslik Binası",0));
            densityMap.put( new PolygonOptions().add(new LatLng(41.107237, 29.022601)).add(new LatLng(41.107342, 29.023416)).add(new LatLng(41.106869, 29.023491)).add(new LatLng(41.106756, 29.022724)).add(new LatLng(41.107237, 29.022601)).strokeWidth(3.0F).strokeColor(Color.BLACK).geodesic(true).clickable(true), Pair.create("Süleyman Demirel Kültür Merkezi",0));
            densityMap.put( new PolygonOptions().add(new LatLng(41.107188, 29.023894)).add(new LatLng(41.107248, 29.024297)).add(new LatLng(41.106904, 29.024388)).add(new LatLng(41.107106, 29.025777)).add(new LatLng(41.106633, 29.025938)).add(new LatLng(41.106649, 29.026367)).add(new LatLng(41.106394, 29.026426)).add(new LatLng(41.106135, 29.024200)).add(new LatLng(41.107188, 29.023894)).strokeWidth(3.0F).strokeColor(Color.BLACK).geodesic(true).clickable(true), Pair.create("Fen Edebiyat Fakültesi",0));
            densityMap.put( new PolygonOptions().add(new LatLng(41.102853, 29.026462)).add(new LatLng(41.103043, 29.028095)).add(new LatLng(41.102242, 29.028298)).add(new LatLng(41.102044, 29.026680)).add(new LatLng(41.102853, 29.026462)).strokeWidth(3.0F).strokeColor(Color.BLACK).geodesic(true).clickable(true), Pair.create("Gemi İnşaatı ve Deniz Bilimleri Fakültesi",0));
            densityMap.put( new PolygonOptions().add(new LatLng(41.107821, 29.021052)).add(new LatLng(41.107853, 29.021733)).add(new LatLng(41.107461, 29.021803)).add(new LatLng(41.107380, 29.021347)).add(new LatLng(41.107158, 29.021315)).add(new LatLng(41.107085, 29.021133)).add(new LatLng(41.107821, 29.021052)).strokeWidth(3.0F).strokeColor(Color.BLACK).geodesic(true).clickable(true), Pair.create("Rektörlük",0));
            List<PolygonOptions> arr = new ArrayList(densityMap.keySet());
            for(PolygonOptions x: arr)
                this.mMap.addPolygon(x);
        }
    }

    public void onLocationChanged(Location location)
    {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng newLocation = new LatLng(latitude, longitude);

        String out = "0";

        String selectQuery = "SELECT imei,latitude,longitude,datetime(timestamp,'localtime') as currentTime, out FROM position ORDER BY currentTime LIMIT 1";
        Cursor cursor = localdb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) //If there is any location info before
        {
            HashMap<String, String> lastLocation = new HashMap<String, String>();
            for(int i=0; i<cursor.getColumnCount();i++)
                lastLocation.put(cursor.getColumnName(i), cursor.getString(i));

            //If Last location is out of activation area
            if(lastLocation.get("out") == "1")
            {
                //if (true)
                if (PolyUtil.containsLocation(newLocation,ituArea.getPoints(),true))
                    Insertion(newLocation, out);
            }
            //If distance greater or equal to 20 meter between last location and new location
            //else if(true)
            else if(getDistance(newLocation, new LatLng(Double.parseDouble(lastLocation.get("latitude")),Double.parseDouble(lastLocation.get("longitude")))) >= 20)
            {
                //if(false)
                if (!PolyUtil.containsLocation(newLocation,ituArea.getPoints(),true))
                    out = "1";
                Insertion(newLocation,out);
            }
        }
        else // First location info
        {
            //if (true)
            if (PolyUtil.containsLocation(newLocation,ituArea.getPoints(),true))
                Insertion(newLocation,out);
        }
    }
    public void Insertion(LatLng localLatLng, String out){
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDate = df.format(c.getTime());

        InsertToSqlite(imei, Double.toString(localLatLng.latitude), Double.toString(localLatLng.longitude), out);
        DBTransaction.InsertToDB(imei, Double.toString(localLatLng.latitude), Double.toString(localLatLng.longitude), formattedDate, out);
    }
    public void InsertToSqlite(String imei, String latitude, String longitude, String out)
    {
        SQLiteStatement statement = localdb.compileStatement("INSERT INTO position VALUES(?,?,?,datetime(),?)");
        statement.bindString(1, imei);
        statement.bindString(2, latitude);
        statement.bindString(3, longitude);
        statement.bindString(4, out);
        statement.executeInsert();
        statement.close();
    }

    public float getDistance(LatLng loc1, LatLng loc2)
    {
        Location loca1 = new Location("");
        Location loca2 = new Location("");
        loca1.setLatitude(loc1.latitude);
        loca1.setLongitude(loc1.longitude);
        loca2.setLatitude(loc2.latitude);
        loca2.setLongitude(loc2.longitude);
        return loca1.distanceTo(loca2);
    }
    public void onProviderDisabled(String paramString)
    {
    }

    public void onProviderEnabled(String paramString)
    {
    }

    protected void onResume()
    {
        super.onResume();
        setUpMapIfNeeded();
    }

    public void onStatusChanged(String paramString, int paramInt, Bundle paramBundle)
    {
    }
}