package urlshortener.team.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import urlshortener.RedPepper.DBConnection.DBOperations;
import urlshortener.RedPepper.ExceptionHandlers.ExceptionController;
import urlshortener.RedPepper.controllers.GeohashApiController;
import urlshortener.RedPepper.model.City;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class GeohashTest {
    private MockMvc mockMvc;

    @InjectMocks
    private GeohashApiController geohashApi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(geohashApi)
                .setControllerAdvice(new ExceptionController())
                .build();
        City ExampleCity = new City("ExampleCity", 0, 0);
        DBOperations.addURL(ExampleCity, "http://example.org/", "exampleHasH");
    }

    @After
    public void finish() {
        DBOperations.deleteByHash("exampleHasH");
    }

    @Test
    public void thatExistingShortURLRedirects()
            throws Exception {
        String bodyParams = "{\"mode\":\"1\",\"clientIP\":\"90.94.192.43\"," +
                "\"precision\":1,\"url\":\"https://www.google.es/\"}";
        mockMvc.perform(get("/exampleHasH").with(remoteAddr("90.94.192.43"))
        ).andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://example.org/"));

    }

    @Test
    public void thatNonExistingShortURLGets404()
            throws Exception {
        String expectedError = "{\"code\":404,\"message\":\"Your link is not located\"}";
        mockMvc.perform(get("/00000").with(remoteAddr("90.94.192.43"))
        ).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().json(expectedError));
    }

    private static RequestPostProcessor remoteAddr(final String remoteAddr) {
        return new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setRemoteAddr(remoteAddr);
                return request;
            }
        };
    }
}
