package com.supaham.pbt.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import pluginbase.bukkit.command.BukkitCommandProvider;
import pluginbase.bukkit.messaging.BukkitMessager;
import pluginbase.bukkit.minecraft.BukkitTools;
import pluginbase.bukkit.permission.BukkitPermFactory;
import pluginbase.command.Command;
import pluginbase.command.CommandException;
import pluginbase.command.CommandHandler;
import pluginbase.command.CommandProvider;
import pluginbase.command.CommandUsageException;
import pluginbase.command.QueuedCommand;
import pluginbase.logging.LoggablePlugin;
import pluginbase.logging.PluginLogger;
import pluginbase.messages.messaging.Messager;
import pluginbase.messages.messaging.Messaging;
import pluginbase.minecraft.BasePlayer;
import pluginbase.permission.PermFactory;

import java.io.File;
import java.util.Locale;

public class Commands extends JavaPlugin implements LoggablePlugin, Messaging, CommandProvider {

    private BukkitCommandProvider commandProvider;
    private CommandHandler commandHandler = null;
    private PluginLogger logger;

    static {
        PermFactory.registerPermissionFactory(BukkitPermFactory.class);
        PermFactory.registerPermissionName(Commands.class, "test");
    }

    @Override
    public void onEnable() {
        logger = PluginLogger.getLogger(this);
        commandProvider = BukkitCommandProvider.getBukkitCommandProvider(this, "test");
        getCommandHandler().registerCommand(AwesomeCommand.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {

        if (!isEnabled()) {
            sender.sendMessage("This plugin is Disabled!");
            return true;
        }
        
        final BasePlayer wrappedSender = BukkitTools.wrapSender(sender);
        String[] allArgs = new String[args.length + 1];
        allArgs[0] = command.getName();
        System.arraycopy(args, 0, allArgs, 1, args.length);
        try {
            return getCommandHandler().locateAndRunCommand(wrappedSender, allArgs);
        } catch (CommandException e) {
            e.sendException(getMessager(), wrappedSender);
            if (e instanceof CommandUsageException) {
                for (final String usageString : ((CommandUsageException) e).getUsage()) {
                    wrappedSender.sendMessage(usageString);
                }
            }
        }
        return true;
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
