package training.weather.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import training.weather.dto.QueryResponse;
import training.weather.dto.WeatherForecastResponse;

public class SourceWeather implements ISourceWeather {

    private RestTemplate restTemplate;

    @Override
    public List<QueryResponse> getPlaces(final String query) {
        ResponseEntity<QueryResponse[]> responseEntity = restTemplate.getForEntity("https://www.metaweather.com/api/location/search/?query={query}", QueryResponse[].class, query);
        QueryResponse[] queryResponseArray = responseEntity.getBody();
        return Arrays.stream(queryResponseArray).collect(Collectors.toList());
    }

    @Override
    public WeatherForecastResponse getWeatherForecast(final Long woe) {
        return restTemplate.getForEntity("https://www.metaweather.com/api/location/{woe}", WeatherForecastResponse.class, woe).getBody();
    }

    @Autowired
    public void setRestTemplate(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
