package com.github.flounder;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private JFXButton generateBtn;

    @FXML
    public void initialize() {

    }

    @FXML
    void onGenerateBtnClick(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/GenerateView.fxml"));
            Stage stage = new Stage();
            stage.setAlwaysOnTop(true);
            stage.setTitle("Generate Keys");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
