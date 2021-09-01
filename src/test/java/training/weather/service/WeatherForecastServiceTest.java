package training.weather.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.json.JSONException;
import org.junit.Test;

import training.weather.WeatherForecast;

public class WeatherForecastServiceTest {
	 
    private static final LocalDate PREDICTION_LIMIT = LocalDate.now().plusDays(6);
    private static final LocalDate LAST_DATE = LocalDate.now().minusDays(2);

    private static final String EMPTY_STRING = "";
	
    private WeatherForecastService weatherForecastService = new WeatherForecastService();
	
    @Test
    public void Given_CorrectParameters_WhenGetCityWeather_Then_GetCorrectWeatherStates() throws IOException {
        String forecast = weatherForecastService.getCityWeather("Madrid", new Date());
        assertNotNull(forecast);
    }

    @Test(expected = JSONException.class)
    public void Given_NullCity_WhenGetCityWeather_Then_ThrowJSONException() throws IOException {
    	weatherForecastService.getCityWeather(null, new Date());
    }

    @Test
    public void Given_NullDate_WhenGetCityWeather_Then_GetCorrectWeatherStates() throws IOException {
        String forecast = weatherForecastService.getCityWeather("Madrid", null);
        assertNotNull(forecast);
    }

    @Test(expected = JSONException.class)
    public void Given_WrongCity_WhenGetCityWeather_Then_ThrowJSONException() throws IOException {
    	weatherForecastService.getCityWeather("Madrida", new Date());
    }

    @Test
    public void Given_PartOfCorrectCityName_WhenGetCityWeather_Then_GetCorrectWeatherStatesOfARamdomCityOfQuery() throws IOException {
        String forecast = weatherForecastService.getCityWeather("san", new Date());
        assertNotNull(forecast);
    }

    @Test
    public void Given_Plus6DaysLater_WhenGetCityWeather_Then_GetEmptyResponse() throws IOException {
        String forecast = weatherForecastService.getCityWeather("Madrid", Date.from(PREDICTION_LIMIT.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        assertEquals(forecast, EMPTY_STRING);
    }

    @Test
    public void Given_PastDate_WhenGetCityWeather_Then_GetEmptyResponse() throws IOException {
        String forecast = weatherForecastService.getCityWeather("Madrid", Date.from(LAST_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        assertEquals(forecast, EMPTY_STRING);
    }
}
