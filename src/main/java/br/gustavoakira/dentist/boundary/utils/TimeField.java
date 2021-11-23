package br.gustavoakira.dentist.boundary.utils;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.DateTimeStringConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeField extends TextField {

    public TimeField() {
        super();
        setText("00:00:00");
    }

    @Override
    public void replaceText(int start, int end, String text) {
        if (!text.matches("[a-z]")) {
            super.replaceText(start, end, text);
        }
    }
}
