package training.weather.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import org.junit.Test;

public class ConvertLocalDateServiceTest {

    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate YESTERDAY = LocalDate.now().minusDays(1);
    private static final Date YESTERDAY_DATE = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L);

    private ConvertLocalDateService convertLocalDateService = new ConvertLocalDateService();

    @Test
    public void Given_NullDate_WhenConvertDateToLocalDateNullSafe_Then_GetTodayResponse() throws IOException {
        LocalDate day = convertLocalDateService.convertDateToLocalDateNullSafe(null);
        assertEquals(TODAY, day);
    }

    @Test
    public void Given_AnyDate_WhenConvertDateToLocalDateNullSafe_Then_GetConvertLocalDateResponse() throws IOException {
        LocalDate day = convertLocalDateService.convertDateToLocalDateNullSafe(YESTERDAY_DATE);
        assertEquals(YESTERDAY, day);
    }

}