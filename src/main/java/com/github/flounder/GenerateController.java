package com.github.flounder;

import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Security;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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

        // FIXME: Color change is incorrect
        if (identity.equals("")) {
            identityLabel.setTextFill(Color.web("#E51C17"));
            return;
        } else {
            identityLabel.setTextFill(Color.BLACK);
        }
        if (passphrase.equals("")) {
            passphraseLabel.setTextFill(Color.web("#E51C17"));
            return;
        } else {
            identityLabel.setTextFill(Color.BLACK);
        }

        Security.addProvider(new BouncyCastleProvider());

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", "BC");
        kpg.initialize(4096);
        KeyPair kp = kpg.generateKeyPair();

        // TODO: Custom file output path
        FileOutputStream out1 = new FileOutputStream("sec.asc");
        FileOutputStream out2 = new FileOutputStream("pub.asc");

        // TODO: Create a separate process
        RSAKeyPairGenerator.exportKeyPair(out1, out2, kp, identity, passphrase.toCharArray(), true);
    }

    @FXML
    void onCancelBtnClick(ActionEvent event) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
}
