package urlshortener.RedPepper.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import urlshortener.RedPepper.model.Error;

import io.swagger.annotations.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

import javax.validation.constraints.*;
import javax.validation.Valid;
//@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-21T17:50:33.590Z")

@RestController
public class GeohashApiController implements GeohashApi {

    private Logger logger = LoggerFactory.getLogger(GeohashApiController.class);

    public RedirectView geohashGet(@ApiParam(value = "geohash that defines the shortened url",required=true ) @PathVariable("geohash") String geohash) {
        logger.info("hash: "+geohash);
        RedirectView re = new RedirectView();
        re.setUrl("http://www.google.com");
        return re;
    }
}
