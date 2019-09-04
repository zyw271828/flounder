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
    private JFXButton encryptTextBtn;

    @FXML
    private JFXButton encryptFileBtn;

    @FXML
    private JFXButton decryptTextBtn;

    @FXML
    private JFXButton decryptFileBtn;

    @FXML
    private JFXButton exitBtn;

    @FXML
    public void initialize() {

    }

    @FXML
    void onGenerateBtnClick(ActionEvent event) {
        showStage("GenerateView", "Generate Keys", 600, 400);
    }

    @FXML
    void onEncryptTextBtnClick(ActionEvent event) {
        showStage("EncryptView", "Encrypt Text", 1024, 768);
    }

    @FXML
    void onEncryptFileBtnClick(ActionEvent event) {
        showStage("EncryptView", "Encrypt File", 1024, 768);
    }

    @FXML
    void onDecryptTextBtnClick(ActionEvent event) {
        showStage("DecryptView", "Decrypt Text", 1024, 768);
    }

    @FXML
    void onDecryptFileBtnClick(ActionEvent event) {
        showStage("DecryptView", "Decrypt File", 1024, 768);
    }

    @FXML
    void onExitBtnClick(ActionEvent event) {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }

    private void showStage(String viewName, String stageTitle, double width, double height) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/" + viewName + ".fxml"));
            Stage stage = new Stage();
            stage.setAlwaysOnTop(true);
            stage.setTitle(stageTitle);
            stage.setScene(new Scene(root, width, height));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
