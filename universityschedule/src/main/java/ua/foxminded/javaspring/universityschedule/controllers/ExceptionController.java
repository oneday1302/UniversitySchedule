package ua.foxminded.javaspring.universityschedule.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(SQLException.class)
    public String psqlException(SQLException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/errorPage";
    }
}
