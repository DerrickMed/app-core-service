package ru.sbrf.sell.appcore.dto;

import lombok.Data;
import ru.sbrf.sell.appcore.model.Role;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    private Long id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String username;
    private Role role;
}