package urlshortener.RedPepper.controllers;

import io.swagger.model.Error;

import io.swagger.annotations.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.validation.constraints.*;
import javax.validation.Valid;
//@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-21T17:50:33.590Z")

@Controller
public class GeohashApiController implements GeohashApi {



    public ResponseEntity<String> geohashGet(@ApiParam(value = "geohash that defines the shortened url",required=true ) @PathVariable("geohash") String geohash) {
        // do some magic!
        return new ResponseEntity<String>(HttpStatus.OK);
    }

}
