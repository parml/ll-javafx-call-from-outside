package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This Application's job is to compute the sum of two numbers and allow the user to report the result to the outside of this application by pressing a button.
 * This Application closes when its job is done (result reported)
 */
public class Gui extends Application {
    final Main caller;
    // Data passed from outside
    final int operand1;
    final int operand2;

    Stage stage;

    Gui(Main caller, int operand1, int operand2) {
        this.caller = caller;
        this.operand1 = operand1;
        this.operand2 = operand2;
    }



    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;

        Button reportResult = new Button("Report Result");
        Label op1 = new Label("" + operand1);
        Label plus = new Label(" + ");
        Label op2 = new Label("" + (operand2 >= 0 ? operand2 : "(" + operand2 + ")"));
        Label eq = new Label(" = ");
        Label res = new Label("" + getResult());
        HBox hBox = new HBox(reportResult, op1, plus, op2, eq, res);
        VBox vbox = new VBox(hBox, reportResult);
        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Report data to the outside of the JavaFX application
        reportResult.setOnAction(event -> {
            try {
                caller.reportResult(getResult());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void stop() throws Exception {
        // This is the last window being closed.
        // By default, the JavaFX platform will exit when that happens. We intend to open other windows, that is why `Platform.setImplicitExit(false)` was called earlier in the code.
        stage.close();
    }

    private int getResult() {
        return operand1 + operand2;
    }
}
