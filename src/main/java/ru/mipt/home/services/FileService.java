package ru.mipt.home.services;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

@Component
public class FileService {

    public void createIfNotExists(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                Files.createDirectories(file.toPath().getParent());
                Files.createFile(file.toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(String filePath, String content) {
        try {
            Files.write(Path.of(filePath), (content + "\n").getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<String> readFile(String filePath) {
        try {
            return Optional.of(Files.readString(Path.of(filePath)));
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
            return Optional.empty();
        }
    }
}
