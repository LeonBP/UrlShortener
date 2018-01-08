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
}
