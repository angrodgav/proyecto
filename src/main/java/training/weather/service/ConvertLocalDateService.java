package training.weather.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class ConvertLocalDateService implements IConvertLocalDateService {

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public LocalDate convertDateToLocalDateNullSafe(final Date date) {
        LocalDate day;

        if (date == null) {
            day = LocalDate.now();
        } else {
            day = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }

        return day;
    }

    @Override
    public boolean sameDay(final LocalDate day, final String dayString) {
        if (day == null || dayString == null) {
            return false;
        }

        return day.equals(LocalDate.parse(dayString, DateTimeFormatter.ofPattern(ConvertLocalDateService.DATE_FORMAT)));
    }

}
