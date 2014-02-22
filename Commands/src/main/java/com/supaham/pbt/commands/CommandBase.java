package com.supaham.pbt.commands;

import pluginbase.command.Command;

public abstract class CommandBase extends Command<Commands> {
    protected CommandBase(Commands instance) {
        super(instance);
    }
}
