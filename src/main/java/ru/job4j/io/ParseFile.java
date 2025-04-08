package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized File getFile() {
        return file;
    }

    private String getContent(Predicate<Integer> filter) throws IOException {
        StringBuilder result = new StringBuilder();
        try (BufferedReader input = new BufferedReader(new FileReader(file))) {
            int data;
            while ((data = input.read()) != -1) {
                if (filter.test(data)) {
                    result.append((char) data);
                }
            }
        }
        return result.toString();
    }

    public String getContent() throws IOException {
        return getContent(i -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContent(i -> i < 0x80);
    }
}