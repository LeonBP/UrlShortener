package urlshortener.RedPepper.ExceptionHandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import urlshortener.RedPepper.model.Error;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionController {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(NotFoundException.class)
    public @ResponseBody
    Error handleNotFound(NotFoundException e, HttpServletResponse response) {
        switch (e.getCode()) {
            case 1:
                response.setStatus(HttpStatus.NOT_FOUND.value());
                Error noRedirect = new Error();
                noRedirect.setCode(404);
                noRedirect.setMessage("Your link is not located");
                return noRedirect;
            case 2:
                response.setStatus(HttpStatus.FORBIDDEN.value());
                Error badIP = new Error();
                badIP.setCode(403);
                badIP.setMessage("IP not meant for geolocation");
                return badIP;
            case 3:
                response.setStatus(HttpStatus.FORBIDDEN.value());
                Error noResults = new Error();
                noResults.setCode(403);
                noResults.setMessage("No links found");
                return noResults;
            case -1:
                response.setStatus(HttpStatus.NOT_FOUND.value());
                Error test = new Error();
                test.setCode(404);
                test.setMessage("Test error");
                return test;
            default:
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                Error unknown = new Error();
                unknown.setCode(999);
                unknown.setMessage("Unexpected error");
                return unknown;
        }
    }

    @ExceptionHandler(ApiException.class)
    public @ResponseBody
    Error handleApiExceptions(ApiException e, HttpServletResponse response) {

        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        Error serverError = new Error();
        switch (e.getCode()) {
            case 1:
                serverError.setMessage("No nearby city available");
                serverError.setCode(500);
                break;
            case 2:
                serverError.setMessage("Host not mean for location");
                serverError.setCode(500);
                break;
            case 3:
                serverError.setMessage("External API error");
                serverError.setCode(500);
                break;
            case 4:
                serverError.setMessage("Error creating QR");
                serverError.setCode(500);
            default:
                serverError.setCode(500);
                serverError.setMessage("Internal error");
                break;
        }
        return serverError;
    }
}
