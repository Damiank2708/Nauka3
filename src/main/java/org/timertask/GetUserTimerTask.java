package org.timertask;

import client.JsonPlaceHolderClient;
import domain.UserDTO;

import java.io.IOException;
import java.util.List;
import java.util.TimerTask;

public class GetUserTimerTask extends TimerTask {
    @Override
    public void run() {
        JsonPlaceHolderClient jsonPlaceHolderClient = new JsonPlaceHolderClient();
        try {
            sweetPrint(jsonPlaceHolderClient.getUsersWithTasks());
        }
        catch (IOException ioException){
            System.out.println("Connection Error. Sorry.");
        }
    }

    private void sweetPrint(List<UserDTO> userDTOList){
        userDTOList.stream()
                .peek( s -> System.out.println( "User #" + s.getId() + " (" + s.getUsername() + ")") )
                .flatMap(s -> s.getTask().stream())
                .forEach(s -> System.out.println(String.format("Task: [%s] is %s.",
                                                        s.getTitle(),
                                                        s.isCompleted() ? "completed" : "not completed")));


    }
}
