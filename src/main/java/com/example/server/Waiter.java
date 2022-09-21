package com.example.server;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class Waiter {
    Server server;

    int timeout;

    int key;

    List<String> phrases;

    public void sendMessage() {
        new Thread(() -> {
            int seconds = 0;
            while (true) {
                seconds += 1;
                if (seconds == timeout) {
                    server.sendMessage(this.key, "hey");
                    seconds = 0;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
