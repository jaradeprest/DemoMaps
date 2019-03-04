package iam.deprest.demomaps.model;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Cordal implements Serializable {

    public enum Review{MOOIST, MOOI, MINDER_MOOI, GEWOON, NIET_MOOI, LELIJK}
    private LatLng coördinaten;
    private String titel;
    private String subtitel = "Isaac Cordal";
    private Review review;

    public Cordal() {
    }

    public Cordal(LatLng coördinaten, String titel, Review review) {
        this.coördinaten = coördinaten;
        this.titel = titel;
        this.review = review;
    }

    public LatLng getCoördinaten() {
        return coördinaten;
    }

    public void setCoördinaten(LatLng coördinaten) {
        this.coördinaten = coördinaten;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getSubtitel() {
        return subtitel;
    }

    public void setSubtitel(String subtitel) {
        this.subtitel = subtitel;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
