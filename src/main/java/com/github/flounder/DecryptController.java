package com.github.flounder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Security;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPException;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DecryptController {

    @FXML
    private Label keyLabel;

    @FXML
    private Label passphraseLabel;

    @FXML
    private Label textLabel;

    @FXML
    private Label fileLabel;

    @FXML
    private JFXTextField keyTextField;

    @FXML
    private JFXButton keyChooseBtn;

    @FXML
    private JFXPasswordField passphraseField;

    @FXML
    private JFXTextArea textArea;

    @FXML
    private JFXTextField fileTextField;

    @FXML
    private JFXButton fileChooseBtn;

    @FXML
    private JFXButton decryptBtn;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    public void initialize() {

    }

    void initData(String string) {
        if (string.equals("Decrypt Text")) {
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
        File file = helper.chooseFile("asc", "Private Key", keyChooseBtn.getScene().getWindow());
        if (file != null) {
            keyTextField.setText(file.toPath().toString());
        }
    }

    @FXML
    void onFileChooseBtnClick(ActionEvent event) {
        Helper helper = new Helper();
        File file = helper.chooseFile("bpg", "File", fileChooseBtn.getScene().getWindow());
        if (file != null) {
            fileTextField.setText(file.toPath().toString());
        }
    }

    @FXML
    void onDecryptBtnClick(ActionEvent event) {
        String key = keyTextField.getText();
        String passphrase = passphraseField.getText();
        String text = textArea.getText();
        String file = fileTextField.getText();

        keyLabel.setTextFill(Color.BLACK);
        passphraseLabel.setTextFill(Color.BLACK);

        if (key.equals("")) {
            keyLabel.setTextFill(Color.web("#E51C17"));
            return;
        }

        if (!key.equals("") && passphrase.equals("")) {
            passphraseLabel.setTextFill(Color.web("#E51C17"));
            return;
        }

        if (fileLabel.getTextFill().equals(Color.GRAY)) { // Decrypt Text
            if (text.equals("")) {
                textLabel.setTextFill(Color.web("#E51C17"));
                return;
            } else {
                textLabel.setTextFill(Color.BLACK);

                try {
                    textArea.setText(KeyBasedTextProcessor.decryptText(text, key, passphrase));
                } catch (PGPException e) {
                    if (e.toString().contains("PGPSecretKeyRing expected")) {
                        keyLabel.setTextFill(Color.web("#E51C17"));
                        passphraseLabel.setTextFill(Color.BLACK);
                    } else {
                        keyLabel.setTextFill(Color.BLACK);
                        passphraseLabel.setTextFill(Color.web("#E51C17"));
                    }
                    return;
                } catch (IOException e) {
                    fileLabel.setTextFill(Color.web("#E51C17"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (textLabel.getTextFill().equals(Color.GRAY)) { // Decrypt File
            if (file.equals("")) {
                fileLabel.setTextFill(Color.web("#E51C17"));
                return;
            } else {
                fileLabel.setTextFill(Color.BLACK);

                Security.addProvider(new BouncyCastleProvider());

                decryptBtn.setDisable(true);
                cancelBtn.setDisable(true);
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() {
                        try {
                            String originalName = new File(file).getName().substring(0,
                                    new File(file).getName().length() - 4);

                            KeyBasedFileProcessor.decryptFile(file, key, passphrase.toCharArray(), originalName);

                            Platform.runLater(() -> {
                                Helper helper = new Helper();
                                File outputFile = new File(originalName);
                                helper.saveFile(outputFile, originalName.substring(0, originalName.lastIndexOf('.')),
                                        originalName.substring(originalName.lastIndexOf('.') + 1), "Output File",
                                        decryptBtn.getScene().getWindow());

                                decryptBtn.setDisable(false);
                                cancelBtn.setDisable(false);

                                try {
                                    Files.deleteIfExists(outputFile.toPath());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                        } catch (PGPException e) {
                            Platform.runLater(() -> {
                                decryptBtn.setDisable(false);
                                cancelBtn.setDisable(false);
                                if (e.toString().contains("PGPSecretKeyRing expected")) {
                                    keyLabel.setTextFill(Color.web("#E51C17"));
                                    passphraseLabel.setTextFill(Color.BLACK);
                                } else {
                                    keyLabel.setTextFill(Color.BLACK);
                                    passphraseLabel.setTextFill(Color.web("#E51C17"));
                                }
                            });
                        } catch (IOException e) {
                            Platform.runLater(() -> {
                                decryptBtn.setDisable(false);
                                cancelBtn.setDisable(false);
                                fileLabel.setTextFill(Color.web("#E51C17"));
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };
                new Thread(task).start();
            }
        }
    }

    @FXML
    void onCancelBtnClick(ActionEvent event) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
}
