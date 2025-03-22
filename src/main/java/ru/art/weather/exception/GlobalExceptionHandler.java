package ru.art.weather.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.art.weather.dto.RegistrationDto;
import ru.art.weather.dto.UserLoginDto;
import ru.art.weather.util.DataValidator;

@ControllerAdvice
public class GlobalExceptionHandler {

    DataValidator dataValidator;

    public GlobalExceptionHandler(DataValidator dataValidator) {
        this.dataValidator = dataValidator;
    }

    @ExceptionHandler(IncorrectDataException.class)
    public String handleIncorrectDataException(IncorrectDataException e, HttpServletRequest request, Model model) {
        String referer = request.getHeader("Referer");

        if (referer != null && referer.contains("/registration")) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("registrationDto", new RegistrationDto());
            return "registration";

        } else if (referer != null && referer.contains("/login")) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("userLoginDto", new UserLoginDto());

            return "login";
        }

        return "error-page";
    }

    @ExceptionHandler(DataAlreadyExistsException.class)
    public String handleDataAlreadyExists(DataAlreadyExistsException e, HttpServletRequest request, Model model) {
        String referer = request.getHeader("Referer");

        if (referer != null && referer.contains("/registration")) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("registrationDto", new RegistrationDto());
            return "registration";
        }

        return "error-page";
    }

    @ExceptionHandler(DataNotFoundException.class)
    public String handleDataNotFoundException(DataNotFoundException e, HttpServletRequest request, Model model) {
        String referer = request.getHeader("Referer");
        if (referer != null && referer.contains("/login")) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("userLoginDto", new UserLoginDto());
            return "login";
            //tODO
        } else if (referer != null && referer.contains("/main-page")) {
            model.addAttribute("errorMessage", e.getMessage());

        } else if (referer != null && referer.contains("/search-results")) {
            return "redirect:/login";
        }

        return "error-page";
    }

    @ExceptionHandler(DataBaseException.class)
    public String handleDataBaseException(DataBaseException e) {
        return "error-page";
    }
}
