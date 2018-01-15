package urlshortener.RedPepper.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.config.ConfigurationManager;
import com.spatial4j.core.io.GeohashUtils;
import feign.RequestLine;
import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;
import io.swagger.annotations.ApiParam;
import org.decimal4j.util.DoubleRounder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import urlshortener.RedPepper.DBConnection.DBOperations;
import urlshortener.RedPepper.ExceptionHandlers.ApiException;
import urlshortener.RedPepper.ExceptionHandlers.NotFoundException;
import urlshortener.RedPepper.feignClients.CitiesListClient;
import urlshortener.RedPepper.feignClients.GeoCitiesClient;
import urlshortener.RedPepper.feignClients.IpGeoClient;
import urlshortener.RedPepper.model.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-21T17:50:33.590Z")

@RestController
public class DefaultApiController implements DefaultApi {

    private Logger logger = LoggerFactory.getLogger(DefaultApiController.class);
    private GeoCitiesResult localCities = GetCityFromLocal();

    public ResponseEntity<String> rootPost(HttpServletRequest request,
                                           @ApiParam(value = "Configuraion of the geohash generator", required = true)
                                           @Valid @RequestBody Config mode)
            throws ApiException {
        logger.info("url: " + mode.getUrl());
        if (mode.getUrl().equals("ErrorTest")) {
            throw new NotFoundException("test", -1);
        }
        mode.setClientIP(request.getRemoteAddr());
        City c = GetCitiyFromGazeteer(mode);
        logger.info("city: " + c.getName());
        String hash = GeohashUtils.encodeLatLon(c.getLat(), c.getLng());
        boolean response = DBOperations.addURL(c, mode.getUrl(), hash);
        if (response) {
            return new ResponseEntity<String>(hash, HttpStatus.CREATED);
        } else {
            throw new ApiException(0, "Database error");
        }
    }


    private City GetCitiyFromGazeteer(Config m) throws ApiException {
        City theCity = new City();
        if (m.getMode() == 0) { // Random mode.
            Random random = new Random(System.currentTimeMillis());
            City ranCoord = randomCity();
            int maxTries = 5;
            int tryn = 0;
            boolean repetida = false;
            do {
                double ranN = ranCoord.getLat() + 1;
                double ranS = ranCoord.getLat() - 1;
                double ranW = ranCoord.getLng() - 2;
                double ranE = ranCoord.getLng() + 2;
                logger.info(String.valueOf(ranN));
                logger.info(String.valueOf(ranS));
                logger.info(String.valueOf(ranW));
                logger.info(String.valueOf(ranE));
                GeoCitiesClient cityClient = new GeoCitiesClient(localCities);
                GeoCitiesResult apiresults = cityClient.getCities(ranN, ranS,
                        ranE, ranW);
                if (apiresults.getGeonames().isEmpty()) {
                    apiresults = localCities;
                }
                logger.info(String.valueOf(localCities.getGeonames().size()));
                int randomPos = random.nextInt(apiresults.getGeonames().size());
                theCity = apiresults.getGeonames().get(randomPos);
                logger.info(theCity.getName());
                tryn++;
            } while ((repetida = Checker.estaRepetidaEnBD(theCity.getHash()))
                    && tryn < maxTries);
            if (repetida) {
                //TODO: LRU, hash liberado se asigna a la peticion
            } else { // Hash libre, lo quitamos de la lista local si esta
                localCities.deleteHash(theCity.getHash());
            }
        } else if (m.getMode() == 1) {
            IpGeoResults location = IpGeoClient.getLocation(m.getClientIP());
            double localLat = location.getLatitude();
            double localLong = location.getLongitude();
            localLat = DoubleRounder.round(localLat, 2);
            localLong = DoubleRounder.round(localLong, 2);

            double N = localLat + 0.1;
            double S = localLat - 0.1;
            double W = localLong - 0.2;
            double E = localLong + 0.2;
            GeoCitiesClient cityClient = new GeoCitiesClient(localCities);
            GeoCitiesResult apiresults = cityClient.getCities(N, S, E, W);
            boolean hayHueco = false;
            for (City c : apiresults.getGeonames()) {
                if (!Checker.estaRepetidaEnBD(c.getHash())) {
                    theCity = c;
                    hayHueco = true;
                    localCities.deleteHash(theCity.getHash());
                    break;
                }
            }
            if (!hayHueco) {
                throw new ApiException(1, "Collisions found");
            }
        } else if (m.getMode() == 2) {
            IpGeoResults location;
            try {
                location = IpGeoClient.getLocation(InetAddress.getLocalHost().getHostAddress());
            } catch (UnknownHostException e) {
                e.printStackTrace();
                throw new ApiException(2, "Bad host ip");
            }
            double localLat = location.getLatitude();
            double localLong = location.getLongitude();
            localLat = DoubleRounder.round(localLat, 2);
            localLong = DoubleRounder.round(localLong, 2);

            double N = localLat + 0.1;
            double S = localLat - 0.1;
            double W = localLong - 0.2;
            double E = localLong + 0.2;
            GeoCitiesClient cityClient = new GeoCitiesClient(localCities);
            GeoCitiesResult apiresults = cityClient.getCities(N, S, E, W);
            boolean hayHueco = false;
            for (City c : apiresults.getGeonames()) {
                if (!Checker.estaRepetidaEnBD(c.getHash())) {
                    theCity = c;
                    hayHueco = true;
                    localCities.deleteHash(theCity.getHash());
                    break;
                }
            }
            if (!hayHueco) {
                throw new ApiException(1, "Collisions found");
            }
        }
        return theCity;

    }

    private City randomCity() throws ApiException {
        GeoCitiesResult res = CitiesListClient.getList();
        GeoCitiesResult ranCityList = new GeoCitiesResult();
        ranCityList.transform(res.getData());
        Random random = new Random(System.currentTimeMillis());
        return ranCityList.getGeonames().get(random.nextInt(ranCityList.getGeonames().size()));
    }

    private GeoCitiesResult GetCityFromLocal() {
        ObjectMapper mapper = new ObjectMapper();
        logger.info("path: " + DefaultApiController.class.getName());
        InputStream jsonFile = DefaultApiController.class.getResourceAsStream("/static_cities.json");
        try {
            GeoCitiesResult localList = mapper.readValue(jsonFile, GeoCitiesResult.class);
            logger.info("File load success");
            logger.info(String.valueOf(localList.getGeonames().size()));
            return localList;
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("File load failed");
            return new GeoCitiesResult();
        }
    }

}
