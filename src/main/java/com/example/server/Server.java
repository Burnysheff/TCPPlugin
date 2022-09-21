package com.example.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Server {
    Random random = new Random();

    public final int port = Integer.parseInt(System.getenv("port"));

    private final List<Waiter> waiterList = new ArrayList<>();

    private final List<BufferedWriter> writers = new ArrayList<>();

    public final List<String> phrases;

    public Server() {
        try (Stream<String> lines = Files.lines(Paths.get("phrases.txt"))) {
            phrases = lines.collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        new Thread(() -> {
            while (true) {
                try {
                    ServerSocket serverSocket = new ServerSocket(port);
                    Socket socket = serverSocket.accept();
                    serverSocket.close();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    String timeout = reader.readLine();
                    System.out.println(timeout);
                    int time;
                    if (timeout.equals("random")) {
                        time = random.nextInt(1, 11);
                    } else {
                        time = Integer.parseInt(timeout);
                    }
                    Waiter waiter = new Waiter(this, time, waiterList.size(), phrases);
                    waiterList.add(waiter);
                    writers.add(writer);
                    waiter.sendMessage();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public void sendMessage(int destination, String phrase) {
        try {
            writers.get(destination).write(phrase);
            writers.get(destination).newLine();
            writers.get(destination).flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
