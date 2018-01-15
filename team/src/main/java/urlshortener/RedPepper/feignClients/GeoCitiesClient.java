package urlshortener.RedPepper.feignClients;

import com.netflix.config.ConfigurationManager;
import feign.RequestLine;
import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import urlshortener.RedPepper.model.GeoCitiesResult;

public class GeoCitiesClient {
    private static Logger logger = LoggerFactory.getLogger(GeoCitiesClient.class);
    private GeoCitiesResult local;

    public GeoCitiesClient(GeoCitiesResult local) {
        this.local = local;
    }

    private interface GeoCitiesClientFeign {
        @RequestLine("GET /citiesJSON?north={north}&south={south}&east={east}&west={west}&username=crzyivo&maxRows=100")
        GeoCitiesResult getCitiesFeign(@feign.Param("north") double north, @feign.Param("south") double south
                , @feign.Param("east") double east, @feign.Param("west") double west);
    }

    GeoCitiesClientFeign fallback = (north, south, east, west) -> {
        logger.info("FEIGN TIMEOUT");
        return local;
    };

    public GeoCitiesResult getCities(double N, double S
            , double E, double W) {

        GeoCitiesClientFeign CityClient = HystrixFeign.builder()
                .decoder(new JacksonDecoder())
                .target(GeoCitiesClientFeign.class, "http://api.geonames.org", fallback);
        ConfigurationManager.getConfigInstance()
                .setProperty("hystrix.command.getCitiesFeign.execution.isolation.thread.timeoutInMilliseconds", 2000);
        return CityClient.getCitiesFeign(N, S, E, W);
    }
}
