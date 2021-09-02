package training.weather.service;

import java.io.IOException;
import java.time.LocalDate;

public interface IWeatherForecastService {

    String getCityWeather(String city, LocalDate day) throws IOException;
    
}
