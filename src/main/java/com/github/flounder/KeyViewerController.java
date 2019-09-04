package com.github.flounder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class KeyViewerController {

    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXButton exitBtn;

    @FXML
    private JFXTextArea publicKeyArea;

    @FXML
    private JFXTextArea privateKeyArea;

    @FXML
    public void initialize() {
        try {
            saveBtn.setDisable(false);
            File publicKey = new File("pub.asc");
            File privateKey = new File("sec.asc");
            publicKeyArea.setText(Files.readString(publicKey.toPath()));
            privateKeyArea.setText(Files.readString(privateKey.toPath()));
        } catch (Exception e) {
            publicKeyArea.setText("Failed to generate key");
            privateKeyArea.setText("Failed to generate key");
            saveBtn.setDisable(true);
        }
    }

    @FXML
    void onSaveBtnClick(ActionEvent event) {
        File publicKey = new File("pub.asc");
        File privateKey = new File("sec.asc");

        Helper helper = new Helper();
        helper.saveFile(publicKey, "asc", "Public Key", saveBtn.getScene().getWindow());
        helper.saveFile(privateKey, "asc", "Private Key", saveBtn.getScene().getWindow());
    }

    @FXML
    void onExitBtnClick(ActionEvent event) {
        try {
            Files.deleteIfExists(Paths.get("pub.asc"));
            Files.deleteIfExists(Paths.get("sec.asc"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }
}
