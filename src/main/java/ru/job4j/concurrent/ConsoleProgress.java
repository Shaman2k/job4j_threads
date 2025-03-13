package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        var process = new char[]{'-', '\\', '|', '/'};
        int counter = 0;
        while (!Thread.currentThread().isInterrupted()) {
            System.out.print("\r load: " + process[counter++]);
            if (counter >= process.length) {
                counter = 0;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new ConsoleProgress());
        thread.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        thread.interrupt();
    }
}
