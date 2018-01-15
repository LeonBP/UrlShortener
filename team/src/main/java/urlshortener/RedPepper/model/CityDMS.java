package urlshortener.RedPepper.model;

import com.spatial4j.core.io.GeohashUtils;

public class CityDMS {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    private String lat;
    private String lon;

}
