package urlshortener.RedPepper.feignClients;

import com.netflix.config.ConfigurationManager;
import feign.RequestLine;
import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;
import urlshortener.RedPepper.ExceptionHandlers.ApiException;
import urlshortener.RedPepper.model.GeoCitiesResult;

public class CitiesListClient {
    private GeoCitiesResult local;

    private interface CitiesListFeign {
        @RequestLine("GET /data/longitude-latitude.json")
        GeoCitiesResult getListFeign() throws ApiException;
    }

    static CitiesListFeign fallback = () -> {
        throw new ApiException(3, "randomlists error");
    };

    public static GeoCitiesResult getList() throws ApiException {
        CitiesListFeign list = HystrixFeign.builder().decoder(new JacksonDecoder())
                .target(CitiesListFeign.class, "https://www.randomlists.com", fallback);

        ConfigurationManager.getConfigInstance()
                .setProperty("hystrix.command.getListFeign.execution.isolation.thread.timeoutInMilliseconds", 12000);
        return list.getListFeign();
    }
}

