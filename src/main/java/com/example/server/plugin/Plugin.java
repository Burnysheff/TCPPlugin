package com.example.server.plugin;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Plugin extends JavaPlugin {
    private Client client;

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("phrase")).setExecutor((commandSender, command, s, strings) -> {
            client = new Client(commandSender);
            return true;
        });
    }

    @Override
    public void onDisable() {
        client.close();
    }
}
