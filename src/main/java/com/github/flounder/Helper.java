package com.github.flounder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javafx.stage.FileChooser;
import javafx.stage.Window;

public class Helper {
    public boolean saveFile(File file, String name, String extension, String description, Window window) {
        boolean isSuccess = false;

        if (file == null) {
            return false;
        } else {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save " + description);
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser.getExtensionFilters()
                    .addAll(new FileChooser.ExtensionFilter(extension + " file", "*." + extension));
            fileChooser.setInitialFileName(name + "." + extension);

            File targetFile = null;
            targetFile = fileChooser.showSaveDialog(window);
            if (targetFile == null) { // User closes the save dialog directly
                return false;
            } else { // Write file to targetFile, set isSuccess
                try {
                    Files.move(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    isSuccess = true;
                } catch (IOException e) {
                    isSuccess = false;
                    e.printStackTrace();
                }
            }
        }
        return isSuccess;
    }

    public File chooseFile(String extension, String description, Window window) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose " + description);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters()
                .addAll(new FileChooser.ExtensionFilter(extension + " file", "*." + extension));

        File targetFile = null;
        targetFile = fileChooser.showOpenDialog(window);
        if (targetFile == null) {
            return null;
        } else {
            return targetFile;
        }
    }
}
