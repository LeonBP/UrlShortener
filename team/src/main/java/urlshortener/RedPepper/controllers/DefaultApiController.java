package urlshortener.RedPepper.controllers;

import com.spatial4j.core.io.GeohashUtils;
import feign.Feign;
import feign.RequestLine;
import feign.jackson.JacksonDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import urlshortener.RedPepper.model.City;
import urlshortener.RedPepper.model.Error;
import org.springframework.core.io.Resource;

import io.swagger.annotations.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


import javax.validation.constraints.*;
import javax.validation.Valid;
import urlshortener.RedPepper.model.Config;
import urlshortener.RedPepper.model.GeoCitiesResult;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-21T17:50:33.590Z")

@RestController
public class DefaultApiController implements DefaultApi{

    private Logger logger = LoggerFactory.getLogger(DefaultApiController.class);
    public ResponseEntity<Resource> rootGet() {
        // do some magic!
        return new ResponseEntity<Resource>(HttpStatus.OK);
    }

    public ResponseEntity<String> rootPost(
        @ApiParam(value = "Configuraion of the geohash generator" ,required=true )  @Valid @RequestBody Config mode) {
        // do some magic!
        logger.info("url: "+mode.getUrl());
        City c = GetCitiyFromGazeteer(mode);
        //TODO: add URL and geohash to database
        String hash =GeohashUtils.encodeLatLon(c.getLat(),c.getLng());
        return new ResponseEntity<String>(hash, HttpStatus.OK);
    }

    private interface GeoCitiesClient{
        @RequestLine("GET /citiesJSON?north={north}&south={south}&east={east}&west={west}&username=crzyivo")
        GeoCitiesResult getCities (@feign.Param("north") double north,@feign.Param("south") double south
        ,@feign.Param("east") double east,@feign.Param("west") double west);
    }
    private City GetCitiyFromGazeteer(Config m){
        GeoCitiesClient CityClient = Feign.builder().decoder(new JacksonDecoder()).target(GeoCitiesClient.class,
                "http://api.geonames.org");
        //TODO: Randomize coordinates
        //TODO: Check city in db
        GeoCitiesResult apiresults = CityClient.getCities(44.1,-9.9,-22.4,55.2);
        City theCity = apiresults.getGeonames().get(0);
        logger.info(theCity.getName());
        return theCity;
    }
}
