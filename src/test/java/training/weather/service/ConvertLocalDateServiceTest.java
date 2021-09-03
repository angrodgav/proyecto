package training.weather.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.junit.Test;
import org.springframework.stereotype.Service;

@Service
public class ConvertLocalDateServiceTest {

    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate YESTERDAY = LocalDate.now().minusDays(1);
    private static final Date YESTERDAY_DATE = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L);
    private static final String DATE_FORMAT = "yyyy-MM-dd";

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

    @Test
    public void Given_NullLocalDate_WhenSameDay_Then_ReturnFalse() throws IOException {
        assertFalse(convertLocalDateService.sameDay(null, "2021-09-02"));
    }

    @Test
    public void Given_NullStringDate_WhenSameDay_Then_ReturnFalse() throws IOException {
    	assertFalse(convertLocalDateService.sameDay(TODAY, null));
    }

    @Test
    public void Given_MatchingParameters_WhenSameDay_Then_ReturnTrue() throws IOException {
    	assertTrue(convertLocalDateService.sameDay(TODAY, TODAY.format(DateTimeFormatter.ofPattern(DATE_FORMAT))));
    }
    
    @Test
    public void Given_NotMatchingParameters_WhenSameDay_Then_ReturnFalse() throws IOException {
    	assertFalse(convertLocalDateService.sameDay(YESTERDAY, TODAY.format(DateTimeFormatter.ofPattern(DATE_FORMAT))));
    }
}