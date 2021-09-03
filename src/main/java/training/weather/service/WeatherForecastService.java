package training.weather.service;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.weather.dto.QueryResponse;
import training.weather.dto.WeatherForecastDayDto;


@Service
public class WeatherForecastService implements IWeatherForecastService {

    private static final String EMPTY_STRING = "";

    private ISourceWeather sourceWeather;

    private IConvertLocalDateService convertLocalDateService;

    @Override
    public String getCityWeather(final String city, final LocalDate day) throws IOException {
        if (day.isBefore(LocalDate.now().plusDays(6))) {
            Long woe = getWoeForQuery(city);
            return getWeatherForecastFromWoe(woe, day);
        }
        return EMPTY_STRING;
    }

    private Long getWoeForQuery(final String query) {
        return sourceWeather
                .getPlaces(query)
                .stream()
                .map(QueryResponse::getWoeid)
                .findFirst()
                .orElse(null);
    }

    private String getWeatherForecastFromWoe(final Long woe, final LocalDate day) {
        if (woe != null) {
            return sourceWeather
                    .getWeatherForecast(woe)
                    .getConsolidated_weather()
                    .stream()
                    .filter(ele -> convertLocalDateService.sameDay(day, ele.getApplicable_date()))
                    .map(WeatherForecastDayDto::getWeather_state_name)
                    .findFirst()
                    .orElse(EMPTY_STRING);
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
