package br.com.shapeup.core.domain.user;

import br.com.shapeup.common.domain.ValueObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Birth extends ValueObject {
    private LocalDate value;

    private Birth(LocalDate value) {
        this.value = value;
    }

    public static Birth create(String birth) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(birth, formatter);
        return Birth.create(localDate);
    }

    public static Birth create(LocalDate date) {
        return new Birth(date);
    }

    public LocalDate getValue() {
        return value;
    }

    public void setValue(LocalDate value) {
        this.value = value;
    }
}
