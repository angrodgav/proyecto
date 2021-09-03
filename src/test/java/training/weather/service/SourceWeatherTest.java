package training.weather.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import training.weather.dto.QueryResponse;
import training.weather.dto.WeatherForecastResponse;

@RunWith(MockitoJUnitRunner.class)
public class SourceWeatherTest {

    private RestTemplate restTemplate = new RestTemplate();

    private SourceWeather sourceWeather = new SourceWeather();

    @Before
    public void setUp() throws IOException {
        sourceWeather.setRestTemplate(restTemplate);
    }

    @Test
    public void Given_CorrectUniqueParameter_WhenGetPlaces_Then_UniqueResponse() {
        final List<QueryResponse> querysResponse = sourceWeather.getPlaces("Madrid");
        assertEquals(1, querysResponse.size());
    }

    @Test
    public void Given_CorrectUniqueParameter_WhenGetPlaces_Then_SameTitleResponse() {
        final List<QueryResponse> querysResponse = sourceWeather.getPlaces("Madrid");
        assertEquals("Madrid", querysResponse.get(0).getTitle());
    }

    @Test
    public void Given_PartOfManyCorrectCityName_WhenGetPlaces_Then_Get() {
        final List<QueryResponse> querysResponse = sourceWeather.getPlaces("san");
        assertTrue(querysResponse.size() > 1);
    }

    @Test
    public void Given_IncorrectParameter_WhenGetPlaces_Then_GetEmptyResponse() {
        final List<QueryResponse> querysResponse = sourceWeather.getPlaces("Madrida");
        assertEquals(0, querysResponse.size());
    }

    @Test
    public void Given_CorrectParameter_WhenWeatherForecast_Then_CorrectWeatherForecastResponse() {
        final WeatherForecastResponse weatherForecastResponse = sourceWeather.getWeatherForecast(2487956L);
        assertNotNull(weatherForecastResponse.getConsolidated_weather());
    }

    @Test(expected = HttpClientErrorException.class)
    public void Given_InCorrectParameter_WhenWeatherForecast_Then_ThrowHttpClientErrorException() {
    	sourceWeather.getWeatherForecast(12L);
    }
}
