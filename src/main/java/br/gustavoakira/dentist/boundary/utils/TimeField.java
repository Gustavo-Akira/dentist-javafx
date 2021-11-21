package br.gustavoakira.dentist.boundary.utils;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.DateTimeStringConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeField extends TextField {
    @Override
    public void replaceText(int start, int end, String text) {
        if (!text.matches("[a-z]")) {
            super.replaceText(start, end, text);
        }
    }

    public TimeField() throws ParseException {
        super();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        setTextFormatter(new TextFormatter<>(new DateTimeStringConverter(format), format.parse("00:00:00")));
    }
}
