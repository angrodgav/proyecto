package training.weather.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import training.weather.dto.QueryResponse;
import training.weather.dto.WeatherForecastDayDto;
import training.weather.dto.WeatherForecastResponse;

@RunWith(MockitoJUnitRunner.class)
public class WeatherForecastServiceTest {

    private static final LocalDate PREDICTION_LIMIT = LocalDate.now().plusDays(6);
    private static final LocalDate LAST_DATE = LocalDate.now().minusDays(2);

    private static final LocalDate TODAY = LocalDate.now();
    
    private static final String EMPTY_STRING = "";

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    
    @InjectMocks
    private WeatherForecastService weatherForecastService = new WeatherForecastService();

    @Mock
    private ISourceWeather sourceWeather;
    
    @Before
    public void setUp() throws IOException {

    	List<QueryResponse> querysResponseMadrid = new ArrayList<QueryResponse>();
    	QueryResponse madridResponse = new QueryResponse();
    	madridResponse.setTitle("Madrid");
    	madridResponse.setWoeid(766273L);

    	querysResponseMadrid.add(madridResponse);
    	
    	
    	List<QueryResponse> querysResponseSan = new ArrayList<QueryResponse>();

    	QueryResponse san1Response = new QueryResponse();
    	san1Response.setTitle("San Francisco");
    	san1Response.setWoeid(2487956L);

    	QueryResponse san2Response = new QueryResponse();
    	san2Response.setTitle("San Diego");
    	san2Response.setWoeid(2487889L);

    	QueryResponse san3Response = new QueryResponse();
    	san3Response.setTitle("San Jose");
    	san3Response.setWoeid(2488042L);
    	querysResponseSan.add(san1Response);
    	querysResponseSan.add(san2Response);
    	querysResponseSan.add(san3Response);
    	
    	when(sourceWeather.getPlaces("Madrid")).thenReturn(querysResponseMadrid);
    	when(sourceWeather.getPlaces("san")).thenReturn(querysResponseMadrid);
    	when(sourceWeather.getPlaces(null)).thenThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN, "HttpClientErrorException"));
    	when(sourceWeather.getPlaces("Madrida")).thenReturn(new ArrayList<>());

    	WeatherForecastResponse weatherForecastResponseMadridToday = new WeatherForecastResponse();
    	List<WeatherForecastDayDto> weatherForecastDaysDto = new ArrayList<>();
    	
    	WeatherForecastDayDto weatherForecastDayDto = new WeatherForecastDayDto();
    	weatherForecastDayDto.setWeather_state_name("Clear");
    	weatherForecastDayDto.setApplicable_date(TODAY.format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
    	
    	weatherForecastDaysDto.add(weatherForecastDayDto);
    	weatherForecastResponseMadridToday.setConsolidated_weather(weatherForecastDaysDto);
    	
    	WeatherForecastResponse weatherForecastResponseSan1Today = new WeatherForecastResponse();
    	weatherForecastResponseSan1Today.setConsolidated_weather(weatherForecastDaysDto);
    	
    	WeatherForecastResponse weatherForecastResponseSan2Today = new WeatherForecastResponse();
    	weatherForecastResponseSan2Today.setConsolidated_weather(weatherForecastDaysDto);
    	
    	WeatherForecastResponse weatherForecastResponseSan3Today = new WeatherForecastResponse();
    	weatherForecastResponseSan3Today.setConsolidated_weather(weatherForecastDaysDto);
    	
    	when(sourceWeather.getWeatherForecast(766273L)).thenReturn(weatherForecastResponseMadridToday);
    	when(sourceWeather.getWeatherForecast(2487956L)).thenReturn(weatherForecastResponseSan1Today);
    	when(sourceWeather.getWeatherForecast(2487889L)).thenReturn(weatherForecastResponseSan2Today);
    	when(sourceWeather.getWeatherForecast(2488042L)).thenReturn(weatherForecastResponseSan3Today);
    }
    
    
    @Test
    public void Given_CorrectParameters_WhenGetCityWeather_Then_GetCorrectWeatherStates() throws IOException {
        String forecast = weatherForecastService.getCityWeather("Madrid", LocalDate.now());
        assertNotNull(forecast);
    }

    @Test(expected = HttpClientErrorException.class)
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