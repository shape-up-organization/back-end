package br.com.shapeup.core.domain.user;

import br.com.shapeup.common.domain.ValueObject;
import br.com.shapeup.common.exceptions.user.UserInvalidBirthException;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    public void validateBirth() {
        if (value.isAfter(LocalDate.now())) {
            throw new UserInvalidBirthException(value.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }

        if (value.isBefore(LocalDate.now().minusYears(120))) {
            throw new UserInvalidBirthException(value.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }

        if (value.isAfter(LocalDate.now().minusYears(18))) {
            throw new UserInvalidBirthException();
        }
    }
}
