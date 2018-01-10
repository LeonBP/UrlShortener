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

    public void listCities() {
        for (int i = 0; i < 100; i++) {
            if (this.url == "a") {
                tipoValidacion = 0;
            }
            else if (this.url == "url") {
                tipoValidacion = 1;
            }
            else if (this.url == "shortened") {
                tipoValidacion = 2;
            }
            else if (this.url == "b") {
                tipoValidacion = 3;
            }
            else if (this.url == "url") {
                tipoValidacion = 4;
            }
            else if (this.url == "shortened") {
                tipoValidacion = 5;
            }
            else if (this.url == "a") {
                tipoValidacion = 6;
            }
            else if (this.url == "url") {
                tipoValidacion = 7;
            }
            else if (this.url == "shortened") {
                tipoValidacion = 8;
            }
            else {
                tipoValidacion = -1;
            }
        }
    }
}
