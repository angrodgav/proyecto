package training.weather.service;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import training.weather.dto.QueryResponse;
import training.weather.dto.WeatherForecastResponse;

@RunWith(MockitoJUnitRunner.class)
public class SourceWeatherTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SourceWeather sourceWeather;

    @Before
    public void setUp() throws IOException {

    	QueryResponse madridResponse = new QueryResponse();
    	madridResponse.setTitle("Madrid");
    	madridResponse.setWoeid(766273L);
    	QueryResponse[] querysResponseMadrid = {madridResponse};

    	when(
    	restTemplate.getForEntity("https://www.metaweather.com/api/location/search/?query={query}", QueryResponse[].class, "Madrid")).thenReturn(new ResponseEntity<QueryResponse[]>(querysResponseMadrid, HttpStatus.OK));

    	QueryResponse san1Response = new QueryResponse();
    	san1Response.setTitle("San Francisco");
    	san1Response.setWoeid(2487956L);

    	QueryResponse san2Response = new QueryResponse();
    	san2Response.setTitle("San Diego");
    	san2Response.setWoeid(2487889L);

    	QueryResponse san3Response = new QueryResponse();
    	san3Response.setTitle("San Jose");
    	san3Response.setWoeid(2488042L);
    	QueryResponse[] querysResponseSan = {san1Response, san2Response, san3Response};

    	when(
    	restTemplate.getForEntity("https://www.metaweather.com/api/location/search/?query={query}", QueryResponse[].class,"san")).thenReturn(new ResponseEntity<QueryResponse[]>(querysResponseSan, HttpStatus.OK));

    	QueryResponse[] querysResponseMadrida = {};
    	when(
    	restTemplate.getForEntity("https://www.metaweather.com/api/location/search/?query={query}", QueryResponse[].class,"Madrida")).thenReturn( new ResponseEntity<QueryResponse[]>(querysResponseMadrida, HttpStatus.OK));

    	when(
    	restTemplate.getForEntity("https://www.metaweather.com/api/location/search/?query={query}", QueryResponse[].class,(String)null)).thenThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN, "HttpClientErrorException"));


    	WeatherForecastResponse weatherForecastResponse = new WeatherForecastResponse();
    	weatherForecastResponse.setConsolidated_weather(new ArrayList<>());

    	when(
    	restTemplate.getForEntity("https://www.metaweather.com/api/location/{woe}", WeatherForecastResponse.class, 766273L)).thenReturn(new ResponseEntity<WeatherForecastResponse>(weatherForecastResponse, HttpStatus.OK));

    	when(
    	restTemplate.getForEntity("https://www.metaweather.com/api/location/{woe}", WeatherForecastResponse.class, 12L)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "HttpClientErrorException"));

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
    
    @Test(expected = HttpClientErrorException.class)
    public void Given_NullParameter_WhenGetPlaces_Then_GetEmptyResponse() {
        sourceWeather.getPlaces(null);
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
