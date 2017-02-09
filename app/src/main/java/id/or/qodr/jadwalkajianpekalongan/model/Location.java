package id.or.qodr.jadwalkajianpekalongan.model;

import android.widget.ImageView;

/**
 * Created by root on 04/02/17.
 */

public class Location {
    private String name;
    private double lat;
    private double lng;



    private String imgURL;

    public Location(String name, double lat, double lng, String img) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.imgURL = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getImg() {
        return imgURL;
    }

    public void setImg(String img) {
        this.imgURL = img;
    }
    @Override
    public String toString() {
        return name;
    }
}
