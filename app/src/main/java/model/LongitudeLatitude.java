package model;

public class LongitudeLatitude {
    private double longtitude;
    private double latitude;
    private String marker;

    public LongitudeLatitude(double latitude, double longtitude, String marker) {
        this.longtitude = longtitude;
        this.latitude = latitude;
        this.marker = marker;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }
}
