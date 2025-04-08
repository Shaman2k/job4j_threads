package ru.job4j.io;

import java.io.*;

public class FileWriter {
    private final File file;

    public FileWriter(File file) {
        this.file = file;
    }

    public void saveContent(String content) throws IOException {
        try (BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < content.length(); i++) {
                output.write(content.charAt(i));
            }
        }
    }
}
