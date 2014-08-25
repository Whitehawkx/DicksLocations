
package com.jonathansteadman.dickslocations;

import com.google.android.gms.maps.model.LatLng;

public class Dicks {
    private String title;

    private String desc;

    private int image;

    private double lat;

    private double lng;

    public Dicks(String title, String desc, int image, double lat, double lng) {
        this.title = title;
        this.desc = desc;
        this.image = image;
        this.lat = lat;
        this.lng = lng;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public int getImage() {
        return image;
    }

    public LatLng getLocation() {
        return new LatLng(lat, lng);
    }

    public String toString() {
        return title;
    }
}
