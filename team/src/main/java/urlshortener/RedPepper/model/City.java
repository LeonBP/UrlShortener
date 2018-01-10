package urlshortener.RedPepper.model;

public class City {

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    private String name;

    public float getLng() {

        return lng;
    }

    public void setLng(float lng) {

        this.lng = lng;
    }

    public float getLat() {

        return lat;
    }

    public void setLat(float lat) {

        this.lat = lat;
    }

    private float lng;

    private float lat;

}
