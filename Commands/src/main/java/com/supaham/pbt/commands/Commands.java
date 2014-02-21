package com.supaham.pbt.commands;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import pluginbase.bukkit.command.BukkitCommandProvider;
import pluginbase.bukkit.messaging.BukkitMessager;
import pluginbase.bukkit.permission.BukkitPermFactory;
import pluginbase.command.Command;
import pluginbase.command.CommandHandler;
import pluginbase.command.CommandProvider;
import pluginbase.command.QueuedCommand;
import pluginbase.logging.LoggablePlugin;
import pluginbase.logging.PluginLogger;
import pluginbase.messages.messaging.Messager;
import pluginbase.messages.messaging.Messaging;
import pluginbase.permission.PermFactory;

import java.io.File;
import java.util.Locale;

public class Commands extends JavaPlugin implements LoggablePlugin, Messaging, CommandProvider {

    private BukkitCommandProvider commandProvider;
    private CommandHandler commandHandler = null;
    private PluginLogger logger;

    static {
        PermFactory.registerPermissionFactory(BukkitPermFactory.class);
        PermFactory.registerPermissionName(Commands.class, "testcmds");
    }

    @Override
    public void onEnable() {
        logger = PluginLogger.getLogger(this);
        commandProvider = BukkitCommandProvider.getBukkitCommandProvider(this, "test");
        getCommandHandler().registerCommand(AwesomeCommand.class);
    }

    @Override
    public Messager getMessager() {
        return BukkitMessager.loadMessagerWithMessages(this, new File(getDataFolder(), "english.txt"),
                Locale.ENGLISH);
    }

    @NotNull
    @Override
    public PluginLogger getLog() {
        return logger;
    }

    @NotNull
    @Override
    public String getCommandPrefix() {
        return commandProvider.getCommandPrefix();
    }

    @NotNull
    @Override
    public CommandHandler getCommandHandler() {
        return commandProvider.getCommandHandler();
    }

    @Override
    public void scheduleQueuedCommandExpiration(@NotNull QueuedCommand queuedCommand) {
        commandProvider.scheduleQueuedCommandExpiration(queuedCommand);
    }

    @Override
    public boolean useQueuedCommands() {
        return commandProvider.useQueuedCommands();
    }

    @NotNull
    @Override
    public String[] getAdditionalCommandAliases(@NotNull Class<? extends Command> aClass) {
        return commandProvider.getAdditionalCommandAliases(aClass);
    }

    @Override
    public void addCommandAlias(@NotNull Class<? extends Command> aClass, @NotNull String s) {
        commandProvider.addCommandAlias(aClass, s);
    }
}
