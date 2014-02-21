package com.supaham.pbt.commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pluginbase.command.Command;
import pluginbase.command.CommandContext;
import pluginbase.command.CommandInfo;
import pluginbase.messages.Message;
import pluginbase.minecraft.BasePlayer;
import pluginbase.permission.Perm;
import pluginbase.permission.PermFactory;

@CommandInfo(primaryAlias = "awesome", desc = "Awesome command for awesome peeps")
public class AwesomeCommand extends Command<Commands> {

    private Commands plugin;

    protected AwesomeCommand(Commands instance) {
        super(instance);
        this.plugin = instance;
    }

    @Nullable
    @Override
    public Perm getPerm() {
        return PermFactory.newPerm(Commands.class, "awesome").commandPermission().usePluginName().build();
    }

    @Nullable
    @Override
    public Message getHelp() {
        return Message.createMessage("awesome.command", "This is the help message for the awesome command.");
    }

    @Override
    public boolean runCommand(@NotNull BasePlayer sender, @NotNull CommandContext context) {
        if (sender.isPlayer()) {
            Player player = (Player) sender;
            player.getInventory().addItem(new ItemStack(Material.DIAMOND, 64));
        }
        return true;
    }

}
