package training.weather.dto.enums;

public enum WeatherState {
    SNOW("Snow"),
    SLEET("Sleet"),
    HAIL("Hail"),
    THUNDERSTORM("Thunderstorm"),
    HEAVY_RAIN("Heavy Rain"),
    LIGHT_RAIN("Light Rain"),
    SHOWERS("Showers"),
    HEAVY_CLOUD("Heavy Cloud"),
    LIGHT_CLOUD("Light Cloud"),
    CLEAR("Clear");

    private String weatherStateName;

    WeatherState(final String weatherStateName) {
        this.weatherStateName = weatherStateName;
    }

    public static WeatherState getWeatherStateFromName(final String weatherStateNameQuery) {
        for (WeatherState weatherState:WeatherState.values()) {
            if (weatherState.getWeatherStateName().equals(weatherStateNameQuery)) {
                return weatherState;
            }
        }
        return null;
    }

    public String getWeatherStateName() {
        return weatherStateName;
    }
}
