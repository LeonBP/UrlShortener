package urlshortener.RedPepper.ExceptionHandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import urlshortener.RedPepper.ExceptionHandlers.NotFoundException;
import urlshortener.RedPepper.controllers.PinpointApiController;
import urlshortener.RedPepper.model.Error;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionController {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(HttpClientErrorException.class)
    public @ResponseBody Error handleBadIp(HttpServletResponse response) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        Error badIP = new Error();
        badIP.setCode(404);
        badIP.setMessage("IP not meant for geolocation");
        return badIP;
    }

    @ExceptionHandler(NotFoundException.class)
    public @ResponseBody Error handleNoRedirect(NotFoundException e,HttpServletResponse response) {
        if (e.getCode() == 1) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            Error noRedirect = new Error();
            noRedirect.setCode(404);
            noRedirect.setMessage("Your link is not located");
            return noRedirect;
        } else if (e.getCode() == -1) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            Error test = new Error();
            test.setCode(404);
            test.setMessage("Test error");
            return test;
        } else {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            Error unknown = new Error();
            unknown.setCode(999);
            unknown.setMessage("Unexpected error");
            return unknown;
        }
    }
}
