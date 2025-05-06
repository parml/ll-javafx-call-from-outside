package com.example;

import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.Random;

/// - Call JavaFX application from non JavaFX component
/// - Pass data to the JavaFX application at creation time
/// - Receive data from the JavaFX application by means of callback
/// - Run multiple instances of that application
public class Main {
    Gui gui;
    public static void main(String[] args) {
        new Main().work();
    }

    private void work() {
        // Do not let platform exit when last window closed, important for running multiple instances of same JavaFX application, `Gui` in this case
        Platform.setImplicitExit(false);

        // Initialize platform/toolkit
        Platform.startup(() -> {});

        // Launch first instance of the app. Subsequent instances launched after previous finishes job
        launchAppInstance();
    }

    private void launchAppInstance() {
        gui = new Gui(this, randInt(), randInt());
        Platform.runLater(() -> {
            try {
                System.out.println("Launching new Application instance");
                gui.start(new Stage());

                // Not essential, just a stop launching more instances of the gui application counter
                launchesSoFar++;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private int randInt() {
        return new Random().nextInt();
    }

    public void reportResult(int result) throws InterruptedException {
        System.out.println("got result from gui app: " + result);

        System.out.println("Closing instance of JavaFX application");
        try {
            gui.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // Decide if this was the last instance of the application or you need to launch another.
        if (launchesSoFar >= MAX_LAUNCHES) {
            Platform.exit();
        } else {
            launchAppInstance();
        }
    }
    static final int MAX_LAUNCHES = 3;
    int launchesSoFar = 0;

}
