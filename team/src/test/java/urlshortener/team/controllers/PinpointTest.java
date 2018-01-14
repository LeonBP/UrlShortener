package urlshortener.team.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import urlshortener.RedPepper.ExceptionHandlers.ExceptionController;
import urlshortener.RedPepper.controllers.PinpointApiController;
import urlshortener.common.repository.ClickRepository;
import urlshortener.common.repository.ShortURLRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PinpointTest {

    private MockMvc mockMvc;

    @InjectMocks
    private PinpointApiController pinPoint;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(pinPoint)
                .setControllerAdvice(new ExceptionController())
                .build();
    }

    @Test
    public void thatGetsALocationOnCorrectIP()
            throws Exception {
        String expectedJson = "{\"ip\":\"90.94.192.43\",\"city\":\"Zaragoza\"," +
                "\"latitude\":41.6453,\"longitude\":-0.8849}";
        String bodyParams = "{\"redirect\": false,\"radio\": 0,\"resultNumber\": 1}";
        mockMvc.perform(post("/pinpoint").with(remoteAddr("90.94.192.43"))
                .contentType(MediaType.APPLICATION_JSON).content(bodyParams)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expectedJson));

    }

    @Test
    public void thatBadIpGetsError404()
            throws Exception{
        String bodyParams = "{\"redirect\": false,\"radio\": 0,\"resultNumber\": 1,\"cosa\":\"kek\"}";
        mockMvc.perform(post("/pinpoint").with(remoteAddr("0.0.0.1"))
                .contentType(MediaType.APPLICATION_JSON).content(bodyParams))
                .andExpect(status().isForbidden());
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
