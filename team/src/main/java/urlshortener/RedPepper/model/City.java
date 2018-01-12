package urlshortener.RedPepper.model;

public class City {

    private String name;
    private float lng;
    private float lat;

    public City() {
        this.name = "";
        this.lng = 0;
        this.lat = 0;
    }

    public City(String name, float lng, float lat) {
        this.name = name;
        this.lng = lng;
        this.lat = lat;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

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

}
