package training.weather.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;


@Service
public class WeatherForecastService implements IWeatherForecastService {

    @Override
    public String getCityWeather(final String city, final LocalDate day) throws IOException {
        if (day.isBefore(LocalDate.now().plusDays(6))) {
            HttpRequestFactory rf = new NetHttpTransport().createRequestFactory();
            HttpRequest req = rf
                .buildGetRequest(new GenericUrl("https://www.metaweather.com/api/location/search/?query=" + city));
            String r = req.execute().parseAsString();
            JSONArray array = new JSONArray(r);
            String woe = array.getJSONObject(0).get("woeid").toString();
            rf = new NetHttpTransport().createRequestFactory();
            req = rf.buildGetRequest(new GenericUrl("https://www.metaweather.com/api/location/" + woe));
            r = req.execute().parseAsString();
            JSONArray results = new JSONObject(r).getJSONArray("consolidated_weather");
            for (int i = 0; i < results.length(); i++) {
                if (day.equals(LocalDate.parse(results.getJSONObject(i).get("applicable_date").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))) {
                    return results.getJSONObject(i).get("weather_state_name").toString();
                }
            }
        }
        return "";
    }
    
}
