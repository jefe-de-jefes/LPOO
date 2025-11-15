package com.gympos.util;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Formatters {

    private static final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("es", "MX"));

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String formatCurrency(double amount) {
        return currencyFormatter.format(amount);
    }


    public static String formatDate(LocalDate date) {
        if (date == null) return "";
        return date.format(dateFormatter);
    }
}
