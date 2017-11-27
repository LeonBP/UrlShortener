package urlshortener.RedPepper.controllers;

import io.swagger.model.Config;
import io.swagger.model.Error;
import org.springframework.core.io.Resource;

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
public class DefaultApiController implements DefaultApi {



    public ResponseEntity<Resource> rootGet() {
        // do some magic!
        return new ResponseEntity<Resource>(HttpStatus.OK);
    }

    public ResponseEntity<String> rootPost( @NotNull@ApiParam(value = "URL to be shortened", required = true) @RequestParam(value = "url", required = true) String url,
        @ApiParam(value = "Configuraion of the geohash generator" ,required=true )  @Valid @RequestBody Config mode) {
        // do some magic!
        return new ResponseEntity<String>(HttpStatus.OK);
    }

}
