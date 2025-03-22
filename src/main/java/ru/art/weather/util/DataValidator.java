package ru.art.weather.util;

import org.springframework.stereotype.Component;
import ru.art.weather.exception.DataBaseException;
import ru.art.weather.exception.IncorrectDataException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DataValidator {

    public void validateName(String name) {
        String regex = "^[a-zA-Zа-яА-Я0-9_]{3,20}$";

        Matcher matcher = Pattern.compile(regex).matcher(name);
        if (!matcher.matches()) {
            throw new IncorrectDataException("Login incorrect");
        }
    }

    public void validatePasswords(String password , String rePassword) {
        if (!password.equals(rePassword)) {
            throw new IncorrectDataException("Passwords do not match");
        }
        validatePassword(password);
    }

    public void validatePassword(String password) {
        String regex = "^[a-zA-Zа-яА-Я0-9\\W_]{6,20}$";

        Matcher matcher = Pattern.compile(regex).matcher(password);
        if (!matcher.matches()) {
            throw new IncorrectDataException("Password incorrect");
        }
    }
}
