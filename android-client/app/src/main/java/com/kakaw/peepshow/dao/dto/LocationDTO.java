package com.kakaw.peepshow.dao.dto;

/**
 * Created by keetaekhong on 11/16/14.
 *
 * Sample JSON -
 *
     "location":{
        "coordinates": {"latitude":42.0001,"longitude":23.23123}
     }
 *
 */
public class LocationDTO {

    public Coordinate coordinates;

    public Coordinate getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinate coordinates) {
        this.coordinates = coordinates;
    }

    public static class Coordinate {
        private String latitude;
        private String longitude;

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
    }
}
