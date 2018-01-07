package urlshortener.RedPepper.model;

import java.util.List;

public class GeoCitiesResult {
    public List<City> getGeonames() {
        return geonames;
    }

    public void setGeonames(List<City> geonames) {
        this.geonames = geonames;
    }

    List<City> geonames;
}
