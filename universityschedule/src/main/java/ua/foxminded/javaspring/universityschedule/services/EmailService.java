package ua.foxminded.javaspring.universityschedule.services;

public interface EmailService {

    void sendEmail(String to, String subject, String body);
}
