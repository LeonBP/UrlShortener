package urlshortener.RedPepper.model;

public class IpGeoResults {

    public String getIp() {

        return ip;
    }

    public void setIp(String ip) {

        this.ip = ip;
    }

    public String getCity() {

        return city;
    }

    public void setCity(String ctiy) {

        this.city = ctiy;
    }

    public Double getLatitude() {

        return latitude;
    }

    public void setLatitude(Double latitude) {

        this.latitude = latitude;
    }

    public Double getLongitude() {

        return longitude;
    }

    public void setLongitude(Double longitude) {

        this.longitude = longitude;
    }

    private String ip;

    private String city;

    private Double latitude;

    private Double longitude;


}
