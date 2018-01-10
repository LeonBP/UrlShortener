package urlshortener.team.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import urlshortener.RedPepper.controllers.DefaultApiController;
import urlshortener.RedPepper.controllers.PinpointApiController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DefaultTest {
    private MockMvc mockMvc;

    @InjectMocks
    private DefaultApiController defaultApi;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(defaultApi).build();
    }

    @Test
    public void thatGetsALocationOnCorrectIP()
            throws Exception {
        String expectedJson = "{\"mode\":\"1\",\"clientIP\":\"90.94.192.43\"," +
                "\"precision\":1}";
        String bodyParams = "{\"mode\":\"1\",\"clientIP\":\"90.94.192.43\"," +
                "\"precision\":1}";
        mockMvc.perform(post("/").with(remoteAddr("90.94.192.43"))
                .contentType(MediaType.APPLICATION_JSON).content(bodyParams)).andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void thatBadIpGetsError404()
            throws Exception{
        String bodyParams = "{\"redirect\": false,\"radio\": 0,\"resultNumber\": 1,\"cosa\":\"kek\"}";
        mockMvc.perform(post("/pinpoint").with(remoteAddr("0.0.0.1"))
                .contentType(MediaType.APPLICATION_JSON).content(bodyParams)).andExpect(status().isNotFound());
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
