package domain;

import lombok.Data;

@Data
public class UserTask {
    private long userId;
    private long id;
    private String title;
    private boolean completed;
}
