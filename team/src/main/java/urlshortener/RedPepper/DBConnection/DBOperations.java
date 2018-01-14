package urlshortener.RedPepper.DBConnection;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.client.RestTemplate;
import urlshortener.RedPepper.ExceptionHandlers.NotFoundException;
import urlshortener.RedPepper.model.Checker;
import urlshortener.RedPepper.model.City;

public class DBOperations {

    private static Checker checker;

    public static boolean addURL(City newCity,String url,String geohash){
        DBUrl newUrl = new DBUrl(url,geohash,newCity.getLat(),newCity.getLng());
        RestTemplate add = new RestTemplate();
        try {
            if (!checker.estaRepetidaEnBD(geohash)) {
                ResponseEntity response = add.postForEntity("http://localhost:3000/api/urls/", newUrl, DBUrl.class);
            }
            else {
                return false;
            }
        }catch (HttpMessageNotReadableException e){}
        return true;
    }

    public static String getURL(String geohash)
            throws NotFoundException {
        DBUrl[] DBresult;
        RestTemplate getWithGeo = new RestTemplate();
        DBresult= getWithGeo.getForObject("http://localhost:3000/api/urls/"+geohash,DBUrl[].class);
        if (DBresult.length == 0){
            throw new NotFoundException("No element in Database",1);
        }
        return DBresult[0].getUrl();
    }

    public static boolean deleteByHash(String geohash){
        RestTemplate delete = new RestTemplate();
        delete.delete("http://localhost:3000/api/urls/"+geohash);
        return  true;
    }
}
