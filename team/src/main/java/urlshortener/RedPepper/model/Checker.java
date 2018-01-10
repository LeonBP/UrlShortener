package urlshortener.RedPepper.model;

/**
 * Created by ivansantamaria on 19/12/17.
 */
public class Checker {

    String url;
    int tipoValidacion;

    public Checker(String url) {
        this.url = url;
    }

    public IpGeoResults cityUrl(String url) {

        IpGeoResults ipGeoResults = new IpGeoResults();

        ipGeoResults.setCity("zaragoza");

        ipGeoResults.setLatitude(41.39);

        ipGeoResults.setLongitude(0.52);
        return ipGeoResults;
    }
}
