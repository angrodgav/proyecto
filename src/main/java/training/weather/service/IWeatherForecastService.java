package training.weather.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public interface IWeatherForecastService {

    String getCityWeather(String city, LocalDate day) throws IOException;

    LocalDate convertDateToLocalDateNullSafe(Date date);
}
