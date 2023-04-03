package br.com.shapeup.core.domain.user;

import br.com.shapeup.common.domain.ValueObject;
import br.com.shapeup.common.exceptions.user.UserInvalidBirthException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Birth extends ValueObject {
    private LocalDate value;

    private Birth(LocalDate value) {
        this.value = value;
    }

    public static Birth create(String birth) throws ParseException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(birth, formatter);

            return Birth.create(localDate);

        } catch (Exception e) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(birth, inputFormatter);
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = localDate.format(outputFormatter);

            return Birth.create(formattedDate);
        }
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
