package ru.job4j.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String filename;

    public Wget(String url, int speed, String filename) {
        this.url = url;
        this.speed = speed;
        this.filename = filename;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        var file = new File(filename);
        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[512];
            int bytesRead;
            int dowloaded = 0;
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                output.write(dataBuffer, 0, bytesRead);
                dowloaded += bytesRead;
                if (dowloaded >= speed) {
                    long interval = System.currentTimeMillis() - startAt;
                    if (interval < 1000) {
                        Thread.sleep(1000 - interval);
                        System.out.printf("Read %d bytes : %d ms\n", dowloaded, interval);
                    }
                    dowloaded = 0;
                    startAt = System.currentTimeMillis();
                }
            }
            System.out.println(Files.size(file.toPath()) + " bytes");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void validate(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Must be 3 args");
        }
        try {
            new URL(args[0]).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URL");
        }
        if (Integer.parseInt(args[1]) < 1) {
            throw new IllegalArgumentException("Speed must be above zero");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String filename = args[2];
        Thread wget = new Thread(new Wget(url, speed, filename));
        wget.start();
        wget.join();
    }
}