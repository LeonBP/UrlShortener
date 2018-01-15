package urlshortener.RedPepper.DBConnection;

public class DBUrl {

    public DBUrl(String url, String urlAcortada, double latitud, double longitud) {
        this.url = url;
        this.urlAcortada = urlAcortada;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public DBUrl() {
        this.url = "";
        this.urlAcortada = "";
        this.clicks = 0;
        this.latitud = 0;
        this.longitud = 0;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlAcortada() {
        return urlAcortada;
    }

    public void setUrlAcortada(String urlAcortada) {
        this.urlAcortada = urlAcortada;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    private String url;
    private String urlAcortada;
    private int clicks = 0;
    private double latitud;
    private double longitud;
}
