package com.mappau.apps.mulchatandroidstudio;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by guru on 22.04.2015.
 */
public class Location {
    String desc;
    double lat;
    double lon;

    public Location(double lat, double lon, String desc) {
        this(lat,lon);
        this.desc = desc;
    }

    public Location(double lat, double lon) {
        super();
        this.lat = lat;
        this.lon = lon;
    }

    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public double getLat() {
        return lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    public double getLon() {
        return lon;
    }
    public void setLon(double lon) {
        this.lon = lon;
    }

    public JSONObject toJSONObj() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("lat", getLat());
        obj.put("lon", getLon());
        if(getDesc() != null){
            obj.put("desc", getDesc());
        }
        return obj;
    }
}
