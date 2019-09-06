package com.github.flounder;

import java.io.File;
import java.nio.file.Files;
import java.security.Security;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class EncryptController {

    @FXML
    private Label keyLabel;

    @FXML
    private Label textLabel;

    @FXML
    private Label fileLabel;

    @FXML
    private JFXTextField keyTextField;

    @FXML
    private JFXButton keyChooseBtn;

    @FXML
    private JFXTextArea textArea;

    @FXML
    private JFXTextField fileTextField;

    @FXML
    private JFXButton fileChooseBtn;

    @FXML
    private JFXButton encryptBtn;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    public void initialize() {

    }

    void initData(String string) {
        if (string.equals("Encrypt Text")) {
            fileLabel.setTextFill(Color.GRAY);
            fileTextField.setDisable(true);
            fileChooseBtn.setDisable(true);
        } else {
            textLabel.setTextFill(Color.GRAY);
            textArea.setDisable(true);
        }
    }

    @FXML
    void onKeyChooseBtnClick(ActionEvent event) {
        Helper helper = new Helper();
        File file = helper.chooseFile("asc", "Public Key", keyChooseBtn.getScene().getWindow());
        if (file != null) {
            keyTextField.setText(file.toPath().toString());
        }
    }

    @FXML
    void onFileChooseBtnClick(ActionEvent event) {
        Helper helper = new Helper();
        File file = helper.chooseFile("*", "File", fileChooseBtn.getScene().getWindow());
        if (file != null) {
            fileTextField.setText(file.toPath().toString());
        }
    }

    @FXML
    void onEncryptBtnClick(ActionEvent event) {
        String key = keyTextField.getText();
        String text = textArea.getText();
        String file = fileTextField.getText();

        if (key.equals("")) {
            keyLabel.setTextFill(Color.web("#E51C17"));
            return;
        }

        if (fileLabel.getTextFill().equals(Color.GRAY)) { // Encrypt Text
            if (text.equals("")) {
                textLabel.setTextFill(Color.web("#E51C17"));
                return;
            } else {
                // TODO: Encrypt Text
            }
        } else if (textLabel.getTextFill().equals(Color.GRAY)) { // Encrypt File
            if (file.equals("")) {
                fileLabel.setTextFill(Color.web("#E51C17"));
                return;
            } else {
                Security.addProvider(new BouncyCastleProvider());

                try {
                    KeyBasedFileProcessor.encryptFile(file + ".bpg", file, key, false, true);

                    Helper helper = new Helper();
                    File outputFile = new File(file + ".bpg");
                    helper.saveFile(outputFile, new File(file).getName(), "bpg", "Output File",
                            encryptBtn.getScene().getWindow());

                    Files.deleteIfExists(outputFile.toPath());
                } catch (PGPException e) {
                    // TODO: Show "PGPSecretKeyRing found where PGPPublicKeyRing expected"
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    void onCancelBtnClick(ActionEvent event) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
}
