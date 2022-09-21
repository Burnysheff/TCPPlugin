package com.example.server.plugin;

import org.bukkit.command.CommandSender;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private final CommandSender commandSender;

    private final Socket socket;

    private final BufferedReader bufferedReader;

    public Client(CommandSender commandSender) {
        try {
            this.commandSender = commandSender;
            InetAddress host = InetAddress.getByName("localhost");
            socket = new Socket(host, Integer.parseInt(System.getenv("port")));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write("5");
            bufferedWriter.newLine();
            bufferedWriter.flush();
            this.receiveMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void receiveMessage() {
        new Thread(() -> {
            while (this.socket.isConnected()) {
                try {
                    String phrase = this.bufferedReader.readLine();
                    commandSender.sendMessage(phrase);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public void close() {
        try {
            this.bufferedReader.close();
            this.socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
