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
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + "GenerateView" + ".fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setAlwaysOnTop(true);
            stage.setTitle("Generate Keys");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onEncryptTextBtnClick(ActionEvent event) {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + "EncryptView" + ".fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setAlwaysOnTop(true);
            stage.setTitle("Encrypt Text");
            stage.setScene(new Scene(root, 1024, 768));
            EncryptController controller = loader.<EncryptController>getController();
            controller.initData("Encrypt Text");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onEncryptFileBtnClick(ActionEvent event) {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + "EncryptView" + ".fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setAlwaysOnTop(true);
            stage.setTitle("Encrypt File");
            stage.setScene(new Scene(root, 1024, 768));
            EncryptController controller = loader.<EncryptController>getController();
            controller.initData("Encrypt File");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onDecryptTextBtnClick(ActionEvent event) {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + "DecryptView" + ".fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setAlwaysOnTop(true);
            stage.setTitle("Decrypt Text");
            stage.setScene(new Scene(root, 1024, 768));
            DecryptController controller = loader.<DecryptController>getController();
            controller.initData("Decrypt Text");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onDecryptFileBtnClick(ActionEvent event) {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + "DecryptView" + ".fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setAlwaysOnTop(true);
            stage.setTitle("Decrypt File");
            stage.setScene(new Scene(root, 1024, 768));
            DecryptController controller = loader.<DecryptController>getController();
            controller.initData("Decrypt File");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onExitBtnClick(ActionEvent event) {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }
}
