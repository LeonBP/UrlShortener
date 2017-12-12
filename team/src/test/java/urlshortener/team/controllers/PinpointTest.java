package urlshortener.team.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import urlshortener.RedPepper.controllers.PinpointApiController;
import urlshortener.RedPepper.web.UrlShortenerControllerWithLogs;
import urlshortener.common.repository.ClickRepository;
import urlshortener.common.repository.ShortURLRepository;
import urlshortener.common.web.UrlShortenerController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PinpointTest {

    private MockMvc mockMvc;

    @Mock
    private ClickRepository clickRepository;

    @Mock
    private ShortURLRepository shortURLRepository;

    @InjectMocks
    private PinpointApiController pinPoint;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(pinPoint).build();
    }

    @Test
    public void thatGetsALocationOnCorrectIP()
            throws Exception {
        String expectedJson = "{\"ip\":\"90.94.192.43\",\"city\":\"Zaragoza\",\"latitude\":41.6453,\"longitude\":-0.8849}";
        mockMvc.perform(get("/pinpoint").with(remoteAddr("90.94.192.43"))).andDo(print())
                .andExpect(status().isOk()).andExpect(content().string(expectedJson));

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
