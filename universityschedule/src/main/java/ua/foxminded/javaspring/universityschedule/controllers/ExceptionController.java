package ua.foxminded.javaspring.universityschedule.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.foxminded.javaspring.universityschedule.exceptions.PasswordNotMatchException;

import java.sql.SQLException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(SQLException.class)
    public String psqlException(SQLException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/errorPage";
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public String passwordNotMatch(PasswordNotMatchException e, RedirectAttributes attr) {
        attr.addFlashAttribute("error", true);
        attr.addFlashAttribute("message", e.getMessage());
        return "redirect:/profile/editPassword";
    }
}
