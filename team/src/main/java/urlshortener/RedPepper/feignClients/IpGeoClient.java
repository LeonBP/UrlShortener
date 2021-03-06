package urlshortener.RedPepper.feignClients;

import feign.RequestLine;
import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;
import org.springframework.web.client.HttpClientErrorException;
import urlshortener.RedPepper.ExceptionHandlers.ApiException;
import urlshortener.RedPepper.ExceptionHandlers.NotFoundException;
import urlshortener.RedPepper.model.IpGeoResults;

public class IpGeoClient {
    private interface IpGeoClientFeign {
        @RequestLine("GET /{ip}")
        IpGeoResults IpLocalization(@feign.Param("ip") String ip) throws ApiException;
    }

    static IpGeoClientFeign fallback = (ip) -> {
        throw new ApiException(2, "Ip geolocation api conection error");
    };

    public static IpGeoResults getLocation(String ip) throws HttpClientErrorException, ApiException {

        IpGeoClientFeign ipClient = HystrixFeign.builder().decoder(new JacksonDecoder())
                .target(IpGeoClientFeign.class, "https://freegeoip.net/json/", fallback);
        IpGeoResults apiResults = ipClient.IpLocalization(ip);
        if (apiResults.getCity().equals("")) {
            throw new NotFoundException("Bad ip for location", 2);
        }
        return apiResults;
    }
}
