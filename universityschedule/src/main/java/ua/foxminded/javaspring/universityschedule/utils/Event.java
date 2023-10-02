package ua.foxminded.javaspring.universityschedule.utils;

import java.time.LocalDateTime;

public interface Event {

    long getId();

    String getTitle();

    LocalDateTime getStart();

    LocalDateTime getEnd();
}
