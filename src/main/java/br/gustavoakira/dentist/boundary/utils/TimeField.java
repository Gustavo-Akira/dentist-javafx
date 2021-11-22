package br.gustavoakira.dentist.boundary.utils;

import javafx.scene.control.TextField;

public class TimeField extends TextField {
    @Override
    public void replaceText(int start, int end, String text) {
        if (!text.matches("[a-z]")) {
            super.replaceText(start, end, text);
        }
    }

}
