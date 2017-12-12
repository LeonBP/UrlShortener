package urlshortener.RedPepper.controllers;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.swagger.annotations.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.spring.web.json.Json;
import urlshortener.RedPepper.web.UrlShortenerControllerWithLogs;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.*;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-21T17:50:33.590Z")

@RestController
public class PinpointApiController implements PinpointApi {
    private static final Logger logger = LoggerFactory.getLogger(PinpointApiController.class);
    public ResponseEntity<String> pinpointGet(HttpServletRequest request) {
        // do some magic!
        HttpServletRequest re = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String ip = re.getRemoteAddr();
        String ip2 = re.getLocalAddr();
        logger.info("IP: "+ip);
        logger.info("IP local: "+ip2);
        String ulrLocation = "https://freegeoip.net/json/"+ip;
        RestTemplate req = new RestTemplate();
        Map apiResponse = req.getForObject(ulrLocation,Map.class);
        Map location = new LinkedHashMap();
        location.put("ip",apiResponse.get("ip"));
        location.put("city",apiResponse.get("city"));
        location.put("latitude",apiResponse.get("latitude"));
        location.put("longitude",apiResponse.get("longitude"));
        logger.info(location.toString());

        ObjectMapper json = new ObjectMapper();
        String jsonResult = new String() ;
        try {
            jsonResult = json.writeValueAsString(location);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> response = new ResponseEntity<String>(jsonResult,headers , HttpStatus.OK);

        return response;
    }

}
