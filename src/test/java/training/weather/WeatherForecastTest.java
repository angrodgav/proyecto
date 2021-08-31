package training.weather;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

public class WeatherForecastTest {

    private WeatherForecast weatherForecast;

    @Before
    public void setUp() {
        weatherForecast = new WeatherForecast();
    }

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
        Date more144HoursLater = new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 6));
        String forecast = weatherForecast.getCityWeather("Madrid", more144HoursLater);
        assertEquals(forecast, "");
    }

    //TODO 144 hours does not necessarily mean 6 days due to the time change, we must refine more
    @Test
    public void Given_Is6DaysLater_WhenGetCityWeather_Then_GetEmptyResponse() throws IOException {

        final int numberHoursLess = 2;

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 3);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        Date less144HoursLaterButPlus6Days = new Date(c.getTime().getTime() + (1000 * 60 * 60 * (24 * 6 - numberHoursLess)));
        String forecast = weatherForecast.getCityWeather("Madrid", less144HoursLaterButPlus6Days);
        assertEquals(forecast, "");
    }

    //TODO 144 hours does not necessarily mean 6 days due to the time change, we must refine more
    @Test
    public void Given_Is5DaysLater_WhenGetCityWeather_Then_GetCorrectWeatherStates() throws IOException {

        final int numberHoursLess = 4;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 3);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        Date less144HoursLaterAndPlus5Days = new Date(c.getTime().getTime() + (1000 * 60 * 60 * (24 * 6 - numberHoursLess)));
        String forecast = weatherForecast.getCityWeather("Madrid", less144HoursLaterAndPlus5Days);
        assertNotNull(forecast);
    }

    @Test
    public void Given_PastDate_WhenGetCityWeather_Then_GetEmptyResponse() throws IOException {
        Date pastDate = new Date(new Date().getTime() - (1000 * 60 * 60 * 24 * 2));
        String forecast = weatherForecast.getCityWeather("Madrid", pastDate);
        assertEquals(forecast, "");
    }
}