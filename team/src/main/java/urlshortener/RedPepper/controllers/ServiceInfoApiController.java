package urlshortener.RedPepper.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
//@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-21T17:50:33.590Z")

@Controller
public class ServiceInfoApiController implements ServiceInfoApi {


    public ResponseEntity<Void> serviceInfoGet() {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
