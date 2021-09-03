package training.weather.service;

import java.util.List;

import training.weather.dto.QueryResponse;
import training.weather.dto.WeatherForecastResponse;

public interface ISourceWeather {

    List<QueryResponse> getPlaces(String query);

    WeatherForecastResponse getWeatherForecast(Long woe);
}
