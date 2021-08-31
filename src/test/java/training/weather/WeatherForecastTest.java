package training.weather;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.junit.Test;

public class WeatherForecastTest {

    private WeatherForecast weatherForecast = new WeatherForecast();

    private static final int MILISECONDS_1_HOUR = 1000 * 60 * 60;
    
    private static final int HOURS_6_NORMAL_DAYS = 24 * 6;//sin cambio horario
    
    private static final int HOURS_2_NORMAL_DAYS = 24 * 2;//sin cambio horario

    private static final String EMPTY_STRING = "";
    
    @Test
    public void Given_CorrectParameters_WhenGetCityWeather_Then_GetCorrectWeatherStates() throws IOException {
        String forecast = weatherForecast.getCityWeather("Madrid", new Date());
        assertNotNull(forecast);
    }

    @Test(expected = JSONException.class)
    public void Given_NullCity_WhenGetCityWeather_Then_ThrowJSONException() throws IOException {
        weatherForecast.getCityWeather(null, new Date());
    }

    @Test
    public void Given_NullDate_WhenGetCityWeather_Then_GetCorrectWeatherStates() throws IOException {
        String forecast = weatherForecast.getCityWeather("Madrid", null);
        assertNotNull(forecast);
    }

    @Test(expected = JSONException.class)
    public void Given_WrongCity_WhenGetCityWeather_Then_ThrowJSONException() throws IOException {
        weatherForecast.getCityWeather("Madrida", new Date());
    }

    @Test
    public void Given_PartOfCorrectCityName_WhenGetCityWeather_Then_GetCorrectWeatherStatesOfARamdomCityOfQuery() throws IOException {
        String forecast = weatherForecast.getCityWeather("san", new Date());
        assertNotNull(forecast);
    }

    //TODO 144 hours does not necessarily mean 6 days due to the time change, we must refine more
    @Test
    public void Given_Plus6DaysLater_WhenGetCityWeather_Then_GetEmptyResponse() throws IOException {
        Date more144HoursLater = new Date(new Date().getTime() + (MILISECONDS_1_HOUR * HOURS_6_NORMAL_DAYS));
        String forecast = weatherForecast.getCityWeather("Madrid", more144HoursLater);
        assertEquals(forecast, EMPTY_STRING);
    }

    //TODO 144 hours does not necessarily mean 6 days due to the time change, we must refine more
    @Test
    public void Given_Is6DaysLater_WhenGetCityWeather_Then_GetEmptyResponse() throws IOException {

        final int numberHoursLess = 2;

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 3);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        Date less144HoursLaterButPlus6Days = new Date(c.getTime().getTime() + (MILISECONDS_1_HOUR * (HOURS_6_NORMAL_DAYS - numberHoursLess)));
        String forecast = weatherForecast.getCityWeather("Madrid", less144HoursLaterButPlus6Days);
        assertEquals(forecast, EMPTY_STRING);
    }

    //TODO 144 hours does not necessarily mean 6 days due to the time change, we must refine more
    @Test
    public void Given_Is5DaysLater_WhenGetCityWeather_Then_GetCorrectWeatherStates() throws IOException {

        final int numberHoursLess = 4;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 3);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        Date less144HoursLaterAndPlus5Days = new Date(c.getTime().getTime() + (MILISECONDS_1_HOUR * (HOURS_6_NORMAL_DAYS - numberHoursLess)));
        String forecast = weatherForecast.getCityWeather("Madrid", less144HoursLaterAndPlus5Days);
        assertNotNull(forecast);
    }

    @Test
    public void Given_PastDate_WhenGetCityWeather_Then_GetEmptyResponse() throws IOException {
        Date pastDate = new Date(new Date().getTime() - (MILISECONDS_1_HOUR * HOURS_2_NORMAL_DAYS));
        String forecast = weatherForecast.getCityWeather("Madrid", pastDate);
        assertEquals(forecast, EMPTY_STRING);
    }
}