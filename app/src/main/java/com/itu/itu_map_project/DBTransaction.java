package com.itu.itu_map_project;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;



public class DBTransaction extends MapsActivity{

     public static void InsertToDB(String imei, String latitude, String longitude, String timestamp, String out){

         GPS_DATAS gps_traces = new GPS_DATAS(imei,latitude,longitude,timestamp,out);

         new AsyncInsertGPS().execute(gps_traces);
     }

    static class AsyncGetMarks extends AsyncTask<Void, Void, JSONObject> {
        GoogleMap mapIn;
        String myImei;
        Context ctx;
        public AsyncGetMarks(GoogleMap param, String myImei, Context ctx){
            this.mapIn = param;
            this.myImei = myImei;
            this.ctx = ctx;
        }
        @Override
        protected JSONObject doInBackground(Void... params) {
            RestAPI api = new RestAPI();
            try {
                return api.getMarks();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(JSONObject result) {
            mapIn.clear();
            PolygonOptions ituArea = new PolygonOptions().add(new LatLng(41.106655000000003D, 29.014520999999998D)).add(new LatLng(41.108691999999998D, 29.021429999999999D)).add(new LatLng(41.108967D, 29.029983999999999D)).add(new LatLng(41.111206000000003D, 29.037043000000001D)).add(new LatLng(41.099578999999999D, 29.038195999999999D)).add(new LatLng(41.097768000000002D, 29.022210000000001D)).add(new LatLng(41.106655000000003D, 29.014520999999998D)).strokeWidth(5.0F).strokeColor(-16776961).geodesic(true);
            mapIn.addPolygon(ituArea);

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

            try{
                for (int i = 0; i < result.names().length(); i++) {
                    String lat = null;
                    String lon = null;
                    String imei = null;
                    try {
                        lat = (String) result.getJSONObject(result.names().getString(i)).get("latitude");
                        lon = (String) result.getJSONObject(result.names().getString(i)).get("longitude");
                        imei = (String) result.getJSONObject(result.names().getString(i)).get("imei");

                        if(imei.equals(myImei))
                            mapIn.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lon)))
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                        List<PolygonOptions> arr = new ArrayList(densityMap.keySet());
                        for(PolygonOptions x: arr){
                            if(PolyUtil.containsLocation(Double.parseDouble(lat),Double.parseDouble(lon),x.getPoints(),true)){
                                densityMap.put(x,Pair.create(densityMap.get(x).first,(Integer)densityMap.get(x).second + 1));
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            List<PolygonOptions> arr = new ArrayList(densityMap.keySet());
            for(PolygonOptions x: arr){
                int clr = Color.argb(128, 0, 255, 0);
                if(((Integer)densityMap.get(x).second) == 0) clr = Color.argb(128, 0, 255, 0);
                else if(((Integer)densityMap.get(x).second) == 1) clr = Color.argb(128, 128, 255, 0);
                else if(((Integer)densityMap.get(x).second) == 2) clr = Color.argb(128, 255, 255, 0);
                else if(((Integer)densityMap.get(x).second) == 3) clr = Color.argb(128, 255, 128, 0);
                else if(((Integer)densityMap.get(x).second) > 3) clr = Color.argb(128, 255, 0, 0);
                mapIn.addPolygon(x).setFillColor(clr);
            }

            mapIn.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener(){
                public void onPolygonClick(Polygon polygon){
                    android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(ctx);
                    List<PolygonOptions> arr = new ArrayList(densityMap.keySet());
                    for(PolygonOptions x: arr){
                        if(x.getPoints().equals(polygon.getPoints())) {
                            builder1.setTitle(densityMap.get(x).first.toString());
                            builder1.setMessage("Şu anda burada " + densityMap.get(x).second.toString() + " kullanıcı var.");
                        }
                    }
                    builder1.setCancelable(true);

                    builder1.setPositiveButton("Tamam",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    android.app.AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            });
        }
    }

    static class AsyncGetOwnRoutes extends AsyncTask<String, Void, JSONObject> {
        GoogleMap mapIn;
        public AsyncGetOwnRoutes(GoogleMap param){
            this.mapIn = param;
        }
        @Override
        protected JSONObject doInBackground(String... params) {
            RestAPI api = new RestAPI();
            try {
                return api.getOwnRoutes(params[0],params[1],params[2]);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(JSONObject result) {
            mapIn.clear();
            PolylineOptions localPolylineOptions = new PolylineOptions().add(new LatLng(41.106655000000003D, 29.014520999999998D)).add(new LatLng(41.108691999999998D, 29.021429999999999D)).add(new LatLng(41.108967D, 29.029983999999999D)).add(new LatLng(41.111206000000003D, 29.037043000000001D)).add(new LatLng(41.099578999999999D, 29.038195999999999D)).add(new LatLng(41.097768000000002D, 29.022210000000001D)).add(new LatLng(41.106655000000003D, 29.014520999999998D)).width(5.0F).color(-16776961).geodesic(true);
            mapIn.addPolyline(localPolylineOptions);
            LinkedHashMap<String,JSONObject> map = new LinkedHashMap<>();
            List<PolylineOptions> routes = new ArrayList<PolylineOptions>();
            PolylineOptions route = new PolylineOptions();
            try{
                Iterator<String> iterator = result.keys();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    JSONObject value = result.getJSONObject(key);
                    map.put(key,value);
                }
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                Map<String, JSONObject> treeMap = new TreeMap<String, JSONObject>(map);
                List<String> nodes = new ArrayList<String>(treeMap.keySet());
                //ListIterator<String> it = (ListIterator<String>) treeMap.keySet().iterator();
                //for (Map.Entry<String, JSONObject> entry : treeMap.entrySet()) {
                //while (it.hasNext()){
                for (int i=0; i<nodes.size(); i++ ){
                    String key = nodes.get(i);
                    JSONObject value = treeMap.get(key);
                    String lat = null;
                    String lon = null;
                    String out = null;
                    Date dtime = null;
                    Date nextTime = null;

                    try {
                        lat = (String) value.get("latitude");
                        lon = (String) value.get("longitude");
                        out = (String) value.get("out");
                        dtime = format.parse((String) value.get("timestamp"));

                        if(out.equals("0")) {
                            LatLng point = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));

                            if(i+1 < nodes.size()) {
                                String nextKey = nodes.get(i+1);
                                nextTime = format.parse((String)treeMap.get(nextKey).get("timestamp"));
                            }
                            else{////////
                                nextTime = Calendar.getInstance().getTime();
                            }

                            long diff = nextTime.getTime() - dtime.getTime();
                            long seconds = TimeUnit.MILLISECONDS.toSeconds(diff)%60;
                            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff)%60;
                            long hours = TimeUnit.MILLISECONDS.toHours(diff);

                            mapIn.addMarker(new MarkerOptions().position(point).title(i+1 < nodes.size() ? "You were here for" : "You are here for")
                                    .snippet(hours + " hours "+ minutes + " minutes " + seconds+ " seconds")
                                    .icon(BitmapDescriptorFactory.defaultMarker(route.getPoints().size() > 0 ? BitmapDescriptorFactory.HUE_RED : BitmapDescriptorFactory.HUE_GREEN)));

                            route.add(point);
                        }
                        else{
                            routes.add(route);
                            route = new PolylineOptions();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                routes.add(route);
                for (PolylineOptions r:routes) {
                    mapIn.addPolyline(r);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public interface AsyncArrayResponse {
        String[] gateNameArray();
        String[] gateLatArray();
        String[] gateLongArray();
    }

    static class AsyncGetRoutes extends AsyncTask<Integer, Void, JSONObject> {
        public AsyncArrayResponse delegate = null;
        GoogleMap mapIn;
        int gatePosition;
        public AsyncGetRoutes(GoogleMap param){
            this.mapIn = param;
        }
        @Override
        protected JSONObject doInBackground(Integer... params) {
            this.gatePosition = params[0];
            RestAPI api = new RestAPI();
            try {
                return api.getRoutes();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(JSONObject result) {
            mapIn.clear();
            PolylineOptions localPolylineOptions = new PolylineOptions().add(new LatLng(41.106655000000003D, 29.014520999999998D)).add(new LatLng(41.108691999999998D, 29.021429999999999D)).add(new LatLng(41.108967D, 29.029983999999999D)).add(new LatLng(41.111206000000003D, 29.037043000000001D)).add(new LatLng(41.099578999999999D, 29.038195999999999D)).add(new LatLng(41.097768000000002D, 29.022210000000001D)).add(new LatLng(41.106655000000003D, 29.014520999999998D)).width(5.0F).color(-16776961).geodesic(true);
            mapIn.addPolyline(localPolylineOptions);
            LinkedHashMap<String, LinkedHashMap<String,JSONObject>> imeiMap = new LinkedHashMap<>();
            List<PolylineOptions> routes = new ArrayList<PolylineOptions>();
            try{
                Iterator<String> imeiIterator = result.keys();
                while (imeiIterator.hasNext())
                {
                    String imei = imeiIterator.next();
                    JSONObject imeiValue = result.getJSONObject(imei);
                    Iterator<String> iterator = imeiValue.keys();
                    LinkedHashMap<String,JSONObject> map = new LinkedHashMap<>();
                    while (iterator.hasNext()) {
                        String id = iterator.next();
                        JSONObject value = imeiValue.getJSONObject(id);
                        map.put(id, value);
                    }
                    imeiMap.put(imei,map);
                }


                //SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

                for (String entry : imeiMap.keySet()) {
                    Map<String, JSONObject> treeMap = new TreeMap<String, JSONObject>(imeiMap.get(entry));
                    List<String> nodes = new ArrayList<String>(treeMap.keySet());
                    PolylineOptions route = new PolylineOptions();
                    //ListIterator<String> it = (ListIterator<String>) treeMap.keySet().iterator();
                    //for (Map.Entry<String, JSONObject> entry : treeMap.entrySet()) {
                    //while (it.hasNext()){
                    for (int i = 0; i < nodes.size(); i++) {
                        String key = nodes.get(i);
                        JSONObject value = treeMap.get(key);
                        String lat = null;
                        String lon = null;
                        String out = null;
                        //Date dtime = null;
                        //Date nextTime = null;

                        try {
                            lat = (String) value.get("latitude");
                            lon = (String) value.get("longitude");
                            out = (String) value.get("out");
                            //dtime = format.parse((String) value.get("timestamp"));

                            if (out.equals("0")) {

                                LatLng point = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));

                                /*if (i + 1 < nodes.size()) {
                                    String nextKey = nodes.get(i + 1);
                                    nextTime = format.parse((String) treeMap.get(nextKey).get("timestamp"));
                                } else {////////
                                    nextTime = Calendar.getInstance().getTime();
                                }

                                long diff = nextTime.getTime() - dtime.getTime();
                                long seconds = TimeUnit.MILLISECONDS.toSeconds(diff) % 60;
                                long minutes = TimeUnit.MILLISECONDS.toMinutes(diff) % 60;
                                long hours = TimeUnit.MILLISECONDS.toHours(diff);

                                mapIn.addMarker(new MarkerOptions().position(point).title(i + 1 < nodes.size() ? "User was here for" : "User is here for")
                                        .snippet(hours + " hours " + minutes + " minutes " + seconds + " seconds")
                                        .icon(BitmapDescriptorFactory.defaultMarker(route.getPoints().size() > 0 ? BitmapDescriptorFactory.HUE_RED : BitmapDescriptorFactory.HUE_GREEN)));
                                */
                                route.add(point);
                            } else {
                                routes.add(route);
                                route = new PolylineOptions();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    routes.add(route);
                }

                for (PolylineOptions r:routes) {
                    int count=0;
                    boolean skip=false;
                    for(LatLng pt:r.getPoints())
                    {
                        if (count==0 && !isClosestGate(r.getPoints().get(0))){
                            skip =true;
                            break;
                        }
                        if(count==0){
                            mapIn.addMarker(new MarkerOptions().position(pt)
                                .icon(BitmapDescriptorFactory.defaultMarker(count > 0 ? BitmapDescriptorFactory.HUE_RED : BitmapDescriptorFactory.HUE_GREEN)));
                            break;
                        }

                        count++;
                    }

                    if(skip) continue;
                    mapIn.addPolyline(r);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        public boolean isClosestGate(LatLng point){
            int gate=Integer.MAX_VALUE;
            float minDistance=Float.MAX_VALUE;

            Location loca1 = new Location("");

            loca1.setLatitude(point.latitude);
            loca1.setLongitude(point.longitude);

            int count=0;
            for(String iterator:delegate.gateNameArray()){
                Location loca2 = new Location("");
                loca2.setLatitude(Double.parseDouble(delegate.gateLatArray()[count]));
                loca2.setLongitude(Double.parseDouble(delegate.gateLongArray()[count]));
                float distance = loca1.distanceTo(loca2);
                if(distance <= minDistance){
                    gate = count;
                    minDistance = distance;
                }
                count++;
            }

            if(gate == this.gatePosition) return true;
            else return false;

        }
    }

    public interface AsyncLoginResponse {
        void processFinish(boolean output);
    }

    static class AsyncLogin extends AsyncTask<String, Void, JSONObject> {
        public AsyncLoginResponse delegate = null;
        @Override
        protected JSONObject doInBackground(String... params) {
            RestAPI api = new RestAPI();
            try {
                return api.login(params[0],params[1]);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(JSONObject result) {
            try{
                if(result.getString("status").equals("successful")) {
                    delegate.processFinish(true);
                }
                else
                    delegate.processFinish(false);

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    protected static class AsyncInsertGPS extends AsyncTask<GPS_DATAS, Void, Void> {

        @Override
        protected Void doInBackground(GPS_DATAS... params) {

            RestAPI api = new RestAPI();
            try {
                api.InsertGPSdatas(params[0].getImei(),
                        params[0].getLatitude(), params[0].getLongitude(),
                        params[0].getTimestamp(), params[0].getOut());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncInsertGPS", e.getMessage());
            }
            return null;
        }
    }

    static class AsyncSendFirebase extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            RestAPI api = new RestAPI();
            try {
                api.notification(params[0]);
            } catch (Exception e) {
                // TODO Auto-generated catch block
            }
            return null;
        }
        protected void onPostExecute(JSONObject result) {
            JSONObject res;
            try{
                res = result;
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
