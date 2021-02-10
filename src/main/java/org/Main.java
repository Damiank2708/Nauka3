package org;

import client.JsonPlaceHolderClient;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ProgramCore programCore = new ProgramCore();
        programCore.repeatGetUsersWithTaskSchedule(10);
    }
}
