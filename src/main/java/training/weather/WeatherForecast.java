package training.weather;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import training.weather.service.IWeatherForecastService;

@Controller
public class WeatherForecast {

    private IWeatherForecastService weatherForecastService;

    public String getCityWeather(final String city, final Date datetime) throws IOException {
        LocalDate day = weatherForecastService.convertDateToLocalDateNullSafe(datetime);

        return this.getCityWeather(city, day);
    }

    private String getCityWeather(final String city, final LocalDate day) throws IOException {
        return weatherForecastService.getCityWeather(city, day);
    }

    @Autowired
    public void setWeatherForecastService(final IWeatherForecastService weatherForecastService) {
        this.weatherForecastService = weatherForecastService;
    }
}
