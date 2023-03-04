package br.com.shapeup.core.domain.user;

import br.com.shapeup.common.domain.ValueObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Birth extends ValueObject {
    private Date value;
    private Birth(Date value) {
        this.value = value;
    }

    public static Birth create(String birth) throws ParseException {
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        return new Birth(date.parse(birth));
    }

    public static Birth create(Date date){
        return new Birth(date);
    }

    public Date getValue() {
        return value;
    }

    public void setValue(Date value) {
        this.value = value;
    }
}
