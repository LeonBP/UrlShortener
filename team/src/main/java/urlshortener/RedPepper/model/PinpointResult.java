package urlshortener.RedPepper.model;

public class PinpointResult extends IpGeoResults {
    private String hash;
    private String url;

    public PinpointResult(String ip, String city, Double latitude, Double longitude,String hash,String url) {
        super(ip,city,latitude,longitude);
        this.hash = hash;
        this.url = url;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
