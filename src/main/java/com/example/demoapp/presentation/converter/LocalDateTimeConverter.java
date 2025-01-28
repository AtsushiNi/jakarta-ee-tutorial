package com.example.demoapp.presentation.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "localDateTimeConverter")
public class LocalDateTimeConverter implements Converter<LocalDateTime> {
    @Override
    public LocalDateTime getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        String pattern = (String) component.getAttributes().get("pattern");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern != null ? pattern : "yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(value, formatter);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, LocalDateTime value) {
        if (value == null) {
            return "";
        }
        String pattern = (String) component.getAttributes().get("pattern");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern != null ? pattern : "yyyy-MM-dd HH:mm:ss");
        return value.format(formatter);
    }
}
