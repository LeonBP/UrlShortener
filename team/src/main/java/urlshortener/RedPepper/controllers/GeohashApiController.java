package urlshortener.RedPepper.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.RedirectView;
import urlshortener.RedPepper.DBConnection.DBOperations;
import urlshortener.RedPepper.ExceptionHandlers.NotFoundException;

import io.swagger.annotations.*;

import org.springframework.web.bind.annotation.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-21T17:50:33.590Z")

@RestController
public class GeohashApiController implements GeohashApi {

    private Logger logger = LoggerFactory.getLogger(GeohashApiController.class);

    public RedirectView geohashGet(@ApiParam(value = "geohash that defines the shortened url",required=true ) @PathVariable("geohash") String geohash)
            throws NotFoundException {
        logger.info("hash: "+geohash);
        String urlToRedirect = DBOperations.getURL(geohash);
        logger.info("url: "+urlToRedirect);
        RedirectView re = new RedirectView();
        re.setUrl(urlToRedirect);
        return re;
    }
}
