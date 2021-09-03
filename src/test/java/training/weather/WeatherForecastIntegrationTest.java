package training.weather;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import training.weather.service.ConvertLocalDateService;
import training.weather.service.IConvertLocalDateService;
import training.weather.service.IWeatherForecastService;
import training.weather.service.SourceWeather;
import training.weather.service.WeatherForecastService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WeatherForecast.class, ConvertLocalDateService.class, WeatherForecastService.class, SourceWeather.class, RestTemplate.class })
public class WeatherForecastIntegrationTest {

    private static final LocalDate PREDICTION_LIMIT = LocalDate.now().plusDays(6);
    private static final LocalDate LAST_DATE = LocalDate.now().minusDays(2);
    private static final Date TODAY_DATE = new Date();
    private static final Date LAST_DATE_DATE = Date.from(PREDICTION_LIMIT.atStartOfDay(ZoneId.systemDefault()).toInstant());
    private static final Date PREDICTION_LIMIT_DATE = Date.from(LAST_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant());

    private static final String EMPTY_STRING = "";

    @Autowired
    private WeatherForecast weatherForecast;

    @Test
    public void Given_CorrectParameters_WhenGetCityWeather_Then_GetCorrectWeatherStates() throws IOException {
        String forecast = weatherForecast.getCityWeather("Madrid", TODAY_DATE);
        assertNotNull(forecast);
    }

    @Test(expected = HttpClientErrorException.class)
    public void Given_NullCity_WhenGetCityWeather_Then_ThrowHttpClientErrorException() throws IOException {
        weatherForecast.getCityWeather(null, TODAY_DATE);
    }

    @Test
    public void Given_NullDate_WhenGetCityWeather_Then_GetCorrectWeatherStates() throws IOException {
        String forecast = weatherForecast.getCityWeather("Madrid", null);
        assertNotNull(forecast);
    }

    @Test
    public void Given_WrongCity_WhenGetCityWeather_Then_GetEmptyResponse() throws IOException {
        String forecast = weatherForecast.getCityWeather("Madrida", TODAY_DATE);
        assertEquals(EMPTY_STRING, forecast);
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
