package training.weather.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.weather.dto.QueryResponse;
import training.weather.dto.WeatherForecastDayDto;
import training.weather.dto.WeatherForecastResponse;


@Service
public class WeatherForecastService implements IWeatherForecastService {

    private static final String EMPTY_STRING = "";

    private ISourceWeather sourceWeather;

    private IConvertLocalDateService convertLocalDateService;

    @Override
    public String getCityWeather(final String city, final LocalDate day) throws IOException {
        if (day.isBefore(LocalDate.now().plusDays(6))) {
            List<QueryResponse> querysResponse = sourceWeather.getPlaces(city);
            Long woe = querysResponse.stream().map(QueryResponse::getWoeid).findFirst().orElse(null);
            if (woe != null) {
                WeatherForecastResponse weatherForecastResponse = sourceWeather.getWeatherForecast(woe);
                return weatherForecastResponse
                        .getConsolidated_weather()
                        .stream()
                        .filter(ele -> convertLocalDateService.sameDay(day, ele.getApplicable_date()))
                        .map(WeatherForecastDayDto::getWeather_state_name)
                        .findFirst()
                        .orElse(EMPTY_STRING);
            }
        }
        return EMPTY_STRING;
    }

    @Autowired
    public void setSourceWeather(final ISourceWeather sourceWeather) {
        this.sourceWeather = sourceWeather;
    }

    @Autowired
    public void setConvertLocalDateService(final IConvertLocalDateService convertLocalDateService) {
        this.convertLocalDateService = convertLocalDateService;
    }
}
