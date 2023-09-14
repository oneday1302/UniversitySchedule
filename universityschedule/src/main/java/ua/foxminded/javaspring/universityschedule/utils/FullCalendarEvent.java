package ua.foxminded.javaspring.universityschedule.utils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FullCalendarEvent implements Event {

    private final Event event;

    @Override
    public long getId() {
        return event.getId();
    }

    @Override
    public String getTitle() {
        return event.getTitle();
    }

    @Override
    public String getStart() {
        return event.getStart();
    }

    @Override
    public String getEnd() {
        return event.getEnd();
    }
}
