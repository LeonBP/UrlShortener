package unit;

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
import urlshortener.RedPepper.model.Checker;
import urlshortener.RedPepper.model.City;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CheckerTest {

    @InjectMocks
    private Checker checker;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        City ExampleCity = new City("ExampleCity",0,0);
        DBOperations.addURL(ExampleCity,"http://example.org/","exampleHasH");
    }

    @After
    public void finish(){
        DBOperations.deleteByHash("exampleHasH");
    }

    @Test
    public void thatDuplicateHashIsChecked()
            throws Exception {
        assert checker.estaRepetidaEnBD("exampleHasH") == true;

    }
}
