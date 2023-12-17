package com.izanagicraft.guard.commands;

import org.jetbrains.annotations.NotNull;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard.commands:GuardCommand
 * <p>
 * Represents an abstract command related to WorldGuard functionality.
 * <p>
 * This class extends AbstractWorldGuardCommand and serves as a base for implementing
 * specific commands related to WorldGuard features in Minecraft. Subclasses are expected
 * to provide their implementation for command execution, error handling, and tab completion.
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 17.12.2023
 */
public abstract class GuardCommand extends AbstractWorldGuardCommand {

    /**
     * Constructs a new GuardCommand with the given name.
     *
     * @param name The name of the command.
     */
    public GuardCommand(@NotNull String name) {
        super(name);
    }

}
