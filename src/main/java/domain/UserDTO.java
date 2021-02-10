package domain;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private long id;
    private String name;
    private String username;
    private String email;
    private List<UserTask> task;
}

