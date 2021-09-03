package training.weather.dto;

public class WeatherForecastDayDto {

    private String weather_state_name;
    private String applicable_date;

    public String getWeather_state_name() {
        return weather_state_name;
    }
    public void setWeather_state_name(final String weather_state_name) {
        this.weather_state_name = weather_state_name;
    }
    public String getApplicable_date() {
        return applicable_date;
    }
    public void setApplicable_date(final String applicable_date) {
        this.applicable_date = applicable_date;
    }
}
