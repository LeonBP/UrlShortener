package urlshortener.RedPepper.controllers;


import io.swagger.annotations.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.validation.constraints.*;
import javax.validation.Valid;
//@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-21T17:50:33.590Z")

@RestController
public class QRApiController implements QRApi {



    public ResponseEntity<Void> qRGet(@RequestParam("url") String url) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
    }

}
