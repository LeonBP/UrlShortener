package unit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import urlshortener.RedPepper.DBConnection.DBOperations;
import urlshortener.RedPepper.model.Checker;
import urlshortener.RedPepper.model.City;

public class CheckerTest {

    @InjectMocks
    private Checker checker;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        City ExampleCity = new City("ExampleCity", 0, 0);
        DBOperations.addURL(ExampleCity, "http://example.org/", "exampleHasH");
    }

    @After
    public void finish() {
        DBOperations.deleteByHash("exampleHasH");
    }

    @Test
    public void thatDuplicateHashIsChecked()
            throws Exception {
        assert checker.estaRepetidaEnBD("exampleHasH") == true;

    }
}
