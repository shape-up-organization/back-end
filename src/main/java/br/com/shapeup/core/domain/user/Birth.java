package br.com.shapeup.core.domain.user;

import br.com.shapeup.common.domain.ValueObject;
import br.com.shapeup.common.exceptions.user.UserInvalidBirthException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class Birth extends ValueObject {
    public static final DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            .toFormatter();
    private LocalDate value;

    private Birth(LocalDate value) {
        this.value = value;
    }

    public static Birth create(String birth) throws ParseException {
        LocalDate localDate = LocalDate.parse(birth, DATE_FORMATTER);
        validateBirth(localDate);
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

    public static void validateBirth(LocalDate birth) {
        if (birth.isAfter(LocalDate.now())) {
            throw new UserInvalidBirthException(birth.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }

        if (birth.isBefore(LocalDate.now().minusYears(120))) {
            throw new UserInvalidBirthException(birth.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }

        if (birth.isAfter(LocalDate.now().minusYears(18))) {
            throw new UserInvalidBirthException();
        }
    }

    public static LocalDate convertBirth(String birth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("pt", "BR"));
        return LocalDate.parse(birth, formatter);
    }
}
