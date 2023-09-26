package ua.foxminded.javaspring.universityschedule.dto;

import lombok.Data;

@Data
public class UserDTO {

    private long id;
    private String username;
    private char[] password;
    private String firstName;
    private String lastName;
    private String email;
}
