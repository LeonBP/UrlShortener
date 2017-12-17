package urlshortener.RedPepper.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.netflix.hystrix.Hystrix;
import feign.Feign;
import feign.RequestLine;
import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;
import org.jboss.logging.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import urlshortener.RedPepper.model.Error;
import urlshortener.RedPepper.model.IpGeoResults;
import urlshortener.RedPepper.model.PinPointParameters;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-21T17:50:33.590Z")

@RestController
public class PinpointApiController implements PinpointApi {
    private static final Logger logger = LoggerFactory.getLogger(PinpointApiController.class);
    public ResponseEntity pinpointGet(HttpServletRequest request, @RequestBody PinPointParameters jsonParam) {

        logger.info("Recibido: "+jsonParam);
        String ip = request.getRemoteAddr();
        String ip2 = request.getLocalAddr();
        logger.info("IP: "+ip);
        logger.info("IP local: "+ip2);
        IpGeoResults location = getLocation(ip);
        logger.info(location.toString());

        ObjectMapper json = new ObjectMapper();
        String jsonResult = new String() ;
        try {
            jsonResult = json.writeValueAsString(location);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity(jsonResult , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        ResponseEntity response = new ResponseEntity<String>(jsonResult , HttpStatus.OK);

        return response;
    }

//    Cuerpo final de la respuesta en json
//    TODO: lista de urls cercanas dado el radio y resultNumber
//    TODO: Si el radio es 0, pasar solo el primer resultado
//    TODO: Links a los recursos generados de kml,gml u osm
    private Map urlCercanas(int radio,int latitude, int longitude){
        Map res = new HashMap();
        if (radio == 0){
        }
        return res;
    }
    private interface IpGeoClient{
        @RequestLine("GET /{ip}")
        IpGeoResults IpLocalization (@feign.Param("ip") String ip);
    }
    private IpGeoResults getLocation(String ip) throws HttpClientErrorException{

        IpGeoClient ipClient = Feign.builder().decoder(new JacksonDecoder()).target(IpGeoClient.class,
                "https://freegeoip.net/json/");
        //TODO: Configurar timeouts / Tratar excepcion
        IpGeoResults apiResults = ipClient.IpLocalization(ip);
        if (apiResults.getCity().equals("")) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        return apiResults;
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity handleBadIp(){
        Error badIP = new Error();
        badIP.setCode(404);
        badIP.setMessage("IP not meant for geolocation");
        return new ResponseEntity(badIP , HttpStatus.NOT_FOUND);
    }

}

//TODO: Controller correcto, usando las opciones de RESTController, trabajar JSON como objetos.