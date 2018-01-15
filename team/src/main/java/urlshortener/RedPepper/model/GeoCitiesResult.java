package urlshortener.RedPepper.model;

import java.util.ArrayList;
import java.util.List;

public class GeoCitiesResult {
    public List<City> getGeonames() {
        return geonames;
    }

    public void setGeonames(List<City> geonames) {
        this.geonames = geonames;
    }

    List<City> geonames = new ArrayList<>();

    public List<CityDMS> getData() {
        return data;
    }

    public void setData(List<CityDMS> data) {
        this.data = data;
    }

    List<CityDMS> data = new ArrayList<>();

    public void deleteHash(String hash){
        this.geonames.removeIf(city -> city.getHash() == hash);
    }

    public void transform(List<CityDMS> list){
        for(CityDMS DMS : list){
            try {
                geonames.add(new City(DMS.getName(),DMS.getLon(),DMS.getLat()));
            } catch (NumberFormatException e){}

        }
    }
}
