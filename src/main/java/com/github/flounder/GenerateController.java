package com.github.flounder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Security;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GenerateController {

    @FXML
    private Label identityLabel;

    @FXML
    private Label passphraseLabel;

    @FXML
    private JFXTextField identityField;

    @FXML
    private JFXPasswordField passphraseField;

    @FXML
    private JFXButton generateBtn;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    void onGenerateBtnClick(ActionEvent event) throws Exception {

        String identity = identityField.getText();
        String passphrase = passphraseField.getText();

        generateBtn.setDisable(true);
        cancelBtn.setDisable(true);
        identityLabel.setTextFill(Color.BLACK);
        passphraseLabel.setTextFill(Color.BLACK);
        if (identity.equals("")) {
            identityLabel.setTextFill(Color.web("#E51C17"));
            return;
        } else if (passphrase.equals("")) {
            passphraseLabel.setTextFill(Color.web("#E51C17"));
            return;
        }

        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                Security.addProvider(new BouncyCastleProvider());

                KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", "BC");
                kpg.initialize(4096);
                KeyPair kp = kpg.generateKeyPair();

                FileOutputStream out1 = new FileOutputStream("sec.asc");
                FileOutputStream out2 = new FileOutputStream("pub.asc");

                RSAKeyPairGenerator.exportKeyPair(out1, out2, kp, identity, passphrase.toCharArray(), true);
                return true;
            }
        };

        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent wse) {
                if (task.getValue()) {
                    Parent root;
                    try {
                        root = FXMLLoader.load(getClass().getResource("/KeyViewerView.fxml"));
                        Stage stage = new Stage();
                        stage.setAlwaysOnTop(true);
                        stage.setTitle("Key Viewer");
                        stage.setScene(new Scene(root, 1024, 768));
                        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                            @Override
                            public void handle(WindowEvent event) {
                                try {
                                    Files.deleteIfExists(Paths.get("pub.asc"));
                                    Files.deleteIfExists(Paths.get("sec.asc"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Stage stage = (Stage) generateBtn.getScene().getWindow();
                    stage.close();
                }
            }
        });

        new Thread(task).start();
    }

    @FXML
    void onCancelBtnClick(ActionEvent event) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
}
