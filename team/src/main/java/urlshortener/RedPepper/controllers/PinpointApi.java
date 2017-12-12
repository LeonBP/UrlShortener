/**
 * NOTE: This class is auto generated by the swagger code generator program (2.2.3).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package urlshortener.RedPepper.controllers;


import io.swagger.annotations.*;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.*;
import javax.validation.Valid;
//@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-21T17:50:33.590Z")

@Api(value = "pinpoint", description = "the pinpoint API")
public interface PinpointApi {

    @ApiOperation(value = "", notes = "Obtain the URL(s) near the client location. ", response = Void.class, tags={ "Added funcionality", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = String.class) })
    
    @RequestMapping(value = "/pinpoint",
        method = RequestMethod.GET)
    ResponseEntity<String> pinpointGet(HttpServletRequest request);

}
