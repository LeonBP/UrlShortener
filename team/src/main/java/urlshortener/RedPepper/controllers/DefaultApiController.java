package urlshortener.RedPepper.controllers;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.spatial4j.core.io.GeohashUtils;
import feign.Feign;
import feign.Request;
import feign.RequestLine;
import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;
import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import urlshortener.RedPepper.DBConnection.DBOperations;
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
import java.util.Random;


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
        String hash =GeohashUtils.encodeLatLon(c.getLat(),c.getLng());
        boolean response=DBOperations.addURL(c,mode.getUrl(),hash);
        return new ResponseEntity<String>(hash, HttpStatus.OK);
    }

    private interface GeoCitiesClient{
        @RequestLine("GET /citiesJSON?north={north}&south={south}&east={east}&west={west}&username=crzyivo")
        GeoCitiesResult getCities (@feign.Param("north") double north,@feign.Param("south") double south
        ,@feign.Param("east") double east,@feign.Param("west") double west);
    }

    GeoCitiesClient fallback = (north,south,east,west) -> {
        logger.info("ERROR FEIGN");
        return new GeoCitiesResult();
    };

    private City GetCitiyFromGazeteer(Config m){
        GeoCitiesClient CityClient = HystrixFeign.builder()
                .decoder(new JacksonDecoder())
                .target(GeoCitiesClient.class, "http://api.geonames.org",fallback);
        ConfigurationManager.getConfigInstance()
                .setProperty("hystrix.command.GeoCitiesClient#getCities(Double,Double,Double,Double).execution.isolation.thread.timeoutInMilliseconds", 1000000);
        //TODO: Randomize coordinates
        //TODO: Check city in db
        City theCity = new City();
        if(m.getMode() == 1) {
            Random random = new Random();
            int max = 70;
            int min = -40;
            double ranN = random.nextInt(max - min + 1) + min;
            double ranS = ranN - 20;
            double ranE = random.nextInt(max - min + 1) + min;;
            double ranW = ranE + 20;
            logger.info(String.valueOf(ranN));
            logger.info(String.valueOf(ranS));
            logger.info(String.valueOf(ranW));
            logger.info(String.valueOf(ranE));
            GeoCitiesResult apiresults = CityClient.getCities(ranN, ranS, ranE, ranW);
            logger.info(String.valueOf(apiresults.getGeonames().size()));
            theCity = apiresults.getGeonames().get(0);
            logger.info(theCity.getName());
        }
        return theCity;

    }
}
