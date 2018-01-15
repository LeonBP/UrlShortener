package urlshortener.RedPepper.model;

import com.spatial4j.core.io.GeohashUtils;

public class City {

    private String name;
    private float lng;
    private float lat;
    private String hash;

    public City() {
        this.name = "";
        this.lng = 0;
        this.lat = 0;
        this.hash = "invalid";
    }

    public City(String name, float lng, float lat) {
        this.name = name;
        this.lng = lng;
        this.lat = lat;
        this.hash = GeohashUtils.encodeLatLon(lat, lng);
    }

    public City(String name, String lon, String lat) throws NumberFormatException {
        this.name = name.split(",")[0];
        this.lng = parse(lon);
        this.lat = parse(lat);
        this.hash = GeohashUtils.encodeLatLon(this.lat, this.lng);
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

    public String getHash() {
        return hash;
    }

    private float parse(String s) throws NumberFormatException {
        String[] first = s.split("°");
        int grade = Integer.parseInt(first[0]);
        int minute = 0;
        String[] second = first[1].split("′");
        if (second[0].equals("W") || second[0].equals("E")
                || second[0].equals("S") || second[0].equals("N")) {
            if (second[0].equals("S") || second[0].equals("W")) {
                return 0 - grade;
            } else {
                return grade;
            }
        } else {
            minute = Integer.parseInt(second[0]);
            float result = grade + minute / 60;
            if (second[1].equals("S") || second[1].equals("W")) {
                return 0 - result;
            } else {
                return result;
            }

        }
    }

}
