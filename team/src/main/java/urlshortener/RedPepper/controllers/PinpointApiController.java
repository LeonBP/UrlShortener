package urlshortener.RedPepper.controllers;


import com.github.davidmoten.geo.GeoHash;
import feign.Feign;
import feign.RequestLine;
import feign.jackson.JacksonDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import urlshortener.RedPepper.DBConnection.DBOperations;
import urlshortener.RedPepper.DBConnection.DBUrl;
import urlshortener.RedPepper.ExceptionHandlers.ApiException;
import urlshortener.RedPepper.ExceptionHandlers.NotFoundException;
import urlshortener.RedPepper.feignClients.IpGeoClient;
import urlshortener.RedPepper.model.IpGeoResults;
import urlshortener.RedPepper.model.PinPointParameters;
import urlshortener.RedPepper.model.PinpointResult;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-21T17:50:33.590Z")

@RestController
public class PinpointApiController implements PinpointApi {
    private static final Logger logger = LoggerFactory.getLogger(PinpointApiController.class);

    public PinpointResult pinpointGet(HttpServletRequest request, @RequestBody PinPointParameters jsonParam)
            throws ApiException {

        logger.info("Recibido: " + jsonParam);
        String ip = request.getRemoteAddr();
        String ip2 = request.getLocalAddr();
        logger.info("IP: " + ip);
        logger.info("IP local: " + ip2);
        IpGeoResults location = IpGeoClient.getLocation(ip);
        logger.info(location.toString());
        DBUrl nearbyUrl = urlCercanas(jsonParam.getRadio(), location.getLatitude(), location.getLongitude());
        PinpointResult result = new PinpointResult(ip, location.getCity(), nearbyUrl.getLatitud(),
                nearbyUrl.getLongitud(), nearbyUrl.getUrlAcortada(), nearbyUrl.getUrl());
        return result;
    }

    //    Cuerpo final de la respuesta en json
//    TODO: lista de urls cercanas dado el radio y resultNumber
//    TODO: Si el radio es 0, pasar solo el primer resultado
//    TODO: Links a los recursos generados de kml,gml u osm
    private DBUrl urlCercanas(int radio, double latitude, double longitude) throws NotFoundException {
        DBUrl res = new DBUrl();
        if (radio == 0) {
            String hash = GeoHash.encodeHash(latitude, longitude, 5);
            List<String> neighbours = GeoHash.neighbours(hash);
            neighbours.add(hash);
            logger.info("hahshehs: " + String.valueOf(neighbours.get(0)));
            logger.info("hahshehs: " + String.valueOf(neighbours.get(1)));
            logger.info("hahshehs: " + String.valueOf(neighbours.get(2)));
            logger.info("hahshehs: " + String.valueOf(neighbours.get(3)));
            logger.info("hahshehs: " + String.valueOf(neighbours.get(4)));
            logger.info("hahshehs: " + String.valueOf(neighbours.get(5)));
            logger.info("hahshehs: " + String.valueOf(neighbours.get(6)));
            logger.info("hahshehs: " + String.valueOf(neighbours.get(7)));
            logger.info("hahshehs: " + String.valueOf(neighbours.get(8)));
            List<DBUrl> nearby = DBOperations.getNearbyHash(neighbours);
            if (nearby.isEmpty()) {
                throw new NotFoundException("No results in query", 3);
            } else {
                return nearby.get(0);
            }
        }
        return res;
    }

}

//TODO: Controller correcto, usando las opciones de RESTController, trabajar JSON como objetos.