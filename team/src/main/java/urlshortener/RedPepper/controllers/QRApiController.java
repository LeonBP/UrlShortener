package urlshortener.RedPepper.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import urlshortener.RedPepper.ExceptionHandlers.ApiException;

import java.util.concurrent.TimeUnit;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-21T17:50:33.590Z")

@RestController
public class QRApiController implements QRApi {

    public static final long THIRTY_MINUTES = 1800000;

    @Autowired
    QRService imageService;

    public ResponseEntity<byte[]> qRGet(@RequestParam("url") String url) throws ApiException {
        try {
            return ResponseEntity.ok().cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES))
                    .body(imageService.generateQRCodeAsync(url, 256, 256).get());
        } catch (Exception ex) {
            throw new ApiException(4, "Error while generating QR code image.");
        }
    }

    @Scheduled(fixedRate = THIRTY_MINUTES)
    public void deleteAllCachedImages() {
        imageService.purgeCache();
    }

}
