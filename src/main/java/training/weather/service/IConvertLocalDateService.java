package training.weather.service;

import java.time.LocalDate;
import java.util.Date;

public interface IConvertLocalDateService {
    LocalDate convertDateToLocalDateNullSafe(Date date);
}
