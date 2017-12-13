package urlshortener.RedPepper.controllers;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.swagger.annotations.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.spring.web.json.Json;
import urlshortener.RedPepper.model.PinPointParameters;
import urlshortener.RedPepper.model.Error;
import urlshortener.RedPepper.web.UrlShortenerControllerWithLogs;


import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.*;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-21T17:50:33.590Z")

@RestController
public class PinpointApiController implements PinpointApi {
    private static final Logger logger = LoggerFactory.getLogger(PinpointApiController.class);
    public ResponseEntity<String> pinpointGet(HttpServletRequest request, @RequestBody PinPointParameters jsonParam) {
        // do some magic!

        logger.info("Recibido: "+jsonParam);
        String ip = request.getRemoteAddr();
        String ip2 = request.getLocalAddr();
        logger.info("IP: "+ip);
        logger.info("IP local: "+ip2);
        Map location = getLocation(ip);
        logger.info(location.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper json = new ObjectMapper();
        String jsonResult = new String() ;
        try {
            jsonResult = json.writeValueAsString(location);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<String>(jsonResult,headers , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(badIP(location)){
            Error badIP = new Error();
            badIP.setCode(404);
            badIP.setMessage("IP not meant for geolocation");
            return new ResponseEntity(badIP,headers , HttpStatus.NOT_FOUND);
        }
        ResponseEntity<String> response = new ResponseEntity<String>(jsonResult,headers , HttpStatus.OK);

        return response;
    }

//    Cuerpo final de la respuesta en json
//    TODO: lista de urls cercanas dado el radio y resultNumber
//    TODO: Si el radio es 0, pasar solo el primer resultado
//    TODO: Links a los recursos generados de kml,gml u osm
    @ResponseBody
    private Map urlCercanas(int radio,int latitude, int longitude){
        Map res = new HashMap();
        if (radio == 0){
        }
        return res;
    }

    private Map getLocation(String ip){

        String ulrLocation = "https://freegeoip.net/json/"+ip;
        RestTemplate req = new RestTemplate();
        Map apiResponse = req.getForObject(ulrLocation,Map.class);
        Map location = new LinkedHashMap();
        location.put("ip",apiResponse.get("ip"));
        location.put("city",apiResponse.get("city"));
        location.put("latitude",apiResponse.get("latitude"));
        location.put("longitude",apiResponse.get("longitude"));
        return location;
    }

    private boolean badIP (Map location){
        return location.get("city").equals("");
    }
}
