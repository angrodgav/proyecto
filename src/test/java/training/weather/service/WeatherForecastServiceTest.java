package training.weather.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.time.LocalDate;

import org.json.JSONException;
import org.junit.Test;

public class WeatherForecastServiceTest {

    private static final LocalDate PREDICTION_LIMIT = LocalDate.now().plusDays(6);
    private static final LocalDate LAST_DATE = LocalDate.now().minusDays(2);

    private static final String EMPTY_STRING = "";

    private WeatherForecastService weatherForecastService = new WeatherForecastService();

    @Test
    public void Given_CorrectParameters_WhenGetCityWeather_Then_GetCorrectWeatherStates() throws IOException {
        String forecast = weatherForecastService.getCityWeather("Madrid", LocalDate.now());
        assertNotNull(forecast);
    }

    @Test(expected = JSONException.class)
    public void Given_NullCity_WhenGetCityWeather_Then_ThrowJSONException() throws IOException {
        weatherForecastService.getCityWeather(null, LocalDate.now());
    }

    @Test(expected = JSONException.class)
    public void Given_WrongCity_WhenGetCityWeather_Then_ThrowJSONException() throws IOException {
        weatherForecastService.getCityWeather("Madrida", LocalDate.now());
    }

    @Test
    public void Given_PartOfCorrectCityName_WhenGetCityWeather_Then_GetCorrectWeatherStatesOfARamdomCityOfQuery() throws IOException {
        String forecast = weatherForecastService.getCityWeather("san", LocalDate.now());
        assertNotNull(forecast);
    }

    @Test
    public void Given_Plus6DaysLater_WhenGetCityWeather_Then_GetEmptyResponse() throws IOException {
        String forecast = weatherForecastService.getCityWeather("Madrid", PREDICTION_LIMIT);
        assertEquals(EMPTY_STRING, forecast);
    }

    @Test
    public void Given_PastDate_WhenGetCityWeather_Then_GetEmptyResponse() throws IOException {
        String forecast = weatherForecastService.getCityWeather("Madrid", LAST_DATE);
        assertEquals(EMPTY_STRING, forecast);
    }

}