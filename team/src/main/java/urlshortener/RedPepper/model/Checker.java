package urlshortener.RedPepper.model;

import org.springframework.web.client.RestTemplate;
import urlshortener.RedPepper.DBConnection.DBUrl;

/**
 * Created by ivansantamaria on 19/12/17.
 */
public class Checker {

    String url;
    int tipoValidacion;

    public Checker(String url) {
        this.url = url;
    }


    public static boolean estaRepetidaEnBD (String geohash) {
        DBUrl[] DBresult;
        RestTemplate getWithGeo = new RestTemplate();
        //petition to check if the hash is used
        DBresult= getWithGeo.getForObject("http://localhost:3000/api/urls/"+geohash,DBUrl[].class);
        if (DBresult.length == 0){
            //not used
            return false;
        }
        else {
            //used
            return true;
        }
    }
}
