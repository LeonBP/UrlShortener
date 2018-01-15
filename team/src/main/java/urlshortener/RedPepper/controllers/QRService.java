package urlshortener.RedPepper.controllers;


import java.io.*;

import org.slf4j.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.concurrent.ListenableFuture;

import com.google.zxing.*;
import com.google.zxing.client.j2se.*;
import com.google.zxing.common.BitMatrix;

@Service
@Cacheable(cacheNames = "qr-code-cache", sync = true)
public class QRService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QRService.class);

    public byte[] generateQRCode(String text, int width, int height) throws WriterException, IOException {

        Assert.hasText(text);
        Assert.isTrue(width > 0);
        Assert.isTrue(height > 0);

        LOGGER.info("Will generate image  text=[{}], width=[{}], height=[{}]", text, width, height);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitMatrix matrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height);
        MatrixToImageWriter.writeToStream(matrix, MediaType.IMAGE_PNG.getSubtype(), baos, new MatrixToImageConfig());
        return baos.toByteArray();
    }

    @Async
    public ListenableFuture<byte[]> generateQRCodeAsync(String text, int width, int height) throws Exception {
        return new AsyncResult<byte[]>(generateQRCode(text, width, height));
    }

    @CacheEvict(cacheNames = "qr-code-cache", allEntries = true)
    public void purgeCache() {
        LOGGER.info("Purging cache");
    }

}