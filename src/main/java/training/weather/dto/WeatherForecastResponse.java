package training.weather.dto;

import java.util.List;

public class WeatherForecastResponse {

    private List<WeatherForecastDayDto> consolidated_weather;

    public List<WeatherForecastDayDto> getConsolidated_weather() {
        return consolidated_weather;
    }

    public void setConsolidated_weather(final List<WeatherForecastDayDto> consolidated_weather) {
        this.consolidated_weather = consolidated_weather;
    }
}
