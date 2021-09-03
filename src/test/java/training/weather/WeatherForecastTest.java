package training.weather;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import training.weather.service.IConvertLocalDateService;
import training.weather.service.IWeatherForecastService;

@RunWith(MockitoJUnitRunner.class)
public class WeatherForecastTest {

    private static final LocalDate PREDICTION_LIMIT = LocalDate.now().plusDays(6);
    private static final LocalDate LAST_DATE = LocalDate.now().minusDays(2);
    private static final LocalDate TODAY = LocalDate.now();
    private static final Date TODAY_DATE = new Date();
    private static final Date LAST_DATE_DATE = Date.from(PREDICTION_LIMIT.atStartOfDay(ZoneId.systemDefault()).toInstant());
    private static final Date PREDICTION_LIMIT_DATE = Date.from(LAST_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant());

    private static final String EMPTY_STRING = "";

    private static final String STATE_CLEAR = "Clear";

    private static final String HTTP_EXCEPTION_MENSAJE = "Error";

    private static final HttpClientErrorException ERROR_HTTP_CLIENT = new HttpClientErrorException(HttpStatus.NOT_FOUND, HTTP_EXCEPTION_MENSAJE);
    @InjectMocks
    private WeatherForecast weatherForecast = new WeatherForecast();

    @Mock
    private IWeatherForecastService weatherForecastService;

    @Mock
    private IConvertLocalDateService convertLocalDateService;

    @Before
    public void setUp() throws IOException {

        when(convertLocalDateService.convertDateToLocalDateNullSafe(null)).thenReturn(TODAY);
        when(convertLocalDateService.convertDateToLocalDateNullSafe(TODAY_DATE)).thenReturn(TODAY);
        when(convertLocalDateService.convertDateToLocalDateNullSafe(PREDICTION_LIMIT_DATE)).thenReturn(PREDICTION_LIMIT);
        when(convertLocalDateService.convertDateToLocalDateNullSafe(LAST_DATE_DATE)).thenReturn(LAST_DATE);

        when(weatherForecastService.getCityWeather("Madrid", TODAY)).thenReturn(STATE_CLEAR);
        when(weatherForecastService.getCityWeather(null, TODAY)).thenThrow(ERROR_HTTP_CLIENT);
        when(weatherForecastService.getCityWeather("Madrida", TODAY)).thenThrow(ERROR_HTTP_CLIENT);
        when(weatherForecastService.getCityWeather("san", TODAY)).thenReturn(STATE_CLEAR);
        when(weatherForecastService.getCityWeather("Madrid", PREDICTION_LIMIT)).thenReturn(EMPTY_STRING);
        when(weatherForecastService.getCityWeather("Madrid", LAST_DATE)).thenReturn(EMPTY_STRING);
    }

    @Test
    public void Given_CorrectParameters_WhenGetCityWeather_Then_GetCorrectWeatherStates() throws IOException {
        String forecast = weatherForecast.getCityWeather("Madrid", TODAY_DATE);
        assertNotNull(forecast);
    }

    @Test(expected = HttpClientErrorException.class)
    public void Given_NullCity_WhenGetCityWeather_Then_ThrowJSONException() throws IOException {
        weatherForecast.getCityWeather(null, TODAY_DATE);
    }

    @Test
    public void Given_NullDate_WhenGetCityWeather_Then_GetCorrectWeatherStates() throws IOException {
        String forecast = weatherForecast.getCityWeather("Madrid", null);
        assertNotNull(forecast);
    }

    @Test(expected = HttpClientErrorException.class)
    public void Given_WrongCity_WhenGetCityWeather_Then_ThrowJSONException() throws IOException {
        weatherForecast.getCityWeather("Madrida", TODAY_DATE);
    }

    @Test
    public void Given_PartOfCorrectCityName_WhenGetCityWeather_Then_GetCorrectWeatherStatesOfARamdomCityOfQuery() throws IOException {
        String forecast = weatherForecast.getCityWeather("san", TODAY_DATE);
        assertNotNull(forecast);
    }

    @Test
    public void Given_Plus6DaysLater_WhenGetCityWeather_Then_GetEmptyResponse() throws IOException {
        String forecast = weatherForecast.getCityWeather("Madrid", LAST_DATE_DATE);
        assertEquals(EMPTY_STRING, forecast);
    }

    @Test
    public void Given_PastDate_WhenGetCityWeather_Then_GetEmptyResponse() throws IOException {
        String forecast = weatherForecast.getCityWeather("Madrid", PREDICTION_LIMIT_DATE);
        assertEquals(EMPTY_STRING, forecast);
    }
}
