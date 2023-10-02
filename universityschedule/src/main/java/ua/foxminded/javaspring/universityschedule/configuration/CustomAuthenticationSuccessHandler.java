package ua.foxminded.javaspring.universityschedule.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import ua.foxminded.javaspring.universityschedule.entities.Role;
import ua.foxminded.javaspring.universityschedule.services.UserService;
import ua.foxminded.javaspring.universityschedule.utils.CustomUserDetails;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        if (principal.getAuthorities().contains(Role.ADMIN)) {
            response.sendRedirect("/admin/home");
        } else if (principal.getAuthorities().contains(Role.TEACHER)) {
            response.sendRedirect("/teacher/home");
        } else {
            response.sendRedirect("/student/home");
        }
    }
}
