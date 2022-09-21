package com.example.server;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ServerTest {

    Server server;

    @BeforeEach
    public void setup() {
        server = new Server();
    }

    @Test
    public void checkPort() {
        assertEquals(server.port, 1234);
    }

    @Test
    public void checkPhrases() {
        assertEquals(server.phrases.get(0), "Те́кстовый файл — компьютерный файл, содержащий текстовые данные, как правило, организованные в виде строк.");
    }
}
