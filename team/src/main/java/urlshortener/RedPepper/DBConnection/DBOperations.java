package urlshortener.RedPepper.DBConnection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.client.RestTemplate;
import urlshortener.RedPepper.ExceptionHandlers.NotFoundException;
import urlshortener.RedPepper.model.Checker;
import urlshortener.RedPepper.model.City;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBOperations {

    //to check if the city is repeated
    private static Checker checker;

    public static boolean addURL(City newCity, String url, String geohash) {
        DBUrl newUrl = new DBUrl(url, geohash, newCity.getLat(), newCity.getLng());
        RestTemplate add = new RestTemplate();
        ResponseEntity response = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        try {
            //here we should check if
            //the hash is repeated
            response = add.postForEntity("http://localhost:3000/api/urls/", newUrl, DBUrl.class);
        } catch (HttpMessageNotReadableException e) {
        }
        if (response.getStatusCode().is2xxSuccessful()) {
            return true;
        } else {
            return false;
        }
    }

    public static String getURL(String geohash)
            throws NotFoundException {
        DBUrl[] DBresult;
        RestTemplate getWithGeo = new RestTemplate();
        DBresult = getWithGeo.getForObject("http://localhost:3000/api/urls/" + geohash, DBUrl[].class);
        if (DBresult.length == 0) {
            throw new NotFoundException("No element in Database", 1);
        }
        return DBresult[0].getUrl();
    }

    public static List<DBUrl> getNearbyHash(List<String> neighbours) {
        DBUrl[] DBresult;
        RestTemplate getWithGeo = new RestTemplate();
        ArrayList<DBUrl> list = new ArrayList();
        for (String searchHash : neighbours) {
            DBresult = getWithGeo.getForObject("http://localhost:3000/api/hash/" + searchHash,
                    DBUrl[].class);
            list.addAll(Arrays.asList(DBresult));
        }
        return list;

    }

    public static boolean deleteByHash(String geohash) {
        RestTemplate delete = new RestTemplate();
        delete.delete("http://localhost:3000/api/urls/" + geohash);
        return true;
    }
}
