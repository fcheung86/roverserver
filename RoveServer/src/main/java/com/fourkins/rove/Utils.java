package com.fourkins.rove;

public class Utils {

    private static final double KM_PER_DEGREE_LATITUDE = 111.13;

    public static double[] getLocationDegreeDelta(double curLat, double curLng, double distanceKm) {
        double[] latlng = new double[] { 0, 0 };

        curLat = Math.abs(curLat);
        curLng = Math.abs(curLng);

        latlng[0] = distanceKm / KM_PER_DEGREE_LATITUDE;

        double kmPerDegreeLongitude;
        if (curLat >= 0 && curLat < 10) {
            kmPerDegreeLongitude = 111.32;
        } else if (curLat >= 10 && curLat < 20) {
            kmPerDegreeLongitude = 109.64;
        } else if (curLat >= 20 && curLat < 30) {
            kmPerDegreeLongitude = 104.65;
        } else if (curLat >= 30 && curLat < 40) {
            kmPerDegreeLongitude = 96.49;
        } else if (curLat >= 40 && curLat < 50) {
            kmPerDegreeLongitude = 85.39;
        } else if (curLat >= 50 && curLat < 60) {
            kmPerDegreeLongitude = 71.70;
        } else if (curLat >= 60 && curLat < 70) {
            kmPerDegreeLongitude = 55.80;
        } else if (curLat >= 70 && curLat < 80) {
            kmPerDegreeLongitude = 38.19;
        } else if (curLat >= 80 && curLat < 90) {
            kmPerDegreeLongitude = 19.39;
        } else {
            kmPerDegreeLongitude = 85.39;
        }

        latlng[1] = distanceKm / kmPerDegreeLongitude;

        return latlng;
    }

}
