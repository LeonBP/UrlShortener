package urlshortener.RedPepper.DBConnection;

import org.apache.http.HttpResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.client.RestTemplate;
import urlshortener.RedPepper.model.City;

public class DBOperations {

    public static boolean addURL(City newCity,String url,String geohash){
        DBUrl newUrl = new DBUrl(url,geohash,newCity.getLat(),newCity.getLng());
        RestTemplate add = new RestTemplate();
        try {
            ResponseEntity response = add.postForEntity("http://localhost:3000/api/urls/", newUrl, DBUrl.class);
        }catch (HttpMessageNotReadableException e){}
        return true;
    }

    public static String getURL(String geohash){
        DBUrl[] DBresult;
        RestTemplate getWithGeo = new RestTemplate();
        DBresult= getWithGeo.getForObject("http://localhost:3000/api/urls/"+geohash,DBUrl[].class);
        return DBresult[0].getUrl();
    }

    public static boolean deleteByHash(String geohash){
        RestTemplate delete = new RestTemplate();
        delete.delete("http://localhost:3000/api/urls/"+geohash);
        return  true;
    }
}
