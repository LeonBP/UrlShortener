package urlshortener.RedPepper.controllers;

import com.spatial4j.core.io.GeohashUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import urlshortener.RedPepper.model.Error;
import org.springframework.core.io.Resource;

import io.swagger.annotations.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


import javax.validation.constraints.*;
import javax.validation.Valid;
import urlshortener.RedPepper.model.Config;
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
        String hash =GeohashUtils.encodeLatLon(41.684,-0.891);
        return new ResponseEntity<String>(hash, HttpStatus.OK);
    }

}
