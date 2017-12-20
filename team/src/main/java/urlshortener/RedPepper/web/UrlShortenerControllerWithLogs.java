//package urlshortener.RedPepper.web;
//
//import io.swagger.annotations.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//
//import urlshortener.common.domain.ShortURL;
//import urlshortener.common.web.UrlShortenerController;
//
//import urlshortener.RedPepper.controllers.*;
//import urlshortener.RedPepper.model.Config;
//
//@RestController
//public class UrlShortenerControllerWithLogs extends UrlShortenerController {
//
//	private static final Logger logger = LoggerFactory.getLogger(UrlShortenerControllerWithLogs.class);
//
//	@Override
//	@RequestMapping(value = "/{id:(?!link|index|swagger-ui|pinpoint).*}", method = RequestMethod.GET)
//	public ResponseEntity<?> redirectTo(@PathVariable String id, HttpServletRequest request) {
//		logger.info("Requested redirection with hash " + id);
//		return super.redirectTo(id, request);
//	}
//
//	@Override
//    @RequestMapping(value = "/", method = RequestMethod.POST)
//	public ResponseEntity shortener(@RequestParam("url") String url,
//                                              @RequestParam(value = "sponsor", required = false) String sponsor,
//                                              HttpServletRequest request) {
//		logger.info("Requested new short for uri " + url);
//		return super.shortener(url, sponsor, request);
//	}
//
//        public ResponseEntity<String> rootPost(
//        @ApiParam(value = "Configuraion of the geohash generator" ,required=true )  @Valid @RequestBody Config mode) {
//        // do some magic!
//        return new ResponseEntity<String>("hola mundo", HttpStatus.OK);
//    }
//
//
//}


