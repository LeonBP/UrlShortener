package urlshortener.RedPepper.controllers;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import urlshortener.RedPepper.controllers.QRService;

@Controller
@EnableAsync
@EnableCaching
@EnableScheduling
@SpringBootApplication
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.DELETE})
public class QRApp {

    public static final String QRCODE_ENDPOINT = "/qrcode";
    public static final long THIRTY_MINUTES = 1800000;

    @Autowired
    QRService imageService;

    public static void main(String[] args) {
        SpringApplication.run(QRApp.class, args);
    }

    @GetMapping(value = QRCODE_ENDPOINT, produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getQRCode(@RequestParam(value = "text", required = true) String text) {
        try {
            return ResponseEntity.ok().cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES))
                    .body(imageService.generateQRCodeAsync(text, 256, 256).get());
        } catch (Exception ex) {
            throw new InternalServerError("Error while generating QR code image.", ex);
        }
    }

    @Scheduled(fixedRate = THIRTY_MINUTES)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = QRCODE_ENDPOINT)
    public void deleteAllCachedImages() {
        imageService.purgeCache();
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public class InternalServerError extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public InternalServerError(final String message, final Throwable cause) {
            super(message);
        }

    }
}