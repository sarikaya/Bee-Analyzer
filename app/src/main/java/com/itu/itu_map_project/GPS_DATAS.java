package com.itu.itu_map_project;

/**
 * Created by Selim on 04.01.2016.
 */
public class GPS_DATAS {

    String imei,timestamp,longitude,latitude,out;

    public GPS_DATAS(String imei, String latitude, String longitude, String timestamp, String out) {
        super();
        this.imei = imei;
        this.longitude = longitude;
        this.latitude = latitude;
        this.timestamp = timestamp;
        this.out = out;
    }

    public GPS_DATAS() {
        super();
        this.imei = null;
        this.longitude = null;
        this.latitude = null;
        this.timestamp = null;
        this.out = null;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getOut() {
        return out;
    }

    public void setout(String out) {
        this.out = out;
    }
}
