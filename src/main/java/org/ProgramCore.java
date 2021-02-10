package org;

import org.timertask.GetUserTimerTask;

import java.util.Timer;

public class ProgramCore {
    Timer timer = new Timer();

    public void repeatGetUsersWithTaskSchedule(int seconuds){
          timer.scheduleAtFixedRate(new GetUserTimerTask(), 0, seconuds*1000);
        }
}
