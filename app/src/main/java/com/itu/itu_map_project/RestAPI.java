/* JSON API for android appliation */
package com.itu.itu_map_project;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RestAPI {
    private final String handlerService = "http://www.grad.somee.com/Handler.aspx";
    private final String firebaseService = "https://fcm.googleapis.com/fcm/send";

    private JSONObject load(String contents) throws IOException {
        JSONObject result = new JSONObject();
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(handlerService);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("Content", contents));

		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
        try {
            result = new JSONObject(EntityUtils.toString(entity));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
    private JSONObject firebasePost(String contents) throws IOException, JSONException {
        JSONObject result = new JSONObject();

        HttpURLConnection conn = (HttpURLConnection)new URL(firebaseService).openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(60000);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "key=AIzaSyBiF-vNrii3iHLScevIMDARW-fAfb7DIMM");
        OutputStreamWriter w = new OutputStreamWriter(conn.getOutputStream());
        w.write(contents);
        w.flush();
        int responseCode=conn.getResponseCode();
        InputStream istream = conn.getInputStream();
        result.put("Result",convertStreamToString(istream));
        return result;
    }

    public JSONObject getMarks() throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "getMarks");
        String s = o.toString();
        return load(s);
    }

    public JSONObject getOwnRoutes(String imei, String mindate, String maxdate) throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "getOwnRoutes");
        p.put("imei",imei);
        p.put("mindate",mindate);
        p.put("maxdate",maxdate);
        o.put("parameters", p);
        String s = o.toString();
        return load(s);
    }

    public JSONObject getRoutes() throws Exception {
        JSONObject result = null;
        JSONObject o = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "getRoutes");
        String s = o.toString();
        return load(s);
    }

    public void InsertGPSdatas(String imei, String latitude, String longitude, String timestamp, String out) throws Exception {
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "InsertGPSdatas");
        p.put("imei",imei);
        p.put("longitude",longitude);
        p.put("latitude",latitude);
        p.put("timestamp",timestamp);
        p.put("out",out);
        o.put("parameters", p);
        String s = o.toString();
        load(s);
    }
    public JSONObject login(String username, String password) throws Exception {
        JSONObject o = new JSONObject();
        JSONObject p = new JSONObject();
        o.put("interface","RestAPI");
        o.put("method", "login");
        p.put("username",username);
        p.put("password",password);
        o.put("parameters", p);
        String s = o.toString();
        return load(s);
    }
    public JSONObject notification(String message) throws Exception {
        JSONObject p = new JSONObject();
        JSONObject o = new JSONObject();
        o.put("text",message);
        p.put("data", o);
        p.put("to", "/topics/news");
        String s = p.toString();
        return firebasePost(s);
    }
}


