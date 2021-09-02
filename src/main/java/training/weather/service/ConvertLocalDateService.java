package training.weather.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class ConvertLocalDateService implements IConvertLocalDateService {

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

}
